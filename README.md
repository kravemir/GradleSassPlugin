GradleSassPlugin
================

[![Build Status](https://travis-ci.org/kravemir/GradleSassPlugin.svg?branch=master)](https://travis-ci.org/kravemir/GradleSassPlugin)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a52c030a137944948522ef868ef9548f)](https://www.codacy.com/app/kravec.miroslav/GradleSassPlugin?utm_source=github.com&utm_medium=referral&utm_content=kravemir/GradleSassPlugin&utm_campaign=badger)

Plugin for SASS compilation. It uses [vaadin-sass-compiler](https://github.com/vaadin/sass-compiler).

* [About](#about)
* [Usage](#usage)
  * [Including in your project](#including-in-your-project)
  * [Simple configuration with extensions](#simple-configuration-with-extensions)
  * [Using as task](#using-as-task)
  * [Configuration](#configuration)
* [License](#license)

Discontinued
------------

The author has no use for this gradle plugin anymore, and development has been discontinued.

About
-----

Main features (besides SASS compilation):

* multiple build configurations support
* preserve relative paths
* various options, [see](#configuration)
* ... something missing? raise an issue for feature request :-) ...

Usage
-----

### Including in your project

```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.org.kravemir.gradle.sass:GradleSassPlugin:1.2.2"
  }
}

apply plugin: "org.kravemir.gradle.sass"
```
Or with new the plugin mechanism:
```gradle
plugins {
  id "org.kravemir.gradle.sass" version "1.2.2"
}
```

### Simple configuration with extensions

```
sass {
    main {
        srcDir = file("$projectDir/src/main/sass")
        outDir = file("$buildDir/css")
    }
}
```

### Using as task

You may declare new tasks directly using `SassCompileTask` class,
without applying plugin, just make sure to have correctly set `buildscript`.

### Configuration

Source and output configuration:

| Name                       | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`srcDir`** *(required)*  | source directory containing sass files
| **`outDir`** *(required)*  | output directory for generated resource/css files
| **`outSubDir`**            | relative path for generated files within `outDir`, used together with `registerInSourceSets`
| **`include`**              | pattern defining files to compile
| **`exclude`**              | pattern defining excluded files from compilation (they can still be `@import-ed`)

Compilation properties:

| Name                       | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`minify`**               | minifies compiled files within build configuration                                   |


Properties for integration with Java:

| Name                       | Description                                                                          |
| -------------------------- | ------------------------------------------------------------------------------------ |
| **`registerInSourceSets`** | list of sourceSet names to which `outDir` should be registered (affects classpath), [see example build.gradle](examples/03-JavaResources/build.gradle) |


License
-------

All published versions are licensed under Apache 2.0.
So, you're free to apply gradle plugin in commercial project.

You may also modify and redistribute the source, but with few limitations - see [license](LICENSE).
