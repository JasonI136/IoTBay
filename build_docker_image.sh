#!/bin/bash

MAVEN_BIN=$(command -v mvn)
DOCKER_BIN=$(command -v docker)

# check if maven is installed
if [ -z "$MAVEN_BIN" ]; then
    echo "Maven is not installed"
    exit 1
fi

# check if docker is installed
if [ -z "$DOCKER_BIN" ]; then
    echo "Docker is not installed"
    exit 1
fi

# build
$MAVEN_BIN clean package

# build the docker image
$DOCKER_BIN build . -t iotbay
