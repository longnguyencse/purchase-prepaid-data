#!/bin/bash

function run_app() {
    java -jar prepaid-data.jar
}

run_app "$@"