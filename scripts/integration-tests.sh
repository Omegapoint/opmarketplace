#!/bin/bash
set -ev
# messageservice has to be first in array to enable
# other services to subscribe on startup!
DIRS=(messageservice customer)
PIDS=()
for DIR in ${DIRS[@]}; do
  echo Starting build of $DIR
  cd ../$DIR
  chmod +x gradlew
  ./gradlew build --info
  java -jar build/libs/$DIR-0.0.1-SNAPSHOT.jar &
  PIDS+=($!)
  cd ../scripts
done
echo Starting Apigateway
cd ../apigateway
chmod +x gradlew
./gradlew integrationTest
cd ../scripts
echo -------------------------CleanUp-------------------------
for PID in ${PIDS[@]}; do
  echo Killing process: $PID...
  kill $PID
done
