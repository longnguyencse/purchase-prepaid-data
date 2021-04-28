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

# new login with new container registry url and PAT
#echo "${CR_PAT}"
#echo "${CR_PAT}" | docker login ghcr.io -u "$GITHUB_ACTOR" --password-stdin
#
## $(git rev-parse --abbrev-ref HEAD)
#docker build \
#  -t "${registry}"/"${project}":"$(git rev-parse --short HEAD)" \
#  -t "${registry}"/"${project}":"${environment}"latest .
#
#docker push "${registry}"/"${project}":"$(git rev-parse --short HEAD)"
#docker push "${registry}"/"${project}":"${environment}"latest
