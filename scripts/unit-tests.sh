#!/bin/bash
set -ev
cd $DIR
chmod +x gradlew
./gradlew build --info
