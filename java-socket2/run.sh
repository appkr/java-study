#!/usr/bin/env bash

./gradlew clean build
java -jar build/libs/pointchargercallback-tcp-client-0.0.1.jar
