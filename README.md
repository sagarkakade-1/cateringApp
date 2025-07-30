# 🚀 Professional Portfolio - Java Spring Boot Application

A high-quality, attractive, and rigorous portfolio application built with modern Java technologies and best practices. This application showcases professional experience, skills, projects, and education in an elegant, responsive web interface.

## 🌟 Features

### ✨ Frontend Features
- **Modern Responsive Design** - Mobile-first approach with elegant UI/UX
- **Interactive Animations** - Smooth scroll animations and hover effects
- **Dark/Light Theme Support** - Automatic theme detection and manual toggle
- **Progressive Web App** - Fast loading with caching strategies
- **Accessibility Compliant** - WCAG 2.1 AA compliant design
- **Cross-browser Compatible** - Works on all modern browsers

### 🔧 Backend Features
- **RESTful API Architecture** - Clean, well-documented APIs
- **Comprehensive Validation** - Input validation with custom error handling
- **Database Integration** - PostgreSQL with JPA/Hibernate
- **Error Handling** - Global exception handling with meaningful responses
- **Performance Optimized** - Connection pooling and query optimization
- **Security Features** - Input sanitization and CORS configuration

## 🛠️ Technology Stack

### Backend Technologies
- **Java 17** - Latest LTS version with modern features
- **Spring Boot 3.2.0** - Latest Spring Boot with enhanced performance
- **Spring MVC** - Web layer with RESTful controllers
- **Spring Data JPA** - Data persistence layer
- **Hibernate** - ORM for database operations
- **Bean Validation** - Input validation with annotations
- **PostgreSQL** - Production-ready relational database

### Frontend Technologies
- **HTML5** - Semantic markup with modern standards
- **CSS3** - Modern styling with CSS Grid, Flexbox, and animations
- **JavaScript ES6+** - Modern JavaScript with classes and modules
- **Font Awesome** - Professional icon library
- **Google Fonts** - Typography with web fonts

### Development Tools
- **Maven** - Dependency management and build automation
- **Spring Boot DevTools** - Hot reloading for development
- **Git** - Version control system
- **IntelliJ IDEA** - Recommended IDE

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17 or higher** - [Download Java](https://adoptium.net/)
- **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
- **PostgreSQL 12+** - [Download PostgreSQL](https://www.postgresql.org/download/)
- **Git** - [Download Git](https://git-scm.com/downloads)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/professional-portfolio.git
cd professional-portfolio
```

### 2. Database Setup
Create a PostgreSQL database:
```sql
-- Connect to PostgreSQL as superuser
CREATE DATABASE mapping;
CREATE USER postgres WITH PASSWORD 'root';
GRANT ALL PRIVILEGES ON DATABASE mapping TO postgres;
```

### 3. Configure Application
Update `src/main/resources/application.properties` if needed:
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/mapping
spring.datasource.username=postgres
spring.datasource.password=root
```

### 4. Build and Run
```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

### 5. Access the Application
- **Web Interface**: http://localhost:8080
- **API Documentation**: http://localhost:8080/api/portfolio
- **Health Check**: http://localhost:8080/actuator/health

## 🔌 API Endpoints

### Portfolio Management
- `GET /api/portfolio` - Get portfolio information
- `PUT /api/portfolio` - Update portfolio information

### Project Management
- `GET /api/portfolio/projects` - Get all projects
- `POST /api/portfolio/projects` - Create new project
- `GET /api/portfolio/projects/{id}` - Get project by ID
- `PUT /api/portfolio/projects/{id}` - Update project
- `DELETE /api/portfolio/projects/{id}` - Delete project

### Skill Management
- `GET /api/portfolio/skills` - Get all skills
- `POST /api/portfolio/skills` - Create new skill
- `GET /api/portfolio/skills/{id}` - Get skill by ID
- `PUT /api/portfolio/skills/{id}` - Update skill
- `DELETE /api/portfolio/skills/{id}` - Delete skill

## 🎨 Customization

### Personal Information
Update the following files with your information:
1. `src/main/resources/application.properties` - Contact details and social links
2. `src/main/resources/static/index.html` - Profile information
3. Database entries for projects, skills, and experience

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn verify
```

## 📦 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/professional-portfolio-1.0.0.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Your Name**
- Email: your.email@example.com
- LinkedIn: [Your LinkedIn Profile](https://linkedin.com/in/yourprofile)
- GitHub: [Your GitHub Profile](https://github.com/yourusername)

---

**Built with ❤️ using Java, Spring Boot, PostgreSQL, and modern web technologies**
