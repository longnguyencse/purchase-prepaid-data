#!/usr/bin/env bash

set -e
project="voucher"
registry="ghcr.io/longnguyencse/purchase-prepaid-data"
if [ ! -z $1 ]; then
  environment=$1"-"
else
  environment=''
fi


# 1. build jar file
./gradlew bootJar

docker login ghcr.io -u longnguyencse --password-stdin

docker build \
  -t "${registry}"/"${project}":"$(git rev-parse --short HEAD)" \
  -t "${registry}"/"${project}":"${environment}"latest .

docker push "${registry}"/"${project}":"$(git rev-parse --short HEAD)"
docker push "${registry}"/"${project}":"${environment}"latest
