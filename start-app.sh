#!/bin/bash

echo "========================================"
echo "  🍽️  Catering Management System"
echo "========================================"
echo "Starting application..."
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "⚠️  Java version is $JAVA_VERSION, but Java 17+ is required"
    echo "Please upgrade to Java 17 or higher"
fi

# Check if PostgreSQL is running
echo "🔍 Checking PostgreSQL connection..."
if ! pg_isready -h localhost -p 5432 &> /dev/null; then
    echo "⚠️  PostgreSQL is not running or not accessible"
    echo "Please ensure PostgreSQL is running on localhost:5432"
    echo
    echo "To start PostgreSQL:"
    echo "- macOS: brew services start postgresql"
    echo "- Ubuntu: sudo systemctl start postgresql"
    echo "- Or start from pgAdmin"
    echo
    read -p "Press Enter to continue anyway..."
fi

# Create database if it doesn't exist
echo "🗄️  Setting up database..."
if ! psql -U postgres -lqt | cut -d \| -f 1 | grep -qw catering_db; then
    echo "Creating database catering_db..."
    createdb -U postgres catering_db
    if [ $? -ne 0 ]; then
        echo "❌ Failed to create database"
        echo "Please create the database manually:"
        echo "  createdb -U postgres catering_db"
        read -p "Press Enter to continue..."
    fi
fi

# Start the Spring Boot application
echo "🚀 Starting Catering Management System..."
echo
echo "========================================"
echo "📝 Login Credentials:"
echo "   Username: admin"
echo "   Password: admin123"
echo "========================================"
echo

# Open browser (cross-platform)
sleep 3 && {
    if command -v xdg-open &> /dev/null; then
        xdg-open "http://localhost:8080"
    elif command -v open &> /dev/null; then
        open "http://localhost:8080"
    elif command -v start &> /dev/null; then
        start "http://localhost:8080"
    fi
} &

# Start the application
if command -v mvn &> /dev/null; then
    mvn spring-boot:run
else
    echo "⚠️  Maven not found, trying with Maven wrapper..."
    ./mvnw spring-boot:run
fi

