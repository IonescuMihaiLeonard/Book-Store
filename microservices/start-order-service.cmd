@echo off
setlocal

docker network inspect bookstore-net >nul 2>nul || docker network create bookstore-net >nul
docker exec bookstore-mysql mysql -uroot -proot -e "CREATE DATABASE IF NOT EXISTS order_service; GRANT ALL PRIVILEGES ON order_service.* TO 'bookstore'@'%%'; FLUSH PRIVILEGES;"
docker rm -f bookstore-order-service >nul 2>nul
docker run -d --name bookstore-order-service ^
  --network bookstore-net ^
  -v "%~dp0:/workspace" ^
  -v bookstore_maven_cache:/root/.m2 ^
  -w /workspace ^
  -e DB_HOST=host.docker.internal ^
  -e DB_PORT=13306 ^
  -e DB_NAME=order_service ^
  -e DB_USERNAME=bookstore ^
  -e DB_PASSWORD=bookstore ^
  -p 8083:8083 ^
  maven:3.9.12-eclipse-temurin-21 sh -c "mvn -q -pl order-service package -DskipTests && java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar"

echo order-service porneste pe http://localhost:8083
