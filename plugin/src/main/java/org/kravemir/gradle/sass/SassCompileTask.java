package org.kravemir.gradle.sass;

import com.vaadin.sass.internal.ScssStylesheet;
import com.vaadin.sass.internal.resolver.FilesystemResolver;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.kravemir.gradle.sass.api.SassBuildConfiguration;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by miroslav on 4/23/16.
 */
public class SassCompileTask extends DefaultTask {

    private SassBuildConfiguration configuration;

    @Inject
    public SassCompileTask() {
    }

    FileCollection getSassFiles() {
        if(configuration.getSrcDir().exists() == false)
            throw new RuntimeException("srcDir doesn't exists");
        if(configuration.getSrcDir().isDirectory() == false)
            throw new RuntimeException("srcDir isn't directory");

        ConfigurableFileTree fileTree = getProject().fileTree(configuration.getSrcDir());
        if(configuration.getInclude() != null)
            fileTree.include(configuration.getInclude());
        if(configuration.getExclude() != null)
            fileTree.exclude(configuration.getExclude());

        return fileTree;
    }

    @InputFiles
    FileCollection getInputFiles() {
        if(configuration.getSrcDir().exists() == false)
            throw new RuntimeException("srcDir doesn't exists");
        if(configuration.getSrcDir().isDirectory() == false)
            throw new RuntimeException("srcDir isn't directory");

        ConfigurableFileTree fileTree = getProject().fileTree(configuration.getSrcDir());
        return fileTree;
    }

    @OutputDirectory
    File getOutputDirectory() {
        return getConfiguration().getBuildDir();
    }

    @TaskAction
    void compile() throws Exception {
        File outputDir = configuration.getBuildDir();
        outputDir.mkdirs();

        for(File f : getSassFiles()) {
            ScssStylesheet sass = ScssStylesheet.get(f.getAbsolutePath());
            sass.setFile(f);
            sass.setCharset("UTF-8"); // TODO: inteligent
            sass.addResolver(new FilesystemResolver(configuration.getSrcDir().getAbsolutePath()));
            sass.compile();
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
                    new File(outputDir.getAbsolutePath() + File.separator + f.getName().replaceAll("\\.scss",".css"))
            ));
            sass.write(out,configuration.getMinify());
            out.close();
        }
    }

    public SassBuildConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(SassBuildConfiguration configuration) {
        this.configuration = configuration;
    }
}
