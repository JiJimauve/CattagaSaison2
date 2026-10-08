#!/bin/bash

DIR=$(realpath $(dirname $0))

echo $DIR

set -e

cd "$DIR/../../added-mods/homemade/corruption-1.20.1"
chmod +x ./gradlew
./gradlew clean build --stacktrace
./gradlew -stop
