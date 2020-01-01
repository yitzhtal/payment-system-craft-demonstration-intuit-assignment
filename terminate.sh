echo "Stop & Deleting docker containers"
docker ps -a
docker stop rabbit-co
docker rm rabbit-co
docker stop mysql57
docker rm mysql57
pkill -9 -f tomcat
echo "Successfully terminated project"
