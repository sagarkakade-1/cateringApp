package com.catering.controller;

import com.catering.entity.User;
import com.catering.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for authentication operations
 * Handles login, logout, and user session management
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login endpoint
     * @param loginRequest the login request containing username and password
     * @param session the HTTP session
     * @return ResponseEntity with login result
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginRequest loginRequest, 
                                                   HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean authenticated = authService.authenticate(
                loginRequest.getUsername(), 
                loginRequest.getPassword(), 
                session
            );
            
            if (authenticated) {
                Optional<User> currentUser = authService.getCurrentUser(session);
                
                response.put("success", true);
                response.put("message", "Login successful");
                
                if (currentUser.isPresent()) {
                    User user = currentUser.get();
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", user.getId());
                    userInfo.put("username", user.getUsername());
                    userInfo.put("fullName", user.getFullName());
                    userInfo.put("email", user.getEmail());
                    userInfo.put("role", user.getRole().toString());
                    
                    response.put("user", userInfo);
                }
                
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Logout endpoint
     * @param session the HTTP session
     * @return ResponseEntity with logout result
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            authService.logout(session);
            response.put("success", true);
            response.put("message", "Logout successful");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Logout failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Check authentication status
     * @param session the HTTP session
     * @return ResponseEntity with authentication status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean authenticated = authService.isAuthenticated(session);
            response.put("authenticated", authenticated);
            
            if (authenticated) {
                Optional<User> currentUser = authService.getCurrentUser(session);
                
                if (currentUser.isPresent()) {
                    User user = currentUser.get();
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", user.getId());
                    userInfo.put("username", user.getUsername());
                    userInfo.put("fullName", user.getFullName());
                    userInfo.put("email", user.getEmail());
                    userInfo.put("role", user.getRole().toString());
                    
                    response.put("user", userInfo);
                }
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("authenticated", false);
            response.put("message", "Error checking authentication status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get current user profile
     * @param session the HTTP session
     * @return ResponseEntity with user profile
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!authService.isAuthenticated(session)) {
                response.put("success", false);
                response.put("message", "User not authenticated");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Optional<User> currentUser = authService.getCurrentUser(session);
            
            if (currentUser.isPresent()) {
                User user = currentUser.get();
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("username", user.getUsername());
                userInfo.put("fullName", user.getFullName());
                userInfo.put("email", user.getEmail());
                userInfo.put("role", user.getRole().toString());
                userInfo.put("createdAt", user.getCreatedAt());
                
                response.put("success", true);
                response.put("user", userInfo);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Update user profile
     * @param profileRequest the profile update request
     * @param session the HTTP session
     * @return ResponseEntity with update result
     */
    @PutMapping("/profile")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody @Valid ProfileUpdateRequest profileRequest,
                                                           HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!authService.isAuthenticated(session)) {
                response.put("success", false);
                response.put("message", "User not authenticated");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Long userId = authService.getCurrentUserId(session);
            User updatedUser = authService.updateProfile(userId, profileRequest.getEmail(), profileRequest.getFullName());
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", updatedUser.getId());
            userInfo.put("username", updatedUser.getUsername());
            userInfo.put("fullName", updatedUser.getFullName());
            userInfo.put("email", updatedUser.getEmail());
            userInfo.put("role", updatedUser.getRole().toString());
            
            response.put("success", true);
            response.put("message", "Profile updated successfully");
            response.put("user", userInfo);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Change password
     * @param passwordRequest the password change request
     * @param session the HTTP session
     * @return ResponseEntity with change result
     */
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody @Valid PasswordChangeRequest passwordRequest,
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!authService.isAuthenticated(session)) {
                response.put("success", false);
                response.put("message", "User not authenticated");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Long userId = authService.getCurrentUserId(session);
            boolean changed = authService.changePassword(userId, passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
            
            if (changed) {
                response.put("success", true);
                response.put("message", "Password changed successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid old password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error changing password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Request DTOs
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class ProfileUpdateRequest {
        private String email;
        private String fullName;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }

    public static class PasswordChangeRequest {
        private String oldPassword;
        private String newPassword;

        public String getOldPassword() { return oldPassword; }
        public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}

