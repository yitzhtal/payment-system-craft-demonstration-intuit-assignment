echo "Build & Run rest-payment-service"
kill -9 $(lsof -t -i:8080)
cd rest-payment-service
mvn clean install
java -jar target/rest-payment-service-0.0.1-SNAPSHOT.jar
echo "Successfully Build & Run rest-payment-service"
