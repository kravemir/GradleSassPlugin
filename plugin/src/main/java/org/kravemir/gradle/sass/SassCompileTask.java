package org.kravemir.gradle.sass;

import org.gradle.api.GradleException;
import org.gradle.api.plugins.JavaPluginConvention;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * Concrete implementation of {@link AbstractSassCompileTask}
 * @author Miroslav Kravec
 */
public class SassCompileTask extends AbstractSassCompileTask {
    private File srcDir;
    private File outDir;
    private String outSubDir = null;

    private String include = null;
    private String exclude = null;

    private boolean minify = false;

    private SassCompileTask task = null;

    @Override
    public File getOutputDirectory() {
        if(outSubDir != null)
            return Paths.get(outDir.getPath(), outSubDir).toFile();
        return outDir;
    }

    @Override
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

    public String getOutSubDir() {
        return outSubDir;
    }

    public void setOutSubDir(String outSubDir) {
        this.outSubDir = outSubDir;
    }

    @Override
    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    @Override
    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    @Override
    public boolean getMinify() {
        return minify;
    }

    public void setMinify(boolean minify) {
        this.minify = minify;
    }

    public void registerInSourceSets(String ...sourceSetNames) {
        if (sourceSetNames == null || sourceSetNames.length == 0) return;

        try {
            JavaPluginConvention javaPlugin = getProject().getConvention().getPlugin(JavaPluginConvention.class);
            if (javaPlugin == null) {
                throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
            }

            for (String sourceSet : sourceSetNames) {
                javaPlugin.getSourceSets().getByName(sourceSet).getOutput().dir(
                        Collections.singletonMap("builtBy", task),
                        getOutDir()
                );
            }
        } catch (Exception e) {
            throw new GradleException("You must apply the java plugin if you're using 'registerInSourceSets' functionality.");
        }
    }

}
