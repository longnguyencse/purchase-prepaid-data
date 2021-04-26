#!/bin/bash

function run_app() {
  if [ "${APM:=0}" -eq 1 ]; then
    java -javaagent:elastic-apm-agent-1.19.0.jar \
      -Delastic.apm.service_name=pharmacy \
      -Delastic.apm.server_urls=https://apm.jiohealth.com \
      -Delastic.apm.environment="${APM_ENV:=dev}" \
      -Delastic.apm.secret_token=Mpjk0r5VivECPWlSyNikw888qYV4BM0P \
      -Delastic.apm.application_packages=com.jiohealth \
      -jar jio-pharmacy.jar
  else
    java -jar jio-pharmacy.jar
  fi
}

# Make it rain
./wait-for-it.sh "${DB_HOST}":"${DB_PORT}" && run_app "$@"