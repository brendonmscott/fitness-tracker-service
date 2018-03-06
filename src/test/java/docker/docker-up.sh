#!/bin/bash

# Bring up Docker
cd src/test/java/docker
docker-compose up --build --force-recreate -d

#Give Docker 10 seconds to initialize
sleep 10