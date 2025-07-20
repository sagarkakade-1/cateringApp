@echo off
echo ========================================
echo   🍽️  Catering Management System
echo ========================================
echo Starting application...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if PostgreSQL is running
echo 🔍 Checking PostgreSQL connection...
pg_isready -h localhost -p 5432 >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  PostgreSQL is not running or not accessible
    echo Please ensure PostgreSQL is running on localhost:5432
    echo.
    echo To start PostgreSQL:
    echo - Windows Service: net start postgresql-x64-14
    echo - Or start from pgAdmin
    echo.
    pause
)

REM Create database if it doesn't exist
echo 🗄️  Setting up database...
psql -U postgres -lqt | cut -d ^| -f 1 | findstr /r "catering_db" >nul
if %errorlevel% neq 0 (
    echo Creating database catering_db...
    createdb -U postgres catering_db
    if %errorlevel% neq 0 (
        echo ❌ Failed to create database
        echo Please create the database manually:
        echo   createdb -U postgres catering_db
        pause
    )
)

REM Start the Spring Boot application
echo 🚀 Starting Catering Management System...
echo.
echo ========================================
echo 📝 Login Credentials:
echo    Username: admin
echo    Password: admin123
echo ========================================
echo.

REM Start the application and open browser
start "" "http://localhost:8080"
mvn spring-boot:run

REM If Maven fails, try with wrapper
if %errorlevel% neq 0 (
    echo.
    echo ⚠️  Maven not found, trying with Maven wrapper...
    mvnw.cmd spring-boot:run
)

pause

