package com.catering;

import com.catering.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot Application class for Catering Management System
 * 
 * This application provides a comprehensive solution for managing catering business operations
 * including orders, employees, inventory, customers, tasks, and reports.
 * 
 * Features:
 * - Order Management (Full Catering & Half Catering)
 * - Employee Management with role-based assignments
 * - Inventory Management with stock tracking
 * - Customer & Client Management
 * - Task Tracking and Assignment
 * - Reports and Analytics
 * 
 * The application runs on embedded Tomcat server and serves both REST API and React frontend.
 */
@SpringBootApplication
public class CateringApplication implements WebMvcConfigurer {

    @Autowired
    private AuthService authService;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  🍽️  Catering Management System");
        System.out.println("========================================");
        System.out.println("Starting application...");
        
        SpringApplication.run(CateringApplication.class, args);
        
        System.out.println("========================================");
        System.out.println("✅ Application started successfully!");
        System.out.println("🌐 Access: http://localhost:8080");
        System.out.println("🔑 Login: admin / admin123");
        System.out.println("========================================");
    }

    /**
     * Configure view controllers for React routing
     * This ensures that React Router handles client-side routing properly
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Forward all non-API routes to index.html for React Router
        registry.addViewController("/{spring:\\w+}")
                .setViewName("forward:/");
        registry.addViewController("/**/{spring:\\w+}")
                .setViewName("forward:/");
        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
                .setViewName("forward:/");
    }

    /**
     * Initialize default data on application startup
     * This creates the default admin user if no users exist
     */
    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            System.out.println("🔧 Initializing application data...");
            
            try {
                // Initialize default admin user
                authService.initializeDefaultUser();
                
                System.out.println("✅ Application data initialized successfully");
                System.out.println("📝 Default admin user: admin / admin123");
                
            } catch (Exception e) {
                System.err.println("❌ Error initializing application data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}

