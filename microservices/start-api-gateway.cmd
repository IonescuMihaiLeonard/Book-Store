@echo off
setlocal

docker network inspect bookstore-net >nul 2>nul || docker network create bookstore-net >nul
docker network connect bookstore-net bookstore-auth-service >nul 2>nul
docker network connect bookstore-net bookstore-catalog-service >nul 2>nul
docker network connect bookstore-net bookstore-order-service >nul 2>nul
docker rm -f bookstore-api-gateway >nul 2>nul
docker run -d --name bookstore-api-gateway ^
  --network bookstore-net ^
  -v "%~dp0:/workspace" ^
  -v bookstore_maven_cache:/root/.m2 ^
  -w /workspace ^
  -e SPRING_PROFILES_ACTIVE=dev ^
  -e AUTH_SERVICE_URL=http://bookstore-auth-service:8081 ^
  -e CATALOG_SERVICE_URL=http://bookstore-catalog-service:8082 ^
  -e ORDER_SERVICE_URL=http://bookstore-order-service:8083 ^
  -e JAVA_TOOL_OPTIONS="-Xms128m -Xmx384m -XX:ActiveProcessorCount=2 -XX:TieredStopAtLevel=1" ^
  -p 8085:8085 ^
  maven:3.9.12-eclipse-temurin-21 java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar

echo api-gateway porneste pe http://localhost:8085
