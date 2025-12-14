import axios from "axios";

// Create an axios instance with base configuration
const api = axios.create({
  baseURL: "http://localhost:8080", // Your Spring Boot backend URL
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor - adds JWT token to all requests
api.interceptors.request.use(
  (config) => {
    // Get token from localStorage
    const token = localStorage.getItem("token");

    // If token exists, add it to the Authorization header
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor - handles errors globally
api.interceptors.response.use(
  (response) => {
    // If the response is successful, just return it
    return response;
  },
  (error) => {
    // Handle specific error cases
    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx

      if (error.response.status === 401) {
        // Unauthorized - token expired or invalid
        // Clear token and redirect to login
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        window.location.href = "/login";
      }

      // Return the error response for specific handling
      return Promise.reject(error.response.data);
    } else if (error.request) {
      // The request was made but no response was received
      console.error("No response received:", error.request);
      return Promise.reject({
        message: "Server is not responding. Please try again later.",
      });
    } else {
      // Something happened in setting up the request that triggered an Error
      console.error("Error:", error.message);
      return Promise.reject({
        message: "An error occurred. Please try again.",
      });
    }
  }
);

export default api;
