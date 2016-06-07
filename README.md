GradleSassPlugin
================

Plugin for SASS compilation. It uses [vaadin-sass-compiler](https://github.com/vaadin/sass-compiler).

About
-----

Main features (besides SASS compilation):

* sultiple build configurations support
* various options, [see](#options)
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
    classpath "gradle.plugin.org.kravemir.gradle.sass:GradleSassPlugin:1.0"
  }
}

apply plugin: "org.kravemir.gradle.sass"
```
Or with new the plugin mechanism:
```gradle
plugins {
  id "org.kravemir.gradle.sass" version "1.0"
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

### Options

| Property                   | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`srcDir`**               | source directory containing sass files
| **`outDir`**               | output directory for generated resource/css files
| **`outSubDir`**            | relative path for generated files withing `outDir` used together with `registerInSourceSets`
| **`include`**              | pattern defining files to compile
| **`exclude`**              | pattern defining excluded files from compilation (they can still be `@import-ed`)
| **`minify`**               | minifies compiled files within build configuration                                   |
| **`registerInSourceSets`** | list of sourceSet names to which `outDir` should be registered (affects classpath), [see example build.gradle](examples/03-JavaResources/build.gradle) |
