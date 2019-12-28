echo "Running RabbitMQ for communication between the risk-engine and the rest-payment-service"
if [ ! "$(docker ps -q -f name=rabbit-co)" ]; 
then
    if [ "$(docker ps -aq -f status=exited -f name=rabbit-co)" ]; 
    then
        # cleanup
        docker rm rabbit-co	
    fi
    # run your container
  	docker run -d --hostname rabbit-co --name rabbit-co -p 8081:15672 -p 5672:5672 rabbitmq:3-management
	docker ps
	docker logs rabbit-co
	echo "Finishde successfully"
fi
