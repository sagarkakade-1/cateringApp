import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || '';

// Configure axios defaults
axios.defaults.withCredentials = true;

class AuthService {
  constructor() {
    this.baseURL = `${API_BASE_URL}/api/auth`;
  }

  /**
   * Login with username and password
   * @param {string} username 
   * @param {string} password 
   * @returns {Promise} Login response
   */
  async login(username, password) {
    try {
      const response = await axios.post(`${this.baseURL}/login`, {
        username,
        password
      });
      
      if (response.data.success) {
        // Store user info in localStorage for persistence
        localStorage.setItem('user', JSON.stringify(response.data.user));
        localStorage.setItem('authenticated', 'true');
      }
      
      return response.data;
    } catch (error) {
      console.error('Login error:', error);
      if (error.response && error.response.data) {
        throw new Error(error.response.data.message || 'Login failed');
      }
      throw new Error('Network error during login');
    }
  }

  /**
   * Logout current user
   * @returns {Promise} Logout response
   */
  async logout() {
    try {
      const response = await axios.post(`${this.baseURL}/logout`);
      
      // Clear local storage
      localStorage.removeItem('user');
      localStorage.removeItem('authenticated');
      
      return response.data;
    } catch (error) {
      console.error('Logout error:', error);
      // Clear local storage even if logout request fails
      localStorage.removeItem('user');
      localStorage.removeItem('authenticated');
      throw error;
    }
  }

  /**
   * Check if user is authenticated
   * @returns {Promise<boolean>} Authentication status
   */
  async isAuthenticated() {
    try {
      // First check localStorage
      const localAuth = localStorage.getItem('authenticated');
      if (!localAuth) {
        return false;
      }

      // Verify with server
      const response = await axios.get(`${this.baseURL}/status`);
      
      if (response.data.authenticated) {
        // Update user info if available
        if (response.data.user) {
          localStorage.setItem('user', JSON.stringify(response.data.user));
        }
        return true;
      } else {
        // Clear local storage if server says not authenticated
        localStorage.removeItem('user');
        localStorage.removeItem('authenticated');
        return false;
      }
    } catch (error) {
      console.error('Auth check error:', error);
      // Clear local storage on error
      localStorage.removeItem('user');
      localStorage.removeItem('authenticated');
      return false;
    }
  }

  /**
   * Get current user info
   * @returns {Object|null} Current user or null
   */
  getCurrentUser() {
    try {
      const userStr = localStorage.getItem('user');
      return userStr ? JSON.parse(userStr) : null;
    } catch (error) {
      console.error('Error getting current user:', error);
      return null;
    }
  }

  /**
   * Get user profile from server
   * @returns {Promise} User profile response
   */
  async getProfile() {
    try {
      const response = await axios.get(`${this.baseURL}/profile`);
      
      if (response.data.success && response.data.user) {
        // Update localStorage with fresh user data
        localStorage.setItem('user', JSON.stringify(response.data.user));
      }
      
      return response.data;
    } catch (error) {
      console.error('Get profile error:', error);
      if (error.response && error.response.data) {
        throw new Error(error.response.data.message || 'Failed to get profile');
      }
      throw new Error('Network error while getting profile');
    }
  }

  /**
   * Update user profile
   * @param {string} email 
   * @param {string} fullName 
   * @returns {Promise} Update response
   */
  async updateProfile(email, fullName) {
    try {
      const response = await axios.put(`${this.baseURL}/profile`, {
        email,
        fullName
      });
      
      if (response.data.success && response.data.user) {
        // Update localStorage with updated user data
        localStorage.setItem('user', JSON.stringify(response.data.user));
      }
      
      return response.data;
    } catch (error) {
      console.error('Update profile error:', error);
      if (error.response && error.response.data) {
        throw new Error(error.response.data.message || 'Failed to update profile');
      }
      throw new Error('Network error while updating profile');
    }
  }

  /**
   * Change user password
   * @param {string} oldPassword 
   * @param {string} newPassword 
   * @returns {Promise} Change password response
   */
  async changePassword(oldPassword, newPassword) {
    try {
      const response = await axios.post(`${this.baseURL}/change-password`, {
        oldPassword,
        newPassword
      });
      
      return response.data;
    } catch (error) {
      console.error('Change password error:', error);
      if (error.response && error.response.data) {
        throw new Error(error.response.data.message || 'Failed to change password');
      }
      throw new Error('Network error while changing password');
    }
  }

  /**
   * Check if user has specific role
   * @param {string} role 
   * @returns {boolean} True if user has the role
   */
  hasRole(role) {
    const user = this.getCurrentUser();
    return user && user.role === role;
  }

  /**
   * Check if user is admin
   * @returns {boolean} True if user is admin
   */
  isAdmin() {
    return this.hasRole('ADMIN');
  }
}

// Create and export a singleton instance
export const authService = new AuthService();
export default authService;

