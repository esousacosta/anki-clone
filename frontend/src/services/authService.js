import api from './api';

/**
 * Authentication Service
 * Handles all authentication-related API calls
 */

const authService = {
  /**
   * Login user
   * @param {string} username - User's username
   * @param {string} password - User's password
   * @returns {Promise} Response with token
   */
  login: async (username, password) => {
    try {
      const response = await api.post('/auth/login', {
        username,
        password,
      });

      // The backend returns: { token: "jwt_token_here" }
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Register new user
   * @param {string} username - Desired username
   * @param {string} password - Desired password
   * @returns {Promise} Response from server
   */
  register: async (username, password) => {
    try {
      const response = await api.post('/auth/register', {
        username,
        password,
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  },

  /**
   * Logout user
   * Calls the backend to blacklist the token
   * @returns {Promise} Response from server
   */
  logout: async () => {
    try {
      const response = await api.post('/auth/logout');
      return response.data;
    } catch (error) {
      // Even if the backend call fails, we should clear local storage
      throw error;
    }
  },
};

export default authService;

