@echo off
echo Stopping wigellrepairs
docker stop wigellrepairs
echo Deleting container wigellrepairs
docker rm wigellrepairs
echo Deleting image wigellrepairs
docker rmi wigellrepairs
echo Running mvn package
call mvn package
echo Creating image wigellrepairs
docker build -t wigellrepairs .
echo Creating and running container wigellrepairs
docker run -d -p 5555:5555 --name wigellrepairs --network wigell wigellrepairs
echo Done!