@echo off
echo.
echo ========================================
echo   Catering Management System Startup
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher and try again
    pause
    exit /b 1
)

REM Check if PostgreSQL is running
echo Checking PostgreSQL connection...
pg_isready -h localhost -p 5432 >nul 2>&1
if %errorlevel% neq 0 (
    echo WARNING: PostgreSQL is not running on localhost:5432
    echo Please start PostgreSQL service and ensure database 'catering_db' exists
    echo.
    echo To create the database, run:
    echo   createdb -U postgres catering_db
    echo   psql -U postgres -c "CREATE USER catering_user WITH PASSWORD 'catering_pass';"
    echo   psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE catering_db TO catering_user;"
    echo.
    set /p continue="Continue anyway? (y/n): "
    if /i not "%continue%"=="y" exit /b 1
)

echo.
echo Starting Catering Management System...
echo.
echo Backend: Spring Boot application will start on http://localhost:8080
echo Frontend: React application will be served from the backend
echo.
echo Please wait while the application starts...
echo.

REM Start the Spring Boot application
echo Starting Spring Boot backend...
call mvn spring-boot:run

REM If Maven command fails, try with wrapper
if %errorlevel% neq 0 (
    echo Maven not found, trying with Maven wrapper...
    if exist mvnw.cmd (
        call mvnw.cmd spring-boot:run
    ) else (
        echo ERROR: Neither Maven nor Maven wrapper found
        echo Please install Maven or ensure mvnw.cmd exists
        pause
        exit /b 1
    )
)

REM Open browser after a delay
timeout /t 10 /nobreak >nul
start http://localhost:8080

echo.
echo ========================================
echo   Application started successfully!
echo   Access: http://localhost:8080
echo   Login: admin / admin123
echo ========================================
echo.
echo Press Ctrl+C to stop the application
pause

