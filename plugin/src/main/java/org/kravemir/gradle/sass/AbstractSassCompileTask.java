package org.kravemir.gradle.sass;

import com.vaadin.sass.internal.ScssStylesheet;
import com.vaadin.sass.internal.resolver.FilesystemResolver;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.*;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Abstract and reusable Sass Compilation Task
 * @author Miroslav Kravec
 */
@CacheableTask
public abstract class AbstractSassCompileTask extends DefaultTask {

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    FileCollection getSassFiles() {
        if(getSrcDir().exists() == false)
            throw new RuntimeException("srcDir doesn't exists");
        if(getSrcDir().isDirectory() == false)
            throw new RuntimeException("srcDir isn't directory");

        ConfigurableFileTree fileTree = getProject().fileTree(getSrcDir());
        if(getInclude() != null)
            fileTree.include(getInclude());
        if(getExclude() != null)
            fileTree.exclude(getExclude());

        return fileTree;
    }

    @Internal
    FileCollection getInputFiles() {
        if(getSrcDir().exists() == false)
            throw new RuntimeException("srcDir doesn't exists");
        if(getSrcDir().isDirectory() == false)
            throw new RuntimeException("srcDir isn't directory");

        ConfigurableFileTree fileTree = getProject().fileTree(getSrcDir());
        return fileTree;
    }

    @TaskAction
    void compile(IncrementalTaskInputs incrementalTaskInputs) throws Exception {
        File outputDir = getOutputDirectory();
        outputDir.mkdirs();

        Path srcDirPath = getSrcDir().toPath();

        // only delete removed files
        if(incrementalTaskInputs.isIncremental()) {
            incrementalTaskInputs.outOfDate(inputFileDetails -> {});
            incrementalTaskInputs.removed(inputFileDetails -> {
                File outputFile = getOutputFile(srcDirPath,outputDir,inputFileDetails.getFile());
                if(outputFile.exists())
                    outputFile.delete();
            });
        }

        for(File f : getSassFiles()) {
            File outputFile = getOutputFile(srcDirPath,outputDir,f);
            outputFile.getParentFile().mkdirs();

            try(OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outputFile))) {
                ScssStylesheet sass = ScssStylesheet.get(f.getAbsolutePath());
                sass.setFile(f);
                sass.setCharset("UTF-8"); // TODO: inteligent
                sass.addResolver(new FilesystemResolver(getSrcDir().getAbsolutePath()));
                sass.compile();
                sass.write(out,getMinify());
            }
        }
    }

    private File getOutputFile(Path srcDirPath, File outputDir, File f) {
        Path relativePath = srcDirPath.relativize(f.toPath());
        return new File(outputDir,relativePath.toString().replaceAll("\\.scss",".css"));
    }

    /**
     * @return directory, where compiled CSS files are written
     */
    @OutputDirectory
    protected abstract File getOutputDirectory();

    /**
     * @return source directory, where SASS files are searched
     */
    @InputDirectory
    @PathSensitive(PathSensitivity.RELATIVE)
    protected abstract File getSrcDir();

    /**
     * @return pattern defining files to include in compilation as SASS root file
     */
    @Internal
    protected abstract String getInclude();

    /**
     * @return pattern defining to exclude files as SASS root
     */
    @Internal
    protected abstract String getExclude();

    /**
     * @return boolean, defines whether CSS files should be minified
     */
    @Input
    protected abstract boolean getMinify();
}
