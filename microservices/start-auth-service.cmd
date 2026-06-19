@echo off
setlocal

set "ROOT=%~dp0"

docker network inspect bookstore-net >nul 2>nul || docker network create bookstore-net >nul
docker exec bookstore-mysql mysql -uroot -proot -e "CREATE DATABASE IF NOT EXISTS auth_service; GRANT ALL PRIVILEGES ON auth_service.* TO 'bookstore'@'%%'; FLUSH PRIVILEGES;"

docker container inspect bookstore-auth-service >nul 2>nul
if errorlevel 1 (
  docker run -d --name bookstore-auth-service --network bookstore-net -v "%ROOT%:/workspace" -v bookstore_maven_cache:/root/.m2 -w /workspace -e DB_HOST=host.docker.internal -e DB_PORT=13306 -e DB_NAME=auth_service -e DB_USERNAME=bookstore -e DB_PASSWORD=bookstore -p 8081:8081 maven:3.9.12-eclipse-temurin-21 mvn -pl auth-service spring-boot:run
) else (
  docker start bookstore-auth-service
  docker network connect bookstore-net bookstore-auth-service >nul 2>nul
)

echo Auth service: http://localhost:8081/api/v1/auth
echo Health:       http://localhost:8081/actuator/health
