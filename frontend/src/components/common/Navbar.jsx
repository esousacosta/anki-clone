import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import './Navbar.css';

/**
 * Navbar Component
 * Navigation bar displayed at the top of the app
 * Shows different options based on authentication status
 */
const Navbar = () => {
  const { isAuthenticated, username, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-logo">
          📚 Anki Clone
        </Link>

        <div className="navbar-links">
          {isAuthenticated() ? (
            <>
              <Link to="/" className="navbar-link">Home</Link>
              <Link to="/review" className="navbar-link">Review</Link>
              <Link to="/cards" className="navbar-link">Cards</Link>
              <Link to="/decks" className="navbar-link">Decks</Link>
              <span className="navbar-username">👤 {username}</span>
              <button onClick={handleLogout} className="navbar-logout">
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="navbar-link">Login</Link>
              <Link to="/register" className="navbar-link">Register</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

