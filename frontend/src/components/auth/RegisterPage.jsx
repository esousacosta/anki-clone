import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import './AuthPages.css';

/**
 * RegisterPage Component
 * Displays a registration form for new users
 */
const RegisterPage = () => {
  const navigate = useNavigate();
  const { register } = useAuth();

  // Form state
  const [username, setUsername] = useState('');
  const [givenName, setGivenName] = useState('');
  const [familyName, setFamilyName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  /**
   * Handle form submission
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const form = e.currentTarget;
    // Ask browser to validate form before submission
    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }

    // Validation
    if (!email.trim() || !username.trim() || !password.trim() || !confirmPassword.trim() || !givenName.trim()) {
      setError('Please fill in all required fields');
      return;
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError('Please enter a valid email address');
      return;
    }

    if (username.length < 3) {
      setError('Username must be at least 3 characters long');
      return;
    }

    if (password.length < 8) {
      setError('Password must be at least 8 characters long');
      return;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setIsLoading(true);

    // Call register function from AuthContext
    const result = await register(email, username, password, givenName, familyName);

    setIsLoading(false);

    if (result.success) {
      // Redirect to login page on successful registration
      alert('Registration successful! Please log in.');
      navigate('/login');
    } else {
      // Show error message
      setError(result.error);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h1>Register for Anki Clone</h1>

        <form onSubmit={handleSubmit} className="auth-form" aria-busy={isLoading}>
          {/* Error message display */}
          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          {/* first name input */}
          <div className="form-group">
            <label htmlFor="givenName">First Name</label>
            <input
              id="givenName"
              name="givenName"
              type="text"
              value={givenName}
              onChange={(e) => setGivenName(e.target.value)}
              placeholder="Enter your first name"
              disabled={isLoading}
              autoComplete="given-name"
              required
            />
          </div>

          {/* family name input */}
          <div className="form-group">
            <label htmlFor="familyName">Last Name</label>
            <input
              id="familyName"
              name="familyName"
              type="text"
              value={familyName}
              onChange={(e) => setFamilyName(e.target.value)}
              placeholder="Enter your last name"
              disabled={isLoading}
              autoComplete="family-name"
            />
          </div>

          {/* email input */}
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              name="email"
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              disabled={isLoading}
              autoComplete="email"
              required
            />
          </div>

          {/* Username input */}
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              id="username"
              name="username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Choose a username (min 3 characters)"
              disabled={isLoading}
              autoComplete="username"
              required
              minLength={3}
            />
          </div>

          {/* Password input */}
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              name="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Choose a password (min 8 characters)"
              disabled={isLoading}
              autoComplete="new-password"
              required
              minLength={8}
            />
          </div>

          {/* Confirm password input */}
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirm Password</label>
            <input
              id="confirmPassword"
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder="Confirm your password"
              disabled={isLoading}
              autoComplete="new-password"
            />
          </div>

          {/* Submit button */}
          <button
            type="submit"
            className="btn-primary"
            disabled={isLoading}
          >
            {isLoading ? 'Registering...' : 'Register'}
          </button>
        </form>

        {/* Link to login page */}
        <p className="auth-link">
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;

