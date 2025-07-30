package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Spring Boot application class for the Professional Portfolio application.
 * 
 * This application demonstrates:
 * - Spring Boot framework
 * - JPA/Hibernate for data persistence
 * - PostgreSQL database integration
 * - RESTful API design
 * - Modern Java features (Java 8+)
 * 
 * @author Portfolio Developer
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.portfolio.repository")
@EnableTransactionManagement
public class PortfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }
}

