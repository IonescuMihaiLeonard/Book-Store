@echo off
setlocal

set "ROOT=%~dp0"
set "BACKEND=%ROOT%backend"
set "FRONTEND=%ROOT%frontend"

echo Starting MySQL...
docker compose -f "%BACKEND%\docker-compose.yml" up -d mysql

docker container inspect bookstore-backend-dev >nul 2>nul
if errorlevel 1 (
  echo Creating backend container...
  docker run -d --name bookstore-backend-dev -v "%BACKEND%:/workspace" -v bookstore_maven_cache:/root/.m2 -w /workspace -e DB_HOST=host.docker.internal -e DB_PORT=13306 -e DB_NAME=book_store -e DB_USERNAME=bookstore -e DB_PASSWORD=bookstore -p 8080:8080 maven:3.9.12-eclipse-temurin-21 mvn spring-boot:run
) else (
  echo Starting backend container...
  docker start bookstore-backend-dev
)

docker container inspect bookstore-frontend-dev >nul 2>nul
if errorlevel 1 (
  echo Creating frontend container...
  docker run -d --name bookstore-frontend-dev -v "%FRONTEND%:/workspace" -v bookstore_frontend_node_modules:/workspace/node_modules -w /workspace -p 5173:5173 node:24-alpine sh -c "npm ci && npm run dev -- --host 0.0.0.0 --port 5173"
) else (
  echo Starting frontend container...
  docker start bookstore-frontend-dev
)

echo.
echo Frontend: http://localhost:5173
echo Backend:  http://localhost:8080/api/v1
echo MySQL:    localhost:13306 / database book_store / user bookstore / password bookstore
echo.
echo Logs:
echo   docker logs -f bookstore-backend-dev
echo   docker logs -f bookstore-frontend-dev
