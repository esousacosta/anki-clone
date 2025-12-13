import React, { createContext, useState, useContext, useEffect } from 'react';
import authService from '../services/authService';

// Create the AuthContext
/**
 * React Context for authentication state and actions.
 *
 * The context value should be either null (when no provider is present)
 * or an object containing authentication-related state and functions, for example:
 *
 * {
 *   user: Object|null,                // currently authenticated user or null
 *   token: string|null,               // authentication token if available
 *   isAuthenticated: boolean,         // whether a user is authenticated
 *   loading: boolean,                 // whether authentication state is being resolved
 *   error: Error|null,                // last authentication error, if any
 *   login: (credentials) => Promise,  // function to sign a user in
 *   logout: () => void,               // function to sign a user out
 *   register?: (data) => Promise,     // optional function to register a new user
 *   refreshToken?: () => Promise      // optional function to refresh auth token
 * }
 *
 * The context is created with a default value of null to allow consumers
 * to detect the absence of a provider.
 *
 * @type {import('react').Context<{
 *   user: any | null;
 *   token: string | null;
 *   isAuthenticated: boolean;
 *   loading: boolean;
 *   error: any | null;
 *   login: (credentials: any) => Promise<any>;
 *   logout: () => void;
 *   register?: (data: any) => Promise<any>;
 *   refreshToken?: () => Promise<any>;
 * } | null>}
 */
/**
 * React context that provides authentication state and related actions to the component tree.
 *
 * A Context in React is an object created by React.createContext that enables sharing values
 * (such as state and functions) across a component tree without the need to pass props
 * through every intermediate component ("prop drilling"). Consumers of this AuthContext
 * can read authentication state and invoke actions (e.g., login, logout) from any nested
 * component wrapped by the corresponding provider.
 *
 * @typedef {Object} AuthContextValue
 * @property {Object|null} user - The authenticated user object, or null if not authenticated.
 * @property {boolean} isAuthenticated - True when a user is authenticated.
 * @property {boolean} loading - True while authentication status is being determined.
 * @property {(credentials: Object) => Promise<void>} login - Authenticate a user with given credentials.
 * @property {() => Promise<void>} logout - Sign out the current user.
 * @property {() => Promise<void>} [refresh] - Optional: refresh authentication/token.
 *
 * @constant {React.Context<AuthContextValue|null>} AuthContext - Provides AuthContextValue or null.
 * @see https://reactjs.org/docs/context.html
 */
const AuthContext = createContext(null);

/**
 * AuthProvider Component
 * Wraps the app and provides authentication state and methods to all components
 */
/**
 * AuthProvider - React Context provider that manages authentication state and exposes
 * authentication actions to descendant components.
 *
 * This provider initializes its state by reading a stored token and username from
 * localStorage on mount and exposes a context value that includes the token,
 * username, isLoading flag, and helper methods for login, registration, logout,
 * and authentication checking.
 *
 * Behavior:
 * - On mount: reads 'token' and 'username' from localStorage; sets state accordingly;
 *   sets isLoading to false when initialization completes.
 * - login(username, password): calls authService.login to obtain a token, saves the
 *   token and username to localStorage, and updates state.
 * - register(username, password): calls authService.register to create a new user.
 * - logout(): attempts to call authService.logout to blacklist the token on the backend;
 *   regardless of backend success/failure, clears token and username from localStorage
 *   and resets state.
 * - isAuthenticated(): returns a boolean indicating whether a token is present.
 *
 * What is a Context provider?
 * A Context provider in React is a component that wraps child components and makes a
 * shared value available to all descendants without passing props through every level.
 * Consumers access the provided value via useContext(SomeContext) or a Context.Consumer.
 *
 * @component
 * @param {Object} props
 * @param {React.ReactNode} props.children - Child elements that will receive the context.
 * @returns {JSX.Element} The AuthContext.Provider wrapping the children.
 *
 * Provided context shape:
 * @property {string|null} token - JWT token string, or null when unauthenticated.
 * @property {string|null} username - Authenticated user's username, or null.
 * @property {boolean} isLoading - True while initial auth state is being determined.
 * @property {() => boolean} isAuthenticated - Returns true if a token exists.
 * @property {(username: string, password: string) => Promise<{success: boolean, error?: string}>} login - Performs login and persists credentials.
 * @property {(username: string, password: string) => Promise<{success: boolean, error?: string}>} register - Registers a new user.
 * @property {() => Promise<void>} logout - Logs out, attempts backend invalidation, and clears stored credentials.
 */
export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);
  const [username, setUsername] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  // On component mount, check if user is already logged in
  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUsername = localStorage.getItem('username');

    if (storedToken && storedUsername) {
      setToken(storedToken);
      setUsername(storedUsername);
    }

    setIsLoading(false);
  }, []);

  /**
   * Login function
   * Calls the backend, stores token, and updates state
   */
  const login = async (username, password) => {
    try {
      const response = await authService.login(username, password);

      // Store token and username
      localStorage.setItem('token', response.token);
      localStorage.setItem('username', username);

      // Update state
      setToken(response.token);
      setUsername(username);

      return { success: true };
    } catch (error) {
      console.error('Login error:', error);
      return {
        success: false,
        error: error.message || 'Login failed. Please check your credentials.'
      };
    }
  };

  /**
   * Register function
   * Creates a new user account
   */
  const register = async (username, password) => {
    try {
      await authService.register(username, password);
      return { success: true };
    } catch (error) {
      console.error('Registration error:', error);
      return {
        success: false,
        error: error.message || 'Registration failed. Username may already exist.'
      };
    }
  };

  /**
   * Logout function
   * Calls backend to blacklist token, clears local storage, and updates state
   */
  const logout = async () => {
    try {
      // Call backend to blacklist the token
      await authService.logout();
    } catch (error) {
      console.error('Logout error:', error);
      // Continue with logout even if backend call fails
    } finally {
      // Clear local storage and state
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      setToken(null);
      setUsername(null);
    }
  };

  /**
   * Check if user is authenticated
   */
  const isAuthenticated = () => {
    return !!token;
  };

  // Context value that will be provided to consuming components
  const value = {
    token,
    username,
    isAuthenticated,
    login,
    register,
    logout,
    isLoading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

/**
 * Custom React hook that accesses the AuthContext and returns its current value.
 *
 * Uses React's useContext(AuthContext) under the hood. If the hook is invoked
 * outside of an AuthProvider, it throws an Error to help ensure correct usage.
 *
 * @function useAuth
 * @returns {AuthContextValue|object} The current authentication context value
 *   (typically includes the authenticated user, tokens, and auth helper functions).
 * @throws {Error} If called outside of an AuthProvider.
 *
 * @example
 * const { user, login, logout } = useAuth();
 */
export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }

  return context;
};

