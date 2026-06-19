@echo off
setlocal

set "ROOT=%~dp0"
set "BACKEND=%ROOT%backend"

echo Stopping frontend...
docker stop bookstore-frontend-dev >nul 2>nul

echo Stopping backend...
docker stop bookstore-backend-dev >nul 2>nul

echo Stopping MySQL...
docker compose -f "%BACKEND%\docker-compose.yml" stop mysql

echo Done.
