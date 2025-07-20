#!/bin/bash

echo ""
echo "========================================"
echo "  Catering Management System Startup"
echo "========================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}ERROR: Java is not installed or not in PATH${NC}"
    echo "Please install Java 17 or higher and try again"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo -e "${RED}ERROR: Java 17 or higher is required${NC}"
    echo "Current Java version: $(java -version 2>&1 | head -n 1)"
    exit 1
fi

# Check if PostgreSQL is running
echo "Checking PostgreSQL connection..."
if ! pg_isready -h localhost -p 5432 &> /dev/null; then
    echo -e "${YELLOW}WARNING: PostgreSQL is not running on localhost:5432${NC}"
    echo "Please start PostgreSQL service and ensure database 'catering_db' exists"
    echo ""
    echo "To create the database, run:"
    echo "  createdb -U postgres catering_db"
    echo "  psql -U postgres -c \"CREATE USER catering_user WITH PASSWORD 'catering_pass';\""
    echo "  psql -U postgres -c \"GRANT ALL PRIVILEGES ON DATABASE catering_db TO catering_user;\""
    echo ""
    read -p "Continue anyway? (y/n): " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

echo ""
echo -e "${BLUE}Starting Catering Management System...${NC}"
echo ""
echo "Backend: Spring Boot application will start on http://localhost:8080"
echo "Frontend: React application will be served from the backend"
echo ""
echo "Please wait while the application starts..."
echo ""

# Function to open browser
open_browser() {
    sleep 15
    if command -v xdg-open &> /dev/null; then
        xdg-open http://localhost:8080
    elif command -v open &> /dev/null; then
        open http://localhost:8080
    elif command -v start &> /dev/null; then
        start http://localhost:8080
    else
        echo -e "${YELLOW}Please open http://localhost:8080 in your browser${NC}"
    fi
}

# Start browser opener in background
open_browser &

# Start the Spring Boot application
echo -e "${GREEN}Starting Spring Boot backend...${NC}"

# Check if Maven is available
if command -v mvn &> /dev/null; then
    mvn spring-boot:run
elif [ -f "./mvnw" ]; then
    echo "Maven not found, using Maven wrapper..."
    chmod +x ./mvnw
    ./mvnw spring-boot:run
else
    echo -e "${RED}ERROR: Neither Maven nor Maven wrapper found${NC}"
    echo "Please install Maven or ensure mvnw exists"
    exit 1
fi

echo ""
echo "========================================"
echo "  Application started successfully!"
echo "  Access: http://localhost:8080"
echo "  Login: admin / admin123"
echo "========================================"
echo ""

