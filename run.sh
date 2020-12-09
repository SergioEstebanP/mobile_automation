#!/bin/bash

# CHANGE ACCORDING TO YOUR SYSTEM
export ANDROID_HOME=/Users/sergioestebanpellejero/Library/Android/sdk
export PATH=${PATH}:/Users/sergioestebanpellejero/Library/Android/sdk/tools
export PATH=${PATH}:/Users/sergioestebanpellejero/Library/Android/sdk/platform-tools
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

./gradlew clean test aggregate -Dcucumber.options="--tags @run"