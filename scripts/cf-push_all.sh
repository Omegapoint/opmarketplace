#!/bin/bash
# messageservice has to be first in array to enable
# other services to subscribe on startup!
DIRS=(messageservice apigateway customer eventanalyzer)
for DIR in ${DIRS[@]}; do
  echo -------------------------Starting build of $DIR-------------------------
  cd ../$DIR
  chmod +x gradlew
  ./gradlew clean build -quiet
  echo -------------------------Starting push to CloudFoundry of $DIR-------------------------
  cf push
  cd ../scripts
done
