package org.kravemir.gradle.sass;

import com.vaadin.sass.internal.ScssStylesheet;
import com.vaadin.sass.internal.resolver.FilesystemResolver;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Abstract and reusable Sass Compilation Task
 * @author Miroslav Kravec
 */
public abstract class AbstractSassCompileTask extends DefaultTask {
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

    @InputFiles
    FileCollection getInputFiles() {
        if(getSrcDir().exists() == false)
            throw new RuntimeException("srcDir doesn't exists");
        if(getSrcDir().isDirectory() == false)
            throw new RuntimeException("srcDir isn't directory");

        ConfigurableFileTree fileTree = getProject().fileTree(getSrcDir());
        return fileTree;
    }

    @TaskAction
    void compile() throws Exception {
        File outputDir = getOutputDirectory();
        outputDir.mkdirs();

        Path srcDirPath = getSrcDir().toPath();

        for(File f : getSassFiles()) {
            Path relativePath = srcDirPath.relativize(f.toPath());
            File outputFile = new File(outputDir,relativePath.toString().replaceAll("\\.scss",".css"));
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

    /**
     * @return directory, where compiled CSS files are written
     */
    @OutputDirectory
    protected abstract File getOutputDirectory();

    /**
     * @return source directory, where SASS files are searched
     */
    protected abstract File getSrcDir();

    /**
     * @return pattern defining files to include in compilation as SASS root file
     */
    protected abstract String getInclude();

    /**
     * @return pattern defining to exclude files as SASS root
     */
    protected abstract String getExclude();

    /**
     * @return boolean, defines whether CSS files should be minified
     */
    protected abstract boolean getMinify();
}
