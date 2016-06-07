package org.kravemir.gradle.sass.api;

import java.io.File;

/**
 * Created by miroslav on 6/7/16.
 */
public interface SassBuildConfiguration {
    File getBuildDir();

    File getSrcDir();

    String getInclude();

    String getExclude();

    boolean getMinify();
}
