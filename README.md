# 🍽️ Catering Management System

A comprehensive full-stack web application for managing catering business operations. Built with Spring Boot backend, React frontend, and PostgreSQL database.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Manual Setup](#manual-setup)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Development](#development)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## ✨ Features

### 🔐 Authentication
- Simple admin login system
- Session-based authentication
- User profile management

### 📋 Order Management
- **Full Catering**: Complete service including cook, waiter, service boys, utensils, water cans, tables, transport
- **Half Catering**: Custom items chosen by user
- Order status tracking: Pending, In Progress, Completed, Cancelled
- CRUD operations for orders

### 👥 Employee Management
- Employee types: Cook, Bai, Waiter, Driver, Display Table Boy, Service Boy
- Track orders served per employee per day
- Calculate salary/payment based on orders served
- Search and filter by name, ID, or venue

### 📦 Inventory Management
- Track materials: Utensils, display tables, water cans, food items, equipment
- Log usage per order
- Low stock notifications
- Stock level monitoring

### 🤝 Customer & Client Management
- One-time customers and permanent clients
- Client types: Hotels, event managers, catering services, individuals
- Payment tracking: Advance, Remaining, Fully Paid
- Customer filtering and search

### ✅ Task Tracker
- Add tasks per order
- Task status: To-do, In Progress, Done, Deleted
- View tasks per employee or per order
- Task priority management

### 📊 Reports Module
- Employee-wise reports: orders completed, payment due
- Order-wise reports: resource summary, total cost, client details
- Monthly reports
- Export capabilities (PDF/CSV) - *Coming Soon*

## 🛠️ Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA (Hibernate)**
- **Spring Security**
- **PostgreSQL**
- **Maven**

### Frontend
- **React 18**
- **React Router DOM**
- **Bootstrap 5**
- **React Bootstrap**
- **Chart.js & React-Chartjs-2**
- **Axios**
- **React Toastify**

### Development Tools
- **Maven Frontend Plugin**
- **Spring Boot DevTools**
- **Embedded Tomcat Server**

## 📋 Prerequisites

Before running the application, ensure you have:

1. **Java 17 or higher**
   ```bash
   java -version
   ```

2. **PostgreSQL 12 or higher**
   ```bash
   psql --version
   ```

3. **Maven 3.6+ (optional - Maven wrapper included)**
   ```bash
   mvn -version
   ```

4. **Node.js 16+ and npm (for development only)**
   ```bash
   node --version
   npm --version
   ```

## 🚀 Quick Start

### Option 1: Double-Click Startup (Recommended)

1. **Download/Clone the project**
2. **Setup PostgreSQL database** (see Database Setup below)
3. **Double-click the startup script:**
   - **Windows**: Double-click `start-app.bat`
   - **Linux/Mac**: Double-click `start-app.sh` or run `./start-app.sh`

The application will automatically:
- Start the Spring Boot backend
- Build and serve the React frontend
- Open your browser to `http://localhost:8080`

### Option 2: Command Line

```bash
# Clone the repository
git clone <repository-url>
cd catering-management

# Start the application
./start-app.sh    # Linux/Mac
# or
start-app.bat     # Windows
```

## 🗄️ Database Setup

### Create PostgreSQL Database

```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create database and user
CREATE DATABASE catering_db;
CREATE USER catering_user WITH PASSWORD 'catering_pass';
GRANT ALL PRIVILEGES ON DATABASE catering_db TO catering_user;

-- Exit psql
\\q
```

### Alternative: Using Command Line

```bash
# Create database
createdb -U postgres catering_db

# Create user and grant privileges
psql -U postgres -c "CREATE USER catering_user WITH PASSWORD 'catering_pass';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE catering_db TO catering_user;"
```

## 🏗️ Manual Setup

If you prefer manual setup or need to customize the configuration:

### 1. Backend Setup

```bash
# Navigate to project root
cd catering-management

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

### 2. Frontend Setup (Development Only)

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm start
```

### 3. Access the Application

- **Production**: http://localhost:8080
- **Development**: http://localhost:3000 (frontend dev server)

## 📁 Project Structure

```
catering-management/
├── src/main/java/com/catering/
│   ├── entity/              # JPA Entities
│   ├── repository/          # Data Access Layer
│   ├── service/             # Business Logic
│   ├── controller/          # REST Controllers
│   ├── config/              # Configuration Classes
│   └── CateringApplication.java
├── src/main/resources/
│   ├── application.properties
│   ├── schema.sql           # Database Schema
│   └── data.sql             # Sample Data
├── frontend/
│   ├── src/
│   │   ├── components/      # React Components
│   │   ├── services/        # API Services
│   │   └── App.js
│   ├── public/
│   └── package.json
├── start-app.bat            # Windows Startup Script
├── start-app.sh             # Linux/Mac Startup Script
├── pom.xml                  # Maven Configuration
└── README.md
```

## 🔑 Default Login Credentials

```
Username: admin
Password: admin123
```

## 🌐 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | User login |
| POST | `/api/auth/logout` | User logout |
| GET | `/api/auth/status` | Check auth status |
| GET | `/api/auth/profile` | Get user profile |

### Core Module Endpoints (Coming Soon)

- `/api/orders` - Order management
- `/api/employees` - Employee management
- `/api/inventory` - Inventory management
- `/api/customers` - Customer management
- `/api/tasks` - Task management
- `/api/reports` - Reports and analytics

## 🗃️ Database Schema

The application uses a comprehensive PostgreSQL schema with the following main entities:

- **users** - System users and authentication
- **customers** - Client and customer information
- **employees** - Staff management
- **orders** - Order tracking and management
- **inventory** - Stock and material management
- **tasks** - Task assignment and tracking
- **order_employees** - Order-employee assignments
- **order_inventory** - Order-inventory usage

## 🔧 Configuration

### Application Properties

Key configuration options in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/catering_db
spring.datasource.username=catering_user
spring.datasource.password=catering_pass

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Environment Variables

You can override configuration using environment variables:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=catering_db
export DB_USER=catering_user
export DB_PASS=catering_pass
```

## 🚀 Development

### Running in Development Mode

1. **Backend Development**:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

2. **Frontend Development**:
   ```bash
   cd frontend
   npm start
   ```

### Building for Production

```bash
# Build the entire application
mvn clean package

# Run the built JAR
java -jar target/catering-management-1.0.0.jar
```

## 🐛 Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   ```bash
   # Kill process using port 8080
   lsof -ti:8080 | xargs kill -9  # Mac/Linux
   netstat -ano | findstr :8080   # Windows
   ```

2. **PostgreSQL connection failed**
   - Ensure PostgreSQL is running
   - Check database credentials
   - Verify database exists

3. **Java version issues**
   ```bash
   # Check Java version
   java -version
   
   # Set JAVA_HOME if needed
   export JAVA_HOME=/path/to/java17
   ```

4. **Maven not found**
   - Use the included Maven wrapper: `./mvnw` (Linux/Mac) or `mvnw.cmd` (Windows)

### Logs and Debugging

- **Application logs**: Check console output
- **Database logs**: Check PostgreSQL logs
- **Frontend logs**: Check browser developer console

## 📝 Development Roadmap

### Phase 1 (Current)
- ✅ Project structure and authentication
- ✅ Database schema and sample data
- ✅ Basic dashboard with mock data
- ✅ Responsive UI framework

### Phase 2 (Next)
- 🔄 Complete Order Management module
- 🔄 Employee Management with CRUD operations
- 🔄 Inventory Management with stock tracking
- 🔄 Customer Management with payment tracking

### Phase 3 (Future)
- 📋 Task Management with assignments
- 📊 Reports and Analytics module
- 📄 PDF/CSV export functionality
- 🔔 Real-time notifications

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:

- Create an issue in the repository
- Check the troubleshooting section
- Review the logs for error details

## 🎯 Target Users

This application is designed for:
- **Catering Business Owners** - Non-technical users who need comprehensive business management
- **Catering Managers** - Staff who handle day-to-day operations
- **Event Coordinators** - Users who manage multiple events and orders

---

**Made with ❤️ for the catering industry**

