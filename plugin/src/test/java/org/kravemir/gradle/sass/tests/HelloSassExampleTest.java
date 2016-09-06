package org.kravemir.gradle.sass.tests;

import org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * @author Miroslav Kravec
 */
public class HelloSassExampleTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private void sed(File file, Pattern regex, String value) throws IOException {
        String contents = FileUtils.readFileToString(file, StandardCharsets.UTF_8.name());
        contents = regex.matcher(contents).replaceAll(value);
        FileUtils.write(file, contents);
    }

    private void removePluginVersion(File file) throws IOException {
        sed(
                file,
                Pattern.compile("id 'org\\.kravemir\\.gradle\\.sass\\' version .*"),
                "id 'org.kravemir.gradle.sass'"
        );

    }

    @Before
    public void setup() throws IOException {
        // copy 01-HelloSass example
        FileUtils.copyDirectory(Paths.get("..","examples","01-HelloSass").toFile(), testProjectDir.getRoot());
        File buildDirectory = new File(testProjectDir.getRoot(), "build");
        if(buildDirectory.exists())
            FileUtils.deleteDirectory(buildDirectory);

        removePluginVersion(new File(testProjectDir.getRoot(), "build.gradle"));
    }

    @Test
    public void checkBuiltFiles() {
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("mainSass")
                .withPluginClasspath()
                .build();

        // check .css files exist
        Path mainSassBuildRoot = Paths.get(testProjectDir.getRoot().getPath(), "build", "sass", "main");
        assertTrue(mainSassBuildRoot.resolve(Paths.get("main.css")).toFile().exists());
        assertTrue(mainSassBuildRoot.resolve(Paths.get("relPath","foo.css")).toFile().exists());
    }

}
