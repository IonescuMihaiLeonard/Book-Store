@echo off
setlocal

set "ROOT=%~dp0"

docker network inspect bookstore-net >nul 2>nul || docker network create bookstore-net >nul
docker exec bookstore-mysql mysql -uroot -proot -e "CREATE DATABASE IF NOT EXISTS catalog_service; GRANT ALL PRIVILEGES ON catalog_service.* TO 'bookstore'@'%%'; FLUSH PRIVILEGES;"

docker container inspect bookstore-catalog-service >nul 2>nul
if errorlevel 1 (
  docker run -d --name bookstore-catalog-service --network bookstore-net -v "%ROOT%:/workspace" -v bookstore_maven_cache:/root/.m2 -w /workspace -e DB_HOST=host.docker.internal -e DB_PORT=13306 -e DB_NAME=catalog_service -e DB_USERNAME=bookstore -e DB_PASSWORD=bookstore -p 8082:8082 maven:3.9.12-eclipse-temurin-21 mvn -pl catalog-service spring-boot:run
) else (
  docker start bookstore-catalog-service
  docker network connect bookstore-net bookstore-catalog-service >nul 2>nul
)

echo Catalog service: http://localhost:8082/api/v1/books
echo Health:          http://localhost:8082/actuator/health
