@echo off
setlocal

set "ROOT=%~dp0"
set "MICROSERVICES=%ROOT%microservices"
set "FRONTEND=%ROOT%frontend"

docker network inspect bookstore-net >nul 2>nul || docker network create bookstore-net >nul

docker container inspect bookstore-mysql >nul 2>nul
if errorlevel 1 (
  echo Creating MySQL...
  docker run -d --name bookstore-mysql --network bookstore-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=auth_service -e MYSQL_USER=bookstore -e MYSQL_PASSWORD=bookstore -p 13306:3306 mysql:8.4
) else (
  echo Starting MySQL...
  docker start bookstore-mysql >nul
  docker network connect bookstore-net bookstore-mysql >nul 2>nul
)

echo Waiting for MySQL...
for /l %%i in (1,1,45) do (
  docker exec bookstore-mysql mysqladmin ping -uroot -proot --silent >nul 2>nul
  if not errorlevel 1 goto mysql_ready
  timeout /t 2 >nul
)

echo MySQL nu a pornit la timp.
exit /b 1

:mysql_ready
call "%MICROSERVICES%\start-auth-service.cmd"
call "%MICROSERVICES%\start-catalog-service.cmd"
call "%MICROSERVICES%\start-order-service.cmd"
call "%MICROSERVICES%\start-api-gateway.cmd"

echo Starting frontend...
set "FRONTEND_PID="
for /f "tokens=5" %%p in ('netstat -ano ^| findstr ":5173 .*LISTENING"') do set "FRONTEND_PID=%%p"

if defined FRONTEND_PID (
  echo Frontend already running on port 5173.
) else (
  start "bookstore-frontend" /min cmd /c "cd /d ""%FRONTEND%"" && node_modules\.bin\vite.cmd --host 0.0.0.0 --port 5173 --strictPort"
)

echo.
echo Frontend:    http://localhost:5173
echo API Gateway: http://localhost:8085/api/v1
echo MySQL:       localhost:13306 / user bookstore / password bookstore
