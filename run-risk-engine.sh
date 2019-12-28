#!/bin/sh
kill -9 $(lsof -t -i:8087)
echo "Build & Run risk-engine"
cd risk-engine
mvn clean install
java -jar target/risk-engine-0.0.1-SNAPSHOT.jar 
echo "Successfully Build & Run risk-engine"