#!/bin/bash

DIR=$(realpath $(dirname $0))

echo $DIR

set -e

cd "$DIR/../../MC-Device"
chmod +x ./gradlew
./gradlew clean build --stacktrace
./gradlew -stop

