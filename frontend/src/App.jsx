import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/common/Navbar';
import PrivateRoute from './components/common/PrivateRoute';
import LoginPage from './components/auth/LoginPage';
import RegisterPage from './components/auth/RegisterPage';
import Home from './components/Home';
import './App.css';

/**
 * Main App Component
 * Sets up routing and authentication context
 */
function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Navbar />

          <Routes>
            {/* Public routes */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />

            {/* Protected routes - require authentication */}
            <Route
              path="/"
              element={
                <PrivateRoute>
                  <Home />
                </PrivateRoute>
              }
            />

            {/* Placeholder routes for future phases */}
            <Route
              path="/review"
              element={
                <PrivateRoute>
                  <div style={{ padding: '40px', textAlign: 'center' }}>
                    <h1>Review Page</h1>
                    <p>Coming in Phase 2! 🚀</p>
                  </div>
                </PrivateRoute>
              }
            />

            <Route
              path="/cards"
              element={
                <PrivateRoute>
                  <div style={{ padding: '40px', textAlign: 'center' }}>
                    <h1>Cards Management</h1>
                    <p>Coming in Phase 3! 📝</p>
                  </div>
                </PrivateRoute>
              }
            />

            <Route
              path="/decks"
              element={
                <PrivateRoute>
                  <div style={{ padding: '40px', textAlign: 'center' }}>
                    <h1>Decks Management</h1>
                    <p>Coming in Phase 4! 🗂️</p>
                  </div>
                </PrivateRoute>
              }
            />

            {/* Catch-all route - redirect to home */}
                <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;

