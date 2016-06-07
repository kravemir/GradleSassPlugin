package org.kravemir.gradle.sass;

import org.kravemir.gradle.sass.api.SassBuildConfiguration;

import java.io.File;
import java.nio.file.Paths;

public class SassBuildResourceSet implements SassBuildConfiguration {
    private final String name;

    private File srcDir;
    private File outDir;
    private String outSubDir = null;

    private String include = null;
    private String exclude = null;

    private boolean minify = false;

    private String[] registerInSourceSets = null;

    public SassBuildResourceSet(String name) {
        this.name = name;
    }

    @Override
    public File getBuildDir() {
        if(outSubDir != null)
            return Paths.get(outDir.getPath(), outSubDir).toFile();
        return outDir;
    }

    public String getName() {
        return name;
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

    public String[] getRegisterInSourceSets() {
        return registerInSourceSets;
    }

    public void setRegisterInSourceSets(String[] registerInSourceSets) {
        this.registerInSourceSets = registerInSourceSets;
    }
}
