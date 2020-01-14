echo "Stop & Deleting docker containers"
docker ps -a
docker stop rabbit-co
docker rm rabbit-co
sudo lsof -i :3306
docker stop mysql57
docker rm mysql57
pkill -9 -f tomcat
echo "Successfully terminated project"
