# Anki Clone Frontend

React frontend for the Anki Clone spaced repetition flashcard application.

## 🚀 Getting Started

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn
- Backend server running on `http://localhost:8080`

### Installation

1. **Navigate to the frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm start
   ```

   The app will open at [http://localhost:3000](http://localhost:3000)

### Building for Production

```bash
npm run build
```

This creates an optimized production build in the `build/` folder.

---

## 📁 Project Structure

```
frontend/
├── public/
│   └── index.html          # HTML template
├── src/
│   ├── components/
│   │   ├── auth/           # Authentication components
│   │   │   ├── LoginPage.jsx
│   │   │   ├── RegisterPage.jsx
│   │   │   └── AuthPages.css
│   │   ├── common/         # Shared components
│   │   │   ├── Navbar.jsx
│   │   │   ├── Navbar.css
│   │   │   └── PrivateRoute.jsx
│   │   └── Home.jsx        # Home page
│   ├── context/
│   │   └── AuthContext.jsx # Authentication state management
│   ├── services/
│   │   ├── api.js          # Axios instance with interceptors
│   │   └── authService.js  # Authentication API calls
│   ├── App.jsx             # Main app component with routing
│   ├── App.css
│   ├── index.js            # Entry point
│   └── index.css
├── package.json
└── README.md
```

---

## 🔑 Features (Phase 1 - Authentication)

### ✅ Completed
- User registration
- User login with JWT authentication
- Token storage in localStorage
- Protected routes (redirect to login if not authenticated)
- Logout functionality
- Responsive navigation bar
- Error handling and validation

### 🚧 Coming Soon
- **Phase 2:** Review interface with spaced repetition
- **Phase 3:** Card management (create, edit, delete)
- **Phase 4:** Deck management
- **Phase 5:** Dashboard and statistics

---

## 🛠️ Technologies Used

- **React 18** - UI library
- **React Router v6** - Client-side routing
- **Axios** - HTTP client for API calls
- **Context API** - State management for authentication
- **CSS3** - Styling

---

## 🔗 API Integration

The frontend connects to the Spring Boot backend at `http://localhost:8080`.

### Authentication Endpoints Used:
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login user (returns JWT token)
- `POST /auth/logout` - Logout user (blacklist token)

### How Authentication Works:

1. **Login:**
   - User submits username/password
   - Backend returns JWT token
   - Token stored in `localStorage`
   - Token automatically added to all subsequent requests via Axios interceptor

2. **Protected Routes:**
   - `PrivateRoute` component checks authentication status
   - Redirects to `/login` if not authenticated
   - Renders component if authenticated

3. **Logout:**
   - Calls backend to blacklist token
   - Clears `localStorage`
   - Redirects to login page

---

## 📝 Usage Guide

### For Users:

1. **Register an account:**
   - Go to [http://localhost:3000/register](http://localhost:3000/register)
   - Choose a username (min 3 characters)
   - Choose a password (min 6 characters)
   - Click "Register"

2. **Login:**
   - Go to [http://localhost:3000/login](http://localhost:3000/login)
   - Enter your credentials
   - Click "Login"

3. **Navigate:**
   - Use the navbar to access different sections
   - Home, Review, Cards, Decks links (some are placeholders for now)

4. **Logout:**
   - Click "Logout" in the navbar

### For Developers:

#### Adding a New Route:

```jsx
// In App.jsx
<Route 
  path="/your-route" 
  element={
    <PrivateRoute>
      <YourComponent />
    </PrivateRoute>
  } 
/>
```

#### Using Authentication in Components:

```jsx
import { useAuth } from '../context/AuthContext';

function YourComponent() {
  const { username, logout, isAuthenticated } = useAuth();
  
  return (
    <div>
      <p>Welcome, {username}!</p>
      <button onClick={logout}>Logout</button>
    </div>
  );
}
```

#### Making API Calls:

```jsx
import api from '../services/api';

// GET request
const fetchData = async () => {
  try {
    const response = await api.get('/your-endpoint');
    console.log(response.data);
  } catch (error) {
    console.error('Error:', error);
  }
};

// POST request
const postData = async (data) => {
  try {
    const response = await api.post('/your-endpoint', data);
    console.log(response.data);
  } catch (error) {
    console.error('Error:', error);
  }
};
```

---

## 🐛 Troubleshooting

### Common Issues:

**1. "Cannot connect to backend"**
- Ensure Spring Boot backend is running on `http://localhost:8080`
- Check CORS configuration in backend

**2. "Token expired" or automatic logout**
- JWT token has expired (default: 1 hour)
- Login again to get a new token

**3. "npm install" fails**
- Delete `node_modules` and `package-lock.json`
- Run `npm install` again

**4. CORS errors**
- Make sure your Spring Boot `SecurityConfig` allows CORS from `http://localhost:3000`

---

## 📚 Learning Resources

- [React Official Docs](https://react.dev)
- [React Router Docs](https://reactrouter.com)
- [Axios Documentation](https://axios-http.com)

---

## 🎯 Next Steps

Now that Phase 1 (Authentication) is complete, you can:

1. **Test the app:**
   - Register a new account
   - Login and explore
   - Test logout functionality

2. **Build Phase 2 (Review Interface):**
   - Create `ReviewPage.jsx`
   - Implement flashcard flip animation
   - Add review buttons (Again, Hard, Good, Easy)
   - Connect to `/cards/due` endpoint

3. **Build Phase 3 (Card Management):**
   - Create card list page
   - Create card form
   - Implement CRUD operations

Refer to `FRONTEND_DEVELOPMENT_PLAN.md` in the project root for detailed guidance!

---

## 📄 License

This project is part of the Anki Clone learning application.

---

**Happy coding! 🚀**

