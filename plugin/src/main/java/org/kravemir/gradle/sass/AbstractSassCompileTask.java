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

        for(File f : getSassFiles()) {
            ScssStylesheet sass = ScssStylesheet.get(f.getAbsolutePath());
            sass.setFile(f);
            sass.setCharset("UTF-8"); // TODO: inteligent
            sass.addResolver(new FilesystemResolver(getSrcDir().getAbsolutePath()));
            sass.compile();
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
                    new File(outputDir.getAbsolutePath() + File.separator + f.getName().replaceAll("\\.scss",".css"))
            ));
            sass.write(out,getMinify());
            out.close();
        }
    }

    @OutputDirectory
    protected abstract File getOutputDirectory();

    protected abstract File getSrcDir();
    protected abstract String getInclude();
    protected abstract String getExclude();
    protected abstract boolean getMinify();
}
