#!/bin/bash

function run_app() {
    java -jar jms.jar
}

# Make it rain
./wait-for-it.sh "${DB_HOST}":"${DB_PORT}" && run_app "$@"