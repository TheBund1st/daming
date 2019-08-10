#!/bin/bash

work_dir=`dirname $0`
cd "$work_dir"
echo " Working Directory: $work_dir "

case "$1" in
  "docker")
    docker-compose -f docker/docker-compose.yml down
    docker-compose -f docker/docker-compose.yml up -d
    ;;
  "stubs")
    docker-compose -f docker/docker-compose-stubs.yml down
    docker-compose -f docker/docker-compose-stubs.yml up -d
    ;;
esac
