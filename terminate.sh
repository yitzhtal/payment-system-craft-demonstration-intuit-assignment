echo "Stop & Deleting docker containers"
docker container ls
docker stop rabbit-co
docker rm rabbit-co
pkill -9 -f tomcat
echo "Successfully terminated project"
