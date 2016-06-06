package org.kravemir.gradle.sass;

import java.io.File;

public class SassBuildConfiguration {
    private final String name;

    private File srcDir;
    private File outDir;

    private String include = null;
    private String exclude = null;

    private boolean minify = false;

    public SassBuildConfiguration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public boolean getMinify() {
        return minify;
    }

    public void setMinify(boolean minify) {
        this.minify = minify;
    }
}
