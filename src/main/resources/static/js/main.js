/**
 * Portfolio JavaScript - Modern & Interactive
 * Features: Dynamic loading, form validation, animations, API integration
 */

// Global configuration
const CONFIG = {
    API_BASE_URL: '/api/portfolio',
    ANIMATION_DURATION: 300,
    DEBOUNCE_DELAY: 500,
    MAX_RETRIES: 3
};

// Utility functions
const Utils = {
    // Debounce function for performance optimization
    debounce: (func, wait) => {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },

    // Format date for display
    formatDate: (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString('en-US', options);
    },

    // Show loading state
    showLoading: (element) => {
        if (element) {
            element.classList.add('loading');
            element.disabled = true;
        }
    },

    // Hide loading state
    hideLoading: (element) => {
        if (element) {
            element.classList.remove('loading');
            element.disabled = false;
        }
    },

    // Show alert message
    showAlert: (message, type = 'success') => {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.textContent = message;
        
        const container = document.querySelector('.container');
        if (container) {
            container.insertBefore(alertDiv, container.firstChild);
            
            // Auto-remove after 5 seconds
            setTimeout(() => {
                if (alertDiv.parentNode) {
                    alertDiv.parentNode.removeChild(alertDiv);
                }
            }, 5000);
        }
    },

    // Smooth scroll to element
    scrollToElement: (elementId) => {
        const element = document.getElementById(elementId);
        if (element) {
            const offsetTop = element.offsetTop - 80; // Account for fixed navbar
            window.scrollTo({
                top: offsetTop,
                behavior: 'smooth'
            });
        }
    },

    // Validate email format
    isValidEmail: (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    },

    // Generate unique ID
    generateId: () => {
        return Date.now().toString(36) + Math.random().toString(36).substr(2);
    }
};

// API service for backend communication
const ApiService = {
    // Generic API call with error handling and retries
    async makeRequest(url, options = {}, retries = CONFIG.MAX_RETRIES) {
        try {
            const response = await fetch(`${CONFIG.API_BASE_URL}${url}`, {
                headers: {
                    'Content-Type': 'application/json',
                    ...options.headers
                },
                ...options
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            if (retries > 0) {
                console.warn(`Request failed, retrying... (${retries} attempts left)`);
                await new Promise(resolve => setTimeout(resolve, 1000));
                return this.makeRequest(url, options, retries - 1);
            }
            throw error;
        }
    },

    // Get portfolio data
    async getPortfolio() {
        return this.makeRequest('/');
    },

    // Get all projects
    async getProjects() {
        return this.makeRequest('/projects');
    },

    // Get all skills
    async getSkills() {
        return this.makeRequest('/skills');
    },

    // Create new project
    async createProject(projectData) {
        return this.makeRequest('/projects', {
            method: 'POST',
            body: JSON.stringify(projectData)
        });
    },

    // Update project
    async updateProject(id, projectData) {
        return this.makeRequest(`/projects/${id}`, {
            method: 'PUT',
            body: JSON.stringify(projectData)
        });
    },

    // Delete project
    async deleteProject(id) {
        return this.makeRequest(`/projects/${id}`, {
            method: 'DELETE'
        });
    },

    // Create new skill
    async createSkill(skillData) {
        return this.makeRequest('/skills', {
            method: 'POST',
            body: JSON.stringify(skillData)
        });
    },

    // Update skill
    async updateSkill(id, skillData) {
        return this.makeRequest(`/skills/${id}`, {
            method: 'PUT',
            body: JSON.stringify(skillData)
        });
    },

    // Delete skill
    async deleteSkill(id) {
        return this.makeRequest(`/skills/${id}`, {
            method: 'DELETE'
        });
    }
};

// Portfolio data manager
class PortfolioManager {
    constructor() {
        this.data = {
            portfolio: null,
            projects: [],
            skills: []
        };
        this.isLoading = false;
    }

    // Initialize portfolio data
    async init() {
        try {
            this.isLoading = true;
            const [portfolio, projects, skills] = await Promise.all([
                ApiService.getPortfolio(),
                ApiService.getProjects(),
                ApiService.getSkills()
            ]);

            this.data = { portfolio, projects, skills };
            this.render();
        } catch (error) {
            console.error('Failed to load portfolio data:', error);
            Utils.showAlert('Failed to load portfolio data. Please refresh the page.', 'error');
        } finally {
            this.isLoading = false;
        }
    }

    // Render all sections
    render() {
        this.renderProjects();
        this.renderSkills();
        this.addAnimations();
    }

    // Render projects section
    renderProjects() {
        const projectsContainer = document.getElementById('projects-container');
        if (!projectsContainer || !this.data.projects.length) return;

        const projectsHTML = this.data.projects.map(project => `
            <div class="project-card fade-in-up" data-project-id="${project.id}">
                <div class="project-image">
                    <i class="fas fa-code"></i>
                </div>
                <div class="project-content">
                    <h3 class="project-title">${project.title}</h3>
                    <p class="project-description">${project.description}</p>
                    <div class="tech-stack">
                        ${project.technologies ? project.technologies.split(',').map(tech => 
                            `<span class="tech-tag">${tech.trim()}</span>`
                        ).join('') : ''}
                    </div>
                    <div class="project-links">
                        ${project.githubUrl ? `<a href="${project.githubUrl}" class="btn btn-outline" target="_blank">
                            <i class="fab fa-github"></i> View Code
                        </a>` : ''}
                        ${project.liveUrl ? `<a href="${project.liveUrl}" class="btn btn-primary" target="_blank">
                            <i class="fas fa-external-link-alt"></i> Live Demo
                        </a>` : ''}
                    </div>
                </div>
            </div>
        `).join('');

        projectsContainer.innerHTML = projectsHTML;
    }

    // Render skills section
    renderSkills() {
        const skillsContainer = document.getElementById('skills-container');
        if (!skillsContainer || !this.data.skills.length) return;

        // Group skills by category
        const skillsByCategory = this.data.skills.reduce((acc, skill) => {
            const category = skill.category || 'Other';
            if (!acc[category]) acc[category] = [];
            acc[category].push(skill);
            return acc;
        }, {});

        const skillsHTML = Object.entries(skillsByCategory).map(([category, skills]) => `
            <div class="skill-category slide-in-left">
                <h3>${category}</h3>
                <ul class="skill-list">
                    ${skills.map(skill => `
                        <li class="skill-item">
                            <span class="skill-name">${skill.name}</span>
                            <div class="skill-level">
                                ${this.renderSkillDots(skill.level)}
                            </div>
                        </li>
                    `).join('')}
                </ul>
            </div>
        `).join('');

        skillsContainer.innerHTML = skillsHTML;
    }

    // Render skill level dots
    renderSkillDots(level) {
        const maxDots = 5;
        const filledDots = Math.min(Math.max(level, 0), maxDots);
        const levelClass = this.getSkillLevelClass(level);
        
        return Array.from({ length: maxDots }, (_, i) => 
            `<span class="skill-dot ${i < filledDots ? `filled ${levelClass}` : ''}"></span>`
        ).join('');
    }

    // Get CSS class for skill level
    getSkillLevelClass(level) {
        if (level >= 5) return 'expert';
        if (level >= 4) return 'advanced';
        if (level >= 3) return 'intermediate';
        return 'beginner';
    }

    // Add scroll animations
    addAnimations() {
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, observerOptions);

        // Observe all animated elements
        document.querySelectorAll('.fade-in-up, .slide-in-left, .slide-in-right').forEach(el => {
            el.style.opacity = '0';
            el.style.transform = 'translateY(30px)';
            observer.observe(el);
        });
    }
}

// Form handler for contact and admin forms
class FormHandler {
    constructor() {
        this.forms = new Map();
        this.validators = new Map();
        this.init();
    }

    init() {
        // Initialize all forms on the page
        document.querySelectorAll('form').forEach(form => {
            this.setupForm(form);
        });
    }

    setupForm(form) {
        const formId = form.id || Utils.generateId();
        form.id = formId;
        
        this.forms.set(formId, form);
        this.setupValidation(form);
        this.setupSubmission(form);
    }

    setupValidation(form) {
        const inputs = form.querySelectorAll('input, textarea, select');
        
        inputs.forEach(input => {
            // Real-time validation
            input.addEventListener('blur', () => this.validateField(input));
            input.addEventListener('input', Utils.debounce(() => {
                if (input.classList.contains('error')) {
                    this.validateField(input);
                }
            }, CONFIG.DEBOUNCE_DELAY));
        });
    }

    validateField(field) {
        const value = field.value.trim();
        const fieldName = field.name || field.id;
        let isValid = true;
        let errorMessage = '';

        // Remove existing error state
        field.classList.remove('error');
        const existingError = field.parentNode.querySelector('.error-message');
        if (existingError) {
            existingError.remove();
        }

        // Required field validation
        if (field.hasAttribute('required') && !value) {
            isValid = false;
            errorMessage = `${fieldName} is required`;
        }

        // Email validation
        if (field.type === 'email' && value && !Utils.isValidEmail(value)) {
            isValid = false;
            errorMessage = 'Please enter a valid email address';
        }

        // Minimum length validation
        const minLength = field.getAttribute('minlength');
        if (minLength && value.length < parseInt(minLength)) {
            isValid = false;
            errorMessage = `${fieldName} must be at least ${minLength} characters long`;
        }

        // Maximum length validation
        const maxLength = field.getAttribute('maxlength');
        if (maxLength && value.length > parseInt(maxLength)) {
            isValid = false;
            errorMessage = `${fieldName} must be no more than ${maxLength} characters long`;
        }

        // Show error if validation failed
        if (!isValid) {
            field.classList.add('error');
            const errorDiv = document.createElement('div');
            errorDiv.className = 'error-message';
            errorDiv.textContent = errorMessage;
            field.parentNode.appendChild(errorDiv);
        }

        return isValid;
    }

    validateForm(form) {
        const inputs = form.querySelectorAll('input, textarea, select');
        let isValid = true;

        inputs.forEach(input => {
            if (!this.validateField(input)) {
                isValid = false;
            }
        });

        return isValid;
    }

    setupSubmission(form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            if (!this.validateForm(form)) {
                Utils.showAlert('Please fix the errors in the form', 'error');
                return;
            }

            const submitBtn = form.querySelector('button[type="submit"]');
            Utils.showLoading(submitBtn);

            try {
                await this.handleFormSubmission(form);
                Utils.showAlert('Form submitted successfully!', 'success');
                form.reset();
            } catch (error) {
                console.error('Form submission error:', error);
                Utils.showAlert('Failed to submit form. Please try again.', 'error');
            } finally {
                Utils.hideLoading(submitBtn);
            }
        });
    }

    async handleFormSubmission(form) {
        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        
        // Handle different form types
        switch (form.dataset.type) {
            case 'project':
                if (data.id) {
                    await ApiService.updateProject(data.id, data);
                } else {
                    await ApiService.createProject(data);
                }
                break;
            case 'skill':
                if (data.id) {
                    await ApiService.updateSkill(data.id, data);
                } else {
                    await ApiService.createSkill(data);
                }
                break;
            default:
                // Generic form submission
                console.log('Form data:', data);
        }
    }
}

// Navigation handler
class NavigationHandler {
    constructor() {
        this.activeSection = '';
        this.init();
    }

    init() {
        this.setupSmoothScrolling();
        this.setupActiveNavigation();
        this.setupMobileMenu();
    }

    setupSmoothScrolling() {
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', (e) => {
                e.preventDefault();
                const targetId = anchor.getAttribute('href').substring(1);
                Utils.scrollToElement(targetId);
            });
        });
    }

    setupActiveNavigation() {
        const sections = document.querySelectorAll('section[id]');
        const navLinks = document.querySelectorAll('.nav-item a');

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const sectionId = entry.target.id;
                    
                    // Update active nav link
                    navLinks.forEach(link => {
                        link.classList.remove('active');
                        if (link.getAttribute('href') === `#${sectionId}`) {
                            link.classList.add('active');
                        }
                    });
                }
            });
        }, {
            threshold: 0.3,
            rootMargin: '-80px 0px -80px 0px'
        });

        sections.forEach(section => observer.observe(section));
    }

    setupMobileMenu() {
        // Add mobile menu toggle if needed
        const navbar = document.querySelector('.navbar');
        if (navbar && window.innerWidth <= 768) {
            // Mobile menu implementation would go here
        }
    }
}

// Theme manager for dark/light mode
class ThemeManager {
    constructor() {
        this.currentTheme = localStorage.getItem('theme') || 'light';
        this.init();
    }

    init() {
        this.applyTheme(this.currentTheme);
        this.setupThemeToggle();
    }

    applyTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
        this.currentTheme = theme;
        localStorage.setItem('theme', theme);
    }

    toggleTheme() {
        const newTheme = this.currentTheme === 'light' ? 'dark' : 'light';
        this.applyTheme(newTheme);
    }

    setupThemeToggle() {
        const themeToggle = document.getElementById('theme-toggle');
        if (themeToggle) {
            themeToggle.addEventListener('click', () => this.toggleTheme());
        }
    }
}

// Performance monitor
class PerformanceMonitor {
    constructor() {
        this.metrics = {
            loadTime: 0,
            renderTime: 0,
            apiCalls: 0
        };
        this.init();
    }

    init() {
        // Monitor page load time
        window.addEventListener('load', () => {
            this.metrics.loadTime = performance.now();
            console.log(`Page loaded in ${this.metrics.loadTime.toFixed(2)}ms`);
        });

        // Monitor API calls
        this.monitorApiCalls();
    }

    monitorApiCalls() {
        const originalFetch = window.fetch;
        let callCount = 0;

        window.fetch = async (...args) => {
            callCount++;
            const startTime = performance.now();
            
            try {
                const response = await originalFetch(...args);
                const endTime = performance.now();
                console.log(`API call ${callCount} took ${(endTime - startTime).toFixed(2)}ms`);
                return response;
            } catch (error) {
                console.error(`API call ${callCount} failed:`, error);
                throw error;
            }
        };
    }
}

// Main application initialization
class PortfolioApp {
    constructor() {
        this.portfolioManager = new PortfolioManager();
        this.formHandler = new FormHandler();
        this.navigationHandler = new NavigationHandler();
        this.themeManager = new ThemeManager();
        this.performanceMonitor = new PerformanceMonitor();
    }

    async init() {
        try {
            console.log('Initializing Portfolio App...');
            
            // Initialize portfolio data
            await this.portfolioManager.init();
            
            // Setup error handling
            this.setupErrorHandling();
            
            // Setup keyboard shortcuts
            this.setupKeyboardShortcuts();
            
            console.log('Portfolio App initialized successfully!');
        } catch (error) {
            console.error('Failed to initialize Portfolio App:', error);
            Utils.showAlert('Failed to initialize application. Please refresh the page.', 'error');
        }
    }

    setupErrorHandling() {
        window.addEventListener('error', (event) => {
            console.error('Global error:', event.error);
            Utils.showAlert('An unexpected error occurred. Please refresh the page.', 'error');
        });

        window.addEventListener('unhandledrejection', (event) => {
            console.error('Unhandled promise rejection:', event.reason);
            Utils.showAlert('An unexpected error occurred. Please refresh the page.', 'error');
        });
    }

    setupKeyboardShortcuts() {
        document.addEventListener('keydown', (e) => {
            // Ctrl/Cmd + K for search (if implemented)
            if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
                e.preventDefault();
                // Implement search functionality
            }
            
            // Escape key to close modals
            if (e.key === 'Escape') {
                const modals = document.querySelectorAll('.modal.show');
                modals.forEach(modal => modal.classList.remove('show'));
            }
        });
    }
}

// Initialize application when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    const app = new PortfolioApp();
    app.init();
});

// Export for testing purposes
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        Utils,
        ApiService,
        PortfolioManager,
        FormHandler,
        NavigationHandler,
        ThemeManager,
        PerformanceMonitor,
        PortfolioApp
    };
}
