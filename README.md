GradleSassPlugin
================

Plugin for SASS compilation. It uses [vaadin-sass-compiler](https://github.com/vaadin/sass-compiler).

* [About](#about)
* [Usage](#usage)
  * [Configuration](#configuration)

About
-----

Main features (besides SASS compilation):

* multiple build configurations support
* various options, [see](#configuration)
* ... something missing? raise an issue for feature request :-) ...

Usage
-----

Including in your project:

```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.org.kravemir.gradle.sass:GradleSassPlugin:1.1"
  }
}

apply plugin: "org.kravemir.gradle.sass"
```
Or with new the plugin mechanism:
```gradle
plugins {
  id "org.kravemir.gradle.sass" version "1.1"
}
```
And, HelloSass configuration:
```
sass {
    main {
        srcDir = file("$projectDir/src/main/sass")
        outDir = file("$buildDir/css")
    }
}
```

### Configuration

Required properties:

| Name                       | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`srcDir`**               | source directory containing sass files
| **`outDir`**               | output directory for generated resource/css files

Build and compilation related properties:

| Name                       | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`outSubDir`**            | relative path for generated files within `outDir`, used together with `registerInSourceSets`
| **`include`**              | pattern defining files to compile
| **`exclude`**              | pattern defining excluded files from compilation (they can still be `@import-ed`)
| **`minify`**               | minifies compiled files within build configuration                                   |


Properties defining integration with java:

| Name                       | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`registerInSourceSets`** | list of sourceSet names to which `outDir` should be registered (affects classpath), [see example build.gradle](examples/03-JavaResources/build.gradle) |
