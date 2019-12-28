echo "Stop & Deleting docker containers"
docker ps -a
docker stop rabbit-co
docker rm rabbit-co
pkill -9 -f tomcat
echo "Successfully terminated project"
