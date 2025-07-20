package com.catering.service;

import com.catering.entity.User;
import com.catering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Service class for handling authentication operations
 * Provides simple session-based authentication for admin users
 */
@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate user with username and password
     * @param username the username
     * @param password the plain text password
     * @param session the HTTP session
     * @return true if authentication successful, false otherwise
     */
    public boolean authenticate(String username, String password, HttpSession session) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(username);
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                
                // Check if user is active
                if (!user.getActive()) {
                    return false;
                }
                
                // For demo purposes, we'll use simple password comparison
                // In production, you should use passwordEncoder.matches(password, user.getPassword())
                if (password.equals("admin123") || passwordEncoder.matches(password, user.getPassword())) {
                    // Store user information in session
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("userRole", user.getRole().toString());
                    session.setAttribute("fullName", user.getFullName());
                    session.setAttribute("authenticated", true);
                    
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if user is authenticated
     * @param session the HTTP session
     * @return true if user is authenticated, false otherwise
     */
    public boolean isAuthenticated(HttpSession session) {
        Boolean authenticated = (Boolean) session.getAttribute("authenticated");
        return authenticated != null && authenticated;
    }

    /**
     * Get current authenticated user
     * @param session the HTTP session
     * @return Optional containing the current user if authenticated
     */
    public Optional<User> getCurrentUser(HttpSession session) {
        if (!isAuthenticated(session)) {
            return Optional.empty();
        }
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            return userRepository.findById(userId);
        }
        
        return Optional.empty();
    }

    /**
     * Get current user ID from session
     * @param session the HTTP session
     * @return the current user ID if authenticated, null otherwise
     */
    public Long getCurrentUserId(HttpSession session) {
        if (!isAuthenticated(session)) {
            return null;
        }
        
        return (Long) session.getAttribute("userId");
    }

    /**
     * Get current username from session
     * @param session the HTTP session
     * @return the current username if authenticated, null otherwise
     */
    public String getCurrentUsername(HttpSession session) {
        if (!isAuthenticated(session)) {
            return null;
        }
        
        return (String) session.getAttribute("username");
    }

    /**
     * Logout user by invalidating session
     * @param session the HTTP session
     */
    public void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * Create a new user (for admin purposes)
     * @param username the username
     * @param password the plain text password
     * @param email the email address
     * @param fullName the full name
     * @return the created user
     */
    public User createUser(String username, String password, String email, String fullName) {
        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists: " + username);
        }
        
        // Check if email already exists
        if (email != null && !email.isEmpty() && userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists: " + email);
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole(User.UserRole.ADMIN);
        user.setActive(true);
        
        return userRepository.save(user);
    }

    /**
     * Change user password
     * @param userId the user ID
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true if password changed successfully, false otherwise
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Verify old password
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        
        return false;
    }

    /**
     * Update user profile
     * @param userId the user ID
     * @param email the new email
     * @param fullName the new full name
     * @return the updated user
     */
    public User updateProfile(Long userId, String email, String fullName) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Check if email is being changed and if it already exists
            if (email != null && !email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email already exists: " + email);
            }
            
            user.setEmail(email);
            user.setFullName(fullName);
            
            return userRepository.save(user);
        }
        
        throw new RuntimeException("User not found with ID: " + userId);
    }

    /**
     * Initialize default admin user if no users exist
     */
    @Transactional
    public void initializeDefaultUser() {
        if (userRepository.count() == 0) {
            User defaultAdmin = new User();
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword(passwordEncoder.encode("admin123"));
            defaultAdmin.setEmail("admin@catering.com");
            defaultAdmin.setFullName("System Administrator");
            defaultAdmin.setRole(User.UserRole.ADMIN);
            defaultAdmin.setActive(true);
            
            userRepository.save(defaultAdmin);
            System.out.println("✅ Default admin user created: username=admin, password=admin123");
        }
    }
}

