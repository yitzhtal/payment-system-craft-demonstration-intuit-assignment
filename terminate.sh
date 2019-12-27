echo "Stop & Deleting docker containers"
docker container ls
docker stop rabbit-co
docker rm rabbit-co
echo "Successfully terminated project"