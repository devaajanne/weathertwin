#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Start the server service
startServer() {
    cd server && ./gradlew bootRun --args='--spring.profiles.active=dev' && cd ..
}

# Start the client service
startClient(){
    cd client && npm run dev && cd ..
}

# Wait for all services to start, kill with CTRL+C
startServer & startClient; trap "exit" SIGINT; wait