# Anki Clone - Frontend Development Plan

**Date:** December 13, 2025  
**Target Framework:** React  
**Developer Experience Level:** Beginner in React

---

## Table of Contents

1. [Current Backend API Summary](#current-backend-api-summary)
2. [Recommended Development Phases](#recommended-development-phases)
3. [Phase 1: Authentication & Basic Setup](#phase-1-authentication--basic-setup)
4. [Phase 2: Review Interface](#phase-2-review-interface)
5. [Phase 3: Card Management](#phase-3-card-management)
6. [Phase 4: Deck Management](#phase-4-deck-management)
7. [Phase 5: Dashboard & Stats](#phase-5-dashboard--stats)
8. [Learning Path & Technology Stack](#learning-path--technology-stack)
9. [Minimal MVP Recommendation](#minimal-mvp-recommendation)

---

## Current Backend API Summary

Your Spring Boot backend exposes the following REST endpoints:

### Authentication Endpoints
- **POST** `/auth/register` - Register new user
- **POST** `/auth/login` - Login (returns JWT token)
- **POST** `/auth/logout` - Logout (blacklist token)

### Card Endpoints
- **GET** `/cards` - Get all cards for authenticated user
- **GET** `/cards/due` - Get cards due for review (ready to study)
- **GET** `/cards/{id}` - Get specific card by ID
- **POST** `/cards` - Create new card
  - Body: `{front, back, category, deckId}`
- **POST** `/cards/{id}/review` - Review a card
  - Body: `{quality}` where quality = 0 (Again), 1 (Hard), 2 (Good), 3 (Easy)
- **PUT** `/cards/{id}` - Update card
- **DELETE** `/cards/{id}` - Delete card

### Deck Endpoints
- **GET** `/decks` - Get all decks for authenticated user
- **GET** `/decks/{id}` - Get specific deck
- **POST** `/decks` - Create new deck
- **PUT** `/decks/{id}` - Update deck
- **DELETE** `/decks/{id}` - Delete deck

### User Endpoints
- **GET** `/users` - Get all users
- **GET** `/users/{id}` - Get specific user

---

## Recommended Development Phases

### Why This Order?

Each phase builds upon the previous one, teaching you React concepts incrementally while delivering a functional application at each step.

---

## Phase 1: Authentication & Basic Setup

**Priority:** 🔴 **START HERE** - Foundation for everything else

### Why First?
- Authentication is required for all other features
- Gets you familiar with React basics: components, state, forms, routing
- Small scope - you'll see results quickly
- Teaches you HTTP requests and token management

### Features to Build

1. **Login Page**
   - Simple form with username and password fields
   - Submit button
   - Error message display
   - "Don't have an account? Register" link

2. **Register Page**
   - Form with username and password fields
   - Password confirmation field
   - Submit button
   - Success message / error handling
   - "Already have an account? Login" link

3. **JWT Token Storage**
   - Store token in `localStorage` or `sessionStorage`
   - Attach token to all authenticated API requests
   - Handle token expiration

4. **Protected Routes**
   - Redirect to login page if user is not authenticated
   - Redirect to home/dashboard if already logged in

5. **Logout Functionality**
   - Clear stored token
   - Call backend logout endpoint
   - Redirect to login page

### Components to Create

```
src/
├── components/
│   ├── auth/
│   │   ├── LoginPage.jsx
│   │   └── RegisterPage.jsx
│   └── common/
│       └── PrivateRoute.jsx
├── context/
│   └── AuthContext.jsx
├── services/
│   └── authService.js
└── App.jsx
```

### Learning Outcomes
- React component structure
- State management with `useState`
- Form handling
- API calls with `fetch` or `axios`
- React Router basics
- Context API for global state

---

## Phase 2: Review Interface

**Priority:** 🔴 **CORE FEATURE** - The heart of your app

### Why Second?
- This is what makes your app valuable
- Implements the spaced repetition algorithm visually
- Teaches state management and user interactions
- Motivating - you'll see the app come to life!

### Features to Build

1. **Review Session Page**
   - Fetch cards from `GET /cards/due`
   - Display one card at a time
   - Show only the front initially

2. **Flip Card Animation**
   - Click on card to reveal the back
   - Smooth flip animation (CSS transitions)
   - Visual indication of front vs. back

3. **Four Review Buttons**
   - **Again** (Red) - quality: 0
   - **Hard** (Orange) - quality: 1
   - **Good** (Green) - quality: 2
   - **Easy** (Blue) - quality: 3
   - Each button calls `POST /cards/{id}/review` with appropriate quality

4. **Progress Indicator**
   - Show "X cards remaining"
   - Progress bar (optional)
   - Card counter (e.g., "5/20")

5. **Session Complete Screen**
   - Congratulations message
   - Statistics: cards reviewed, time spent
   - Button to return to dashboard

### Components to Create

```
src/
├── components/
│   ├── review/
│   │   ├── ReviewPage.jsx
│   │   ├── FlashCard.jsx
│   │   ├── ReviewButtons.jsx
│   │   └── SessionComplete.jsx
└── services/
    └── cardService.js
```

### Learning Outcomes
- Managing complex component state
- Conditional rendering
- CSS animations
- Array manipulation (removing reviewed cards)
- Sequential async operations

---

## Phase 3: Card Management

**Priority:** 🟡 **Important** - Lets users create content

### Features to Build

1. **Card List Page**
   - Display all cards in a table or grid
   - Show front, back, deck, next review date
   - Search/filter functionality (optional)
   - Sort by different fields (optional)

2. **Create Card Form**
   - Form with fields: front, back, category, deck selector
   - Validation (required fields)
   - Success/error messages
   - Redirect to card list after creation

3. **Edit Card**
   - Reuse create card form with pre-filled data
   - Update card on submit
   - Handle errors

4. **Delete Card**
   - Delete button on each card
   - Confirmation dialog ("Are you sure?")
   - Remove from list after deletion

### Components to Create

```
src/
├── components/
│   ├── cards/
│   │   ├── CardList.jsx
│   │   ├── CardForm.jsx
│   │   ├── CardItem.jsx
│   │   └── DeleteConfirmDialog.jsx
```

### Learning Outcomes
- Forms with multiple inputs
- Data validation
- CRUD operations
- Modal/dialog components
- List rendering and updates

---

## Phase 4: Deck Management

**Priority:** 🟡 **Important** - Organize cards into collections

### Features to Build

1. **Deck List Page**
   - Display all decks
   - Show deck name, description, card count
   - Click on deck to view its cards

2. **Create/Edit Deck**
   - Simple form with name and description
   - Validation

3. **Delete Deck**
   - Confirmation dialog
   - Handle decks with cards (cascade or prevent deletion)

4. **Filter Cards by Deck**
   - Update card list page to filter by selected deck
   - "All Decks" option

### Components to Create

```
src/
├── components/
│   ├── decks/
│   │   ├── DeckList.jsx
│   │   ├── DeckForm.jsx
│   │   └── DeckItem.jsx
```

### Learning Outcomes
- Navigation with parameters
- Parent-child component communication
- Filtering and derived state

---

## Phase 5: Dashboard & Stats

**Priority:** 🟢 **Nice to Have** - Polish and user engagement

### Features to Build

1. **Home Dashboard**
   - Overview of due cards count
   - List of decks with card counts
   - Quick access to review session
   - Recent activity

2. **Statistics Page**
   - Cards reviewed today
   - Current streak
   - Total cards
   - Review accuracy
   - Charts (optional - use Chart.js or Recharts)

3. **Calendar View**
   - Review heatmap (like GitHub contributions)
   - Click on date to see reviews that day

### Components to Create

```
src/
├── components/
│   ├── dashboard/
│   │   ├── Dashboard.jsx
│   │   ├── StatsCard.jsx
│   │   └── QuickActions.jsx
│   └── stats/
│       ├── StatsPage.jsx
│       └── ReviewHeatmap.jsx
```

### Learning Outcomes
- Data visualization
- Date manipulation
- Third-party library integration

---

## Learning Path & Technology Stack

### Recommended Tech Stack for Beginners

#### Core Framework
- **React** - UI library
- **Create React App** - Project setup (simpler than Vite for beginners)

#### Routing
- **React Router v6** - Navigation between pages

#### HTTP Client
- **Axios** - API calls (simpler than fetch)
- Alternative: native `fetch` API

#### State Management
- **Context API** - For authentication state (built into React)
- **useState/useReducer** - For component state
- **Don't start with Redux** - Add it later if needed

#### Styling
- **Plain CSS** or **CSS Modules** - If you're comfortable with CSS
- **Tailwind CSS** - If you want utility-first CSS (popular, easy to learn)
- **Material-UI** or **Ant Design** - If you want pre-built components
- **Choose one** and stick with it

#### Form Handling
- **Start simple** - Just controlled components with `useState`
- **Later:** React Hook Form or Formik (if forms get complex)

#### Development Tools
- **React DevTools** - Browser extension for debugging
- **ESLint** - Code quality
- **Prettier** - Code formatting

### Learning Resources

1. **Official React Docs** - https://react.dev (new docs, very beginner-friendly)
2. **React Router Docs** - https://reactrouter.com
3. **MDN Web Docs** - For JavaScript/CSS reference
4. **YouTube Tutorials** - Search for "React beginner tutorial"

---

## Minimal MVP Recommendation

### Start with Just 2 Phases (1-2 weeks of work)

#### **Phase 1: Authentication** (2-3 days)
Build these components:
- `LoginPage.jsx` - Form with username/password
- `RegisterPage.jsx` - Form to create account
- `AuthContext.jsx` - Manage JWT token globally
- `authService.js` - API calls for login/register/logout

#### **Phase 2: Simple Review** (3-4 days)
Build these components:
- `ReviewPage.jsx` - Fetch cards from `/cards/due`, show one at a time
- Display card front → user clicks → show back
- 4 buttons (Again, Hard, Good, Easy)
- Submit review and move to next card

### This Gives You a Working Anki Clone!

Users can:
✅ Create an account  
✅ Login  
✅ Review their cards with spaced repetition  

Everything else (creating cards, managing decks, stats) is **optional improvements** that make the app better but aren't essential for the core functionality.

---

## Project Structure Overview

```
anki-clone-frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── auth/
│   │   │   ├── LoginPage.jsx
│   │   │   └── RegisterPage.jsx
│   │   ├── review/
│   │   │   ├── ReviewPage.jsx
│   │   │   ├── FlashCard.jsx
│   │   │   └── ReviewButtons.jsx
│   │   ├── cards/
│   │   │   ├── CardList.jsx
│   │   │   └── CardForm.jsx
│   │   ├── decks/
│   │   │   ├── DeckList.jsx
│   │   │   └── DeckForm.jsx
│   │   └── common/
│   │       ├── PrivateRoute.jsx
│   │       └── Navbar.jsx
│   ├── context/
│   │   └── AuthContext.jsx
│   ├── services/
│   │   ├── authService.js
│   │   ├── cardService.js
│   │   └── deckService.js
│   ├── utils/
│   │   └── api.js (axios instance with interceptors)
│   ├── App.jsx
│   ├── App.css
│   └── index.js
├── package.json
└── README.md
```

---

## Next Steps

1. ✅ **Read this document** - Understand the overall plan
2. ⏳ **Set up React project** - Create React App or Vite
3. ⏳ **Build Phase 1** - Authentication (login/register)
4. ⏳ **Build Phase 2** - Review interface
5. ⏳ **Test with real backend** - Ensure API integration works
6. ⏳ **Iterate** - Add phases 3, 4, 5 as needed

---

## Tips for Success

### For React Beginners:

1. **Start small** - Don't try to build everything at once
2. **Use functional components** - Avoid class components (old style)
3. **Learn hooks** - `useState`, `useEffect`, `useContext` are essential
4. **Console.log everything** - Debug by understanding state changes
5. **Read error messages** - React errors are usually helpful
6. **Copy examples** - Learn from working code, then modify
7. **Don't over-engineer** - Simple solutions are often best

### Common Pitfalls to Avoid:

❌ **Don't start with TypeScript** - Add it later if needed  
❌ **Don't use Redux immediately** - Context API is sufficient  
❌ **Don't worry about performance** - Optimize later  
❌ **Don't build everything at once** - Phase by phase  
❌ **Don't copy-paste without understanding** - You'll get stuck later  

---

## Conclusion

**Start with Phase 1 (Authentication)** - It's the foundation for everything else and teaches you React fundamentals.

Then move to **Phase 2 (Review Interface)** - This is the core value of your app.

Everything else can be added incrementally as you learn and grow more comfortable with React.

**Good luck!** 🚀

---

**Document Version:** 1.0  
**Last Updated:** December 13, 2025

