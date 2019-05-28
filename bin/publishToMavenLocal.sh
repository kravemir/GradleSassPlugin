#!/usr/bin/env bash

./gradlew --console=plain \
    -PenablePublishing \
    clean publishToMavenLocal
