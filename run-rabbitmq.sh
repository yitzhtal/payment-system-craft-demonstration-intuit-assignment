#!/bin/sh
echo "Running RabbitMQ for communication between the risk-engine and the rest-payment-service"
docker stop rabbit-co
docker rm rabbit-co
docker run -d --hostname rabbit-co --name rabbit-co -p 8081:15672 -p 5672:5672 rabbitmq:3-management
docker container ls
docker logs rabbit-co
echo "Finishde successfully"

