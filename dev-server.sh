./gradlew clean shadowJar

docker rmi dev-server
docker build -t dev-server .

docker-compose down -v
docker-compose up -d