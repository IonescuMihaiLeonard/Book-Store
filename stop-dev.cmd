@echo off
setlocal

echo Stopping frontend...
for /f "tokens=5" %%p in ('netstat -ano ^| findstr ":5173 .*LISTENING"') do taskkill /PID %%p /F >nul 2>nul

echo Stopping microservices...
docker stop bookstore-api-gateway bookstore-order-service bookstore-catalog-service bookstore-auth-service >nul 2>nul

echo Stopping MySQL...
docker stop bookstore-mysql >nul 2>nul

echo Done.
