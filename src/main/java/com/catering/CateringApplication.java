package com.catering;

import com.catering.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
 * The application uses Thymeleaf templates for server-side rendering with HTML, CSS, and JavaScript.
 */
@SpringBootApplication
public class CateringApplication {

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

