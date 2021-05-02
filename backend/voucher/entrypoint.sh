#!/bin/bash

function run_app() {
    java -jar voucher.jar
}

# Make it rain
run_app "$@"