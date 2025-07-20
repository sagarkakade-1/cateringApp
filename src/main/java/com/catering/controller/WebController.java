package com.catering.controller;

import com.catering.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

/**
 * Web Controller for serving Thymeleaf templates
 * Handles page navigation and view rendering
 */
@Controller
public class WebController {

    private final AuthService authService;

    @Autowired
    public WebController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Home page - redirects to dashboard if authenticated, otherwise to login
     */
    @GetMapping("/")
    public String home(HttpSession session) {
        if (authService.isAuthenticated(session)) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    /**
     * Login page
     */
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model, 
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout) {
        
        // If already authenticated, redirect to dashboard
        if (authService.isAuthenticated(session)) {
            return "redirect:/dashboard";
        }
        
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        
        return "auth/login";
    }

    /**
     * Handle login form submission
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                              @RequestParam String password,
                              HttpSession session) {
        
        boolean authenticated = authService.authenticate(username, password, session);
        
        if (authenticated) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login?error";
        }
    }

    /**
     * Handle logout
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/login?logout";
    }

    /**
     * Dashboard page
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        // Add user info to model
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        model.addAttribute("username", authService.getCurrentUsername(session));
        
        return "dashboard/index";
    }

    /**
     * Orders page
     */
    @GetMapping("/orders")
    public String orders(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "orders/index";
    }

    /**
     * New order page
     */
    @GetMapping("/orders/new")
    public String newOrder(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "orders/form";
    }

    /**
     * Employees page
     */
    @GetMapping("/employees")
    public String employees(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "employees/index";
    }

    /**
     * New employee page
     */
    @GetMapping("/employees/new")
    public String newEmployee(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "employees/form";
    }

    /**
     * Inventory page
     */
    @GetMapping("/inventory")
    public String inventory(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "inventory/index";
    }

    /**
     * Customers page
     */
    @GetMapping("/customers")
    public String customers(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "customers/index";
    }

    /**
     * Tasks page
     */
    @GetMapping("/tasks")
    public String tasks(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "tasks/index";
    }

    /**
     * Reports page
     */
    @GetMapping("/reports")
    public String reports(HttpSession session, Model model) {
        if (!authService.isAuthenticated(session)) {
            return "redirect:/login";
        }
        
        model.addAttribute("currentUser", authService.getCurrentUser(session).orElse(null));
        return "reports/index";
    }
}

