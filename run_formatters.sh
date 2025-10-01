#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Run server formatter
formatServer() {
    cd server && ./gradlew spotlessApply && cd ..
}

# Run client formatter & eslint
formatClient() {
    cd client && npx prettier . --write && npx eslint . && cd ..
}

# Wait for all services to start, kill with CTRL+C
formatServer && echo && formatClient; trap "exit" SIGINT; wait