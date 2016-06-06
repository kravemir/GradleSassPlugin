package org.kravemir.gradle.sass;

import com.vaadin.sass.internal.ScssStylesheet;
import com.vaadin.sass.internal.resolver.FilesystemResolver;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by miroslav on 4/23/16.
 */
public class SassCompileTask extends DefaultTask {


    private File srcDir;

    @OutputDirectory
    private File outDir;

    private String include = "**/*.scss";
    private String exclude = "**/_*";

    Boolean minify = false;

    @Inject
    public SassCompileTask() {
    }

    @InputFiles
    FileCollection getSassFiles() {
        if(srcDir.exists() == false)
            throw new RuntimeException("srcDir doesn't exists");
        if(srcDir.isDirectory() == false)
            throw new RuntimeException("srcDir isn't directory");

        ConfigurableFileTree fileTree = getProject().fileTree(srcDir);
        if(include != null)
            fileTree.include(include);
        if(exclude != null)
            fileTree.exclude(exclude);

        return fileTree;
    }

    @TaskAction
    void compile() throws Exception {
        File outputDir = outDir;
        outputDir.mkdirs();

        for(File f : getSassFiles()) {
            ScssStylesheet sass = ScssStylesheet.get(f.getAbsolutePath());
            sass.setFile(f);
            sass.setCharset("UTF-8"); // TODO: inteligent
            sass.addResolver(new FilesystemResolver(srcDir.getAbsolutePath()));
            sass.compile();
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
                    new File(outputDir.getAbsolutePath() + File.separator + f.getName().replaceAll("\\.scss",".css"))
            ));
            sass.write(out);
            out.close();
        }
    }

    public File getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public File getOutDir() {
        return outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public Boolean getMinify() {
        return minify;
    }

    public void setMinify(Boolean minify) {
        this.minify = minify;
    }
}
