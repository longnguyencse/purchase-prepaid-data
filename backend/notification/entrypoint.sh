#!/bin/bash

function run_app() {
    java -jar notification.jar
}

# Make it rain
run_app "$@"