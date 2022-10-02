./gradlew clean shadowJar

docker rmi dev-server
docker build -t dev-server .

docker run -p 25565:25565 -e EULA=TRUE -e TYPE=PAPER -it dev-server