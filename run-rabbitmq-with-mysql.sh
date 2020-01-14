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
	echo "Finished successfully"
fi

echo "Running MySQL"
if [ ! "$(docker ps -q -f name=mysql57)" ]; 
then
    if [ "$(docker ps -aq -f status=exited -f name=mysql57)" ]; 
    then
        # cleanup
        docker rm mysql57
    fi
    # run your container
    docker run --name mysql57 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_USER=talitz -e MYSQL_PASSWORD=1234 -e MYSQL_DATABASE=payment-service -d mysql/mysql-server:5.7
    docker ps
    docker logs mysql57
    echo "Finished successfully"
fi

