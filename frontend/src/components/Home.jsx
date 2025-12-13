import React from 'react';
import { useAuth } from '../context/AuthContext';
import './Home.css';

/**
 * Home Component (Placeholder)
 * This is a simple home page shown after login
 * You'll expand this in later phases with dashboard, stats, etc.
 */
const Home = () => {
  const { username } = useAuth();

  return (
    <div className="home-container">
      <div className="home-content">
        <h1>Welcome to Anki Clone, {username}! 👋</h1>

        <div className="home-card">
          <h2>🎯 Quick Start</h2>
          <p>Here's what you can do:</p>
          <ul>
            <li>📝 <strong>Review Cards:</strong> Click "Review" to study your due cards</li>
            <li>📚 <strong>Manage Cards:</strong> Click "Cards" to view, create, or edit flashcards</li>
            <li>🗂️ <strong>Organize Decks:</strong> Click "Decks" to organize your cards into collections</li>
          </ul>
        </div>

        <div className="home-card">
          <h2>📊 Your Stats</h2>
          <div className="stats-grid">
            <div className="stat-item">
              <div className="stat-number">0</div>
              <div className="stat-label">Cards Due</div>
            </div>
            <div className="stat-item">
              <div className="stat-number">0</div>
              <div className="stat-label">Total Cards</div>
            </div>
            <div className="stat-item">
              <div className="stat-number">0</div>
              <div className="stat-label">Total Decks</div>
            </div>
          </div>
          <p className="note">
            <em>Note: Stats will be implemented in Phase 5</em>
          </p>
        </div>

        <div className="home-card">
          <h2>🚀 Next Steps</h2>
          <p>This is Phase 1 (Authentication) complete!</p>
          <p>Continue building:</p>
          <ul>
            <li>Phase 2: Review Interface - The core spaced repetition feature</li>
            <li>Phase 3: Card Management - Create and edit flashcards</li>
            <li>Phase 4: Deck Management - Organize cards into decks</li>
            <li>Phase 5: Dashboard & Stats - Visualize your progress</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Home;

