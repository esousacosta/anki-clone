# 🚀 Quick Start Guide - Anki Clone Frontend

## Step-by-Step Instructions

### 1️⃣ Install Dependencies

Open your terminal and navigate to the frontend directory:

```bash
cd /Users/edesousacosta/personal/projects/Spring-boot/anki-clone/frontend
npm install
```

This will install all required dependencies (React, React Router, Axios, etc.)

**Expected output:** You'll see a progress bar and "added XXX packages" message.

---

### 2️⃣ Start the Backend Server

Before running the frontend, make sure your Spring Boot backend is running.

In a **separate terminal window/tab**:

```bash
cd /Users/edesousacosta/personal/projects/Spring-boot/anki-clone
mvn spring-boot:run
```

Wait for the message: `Started AnkiCloneApplication in X seconds`

Backend will be running at: `http://localhost:8080`

---

### 3️⃣ Start the Frontend Development Server

Back in the frontend directory terminal:

```bash
npm start
```

**Expected output:**
- "Compiled successfully!"
- Browser automatically opens to `http://localhost:3000`

If the browser doesn't open automatically, navigate to: [http://localhost:3000](http://localhost:3000)

---

### 4️⃣ Test the Application

#### Register a New Account:

1. Click "Register" in the navbar (or go to `/register`)
2. Enter a username (min 3 characters): e.g., "testuser"
3. Enter a password (min 6 characters): e.g., "password123"
4. Confirm password: "password123"
5. Click "Register"
6. You should see: "Registration successful! Please log in."

#### Login:

1. You'll be redirected to `/login`
2. Enter your credentials:
   - Username: "testuser"
   - Password: "password123"
3. Click "Login"
4. You should be redirected to the home page
5. Navbar should show: "👤 testuser" and a "Logout" button

#### Explore:

- Click "Home" - See welcome message
- Click "Review" - See placeholder (Phase 2 coming soon)
- Click "Cards" - See placeholder (Phase 3 coming soon)
- Click "Decks" - See placeholder (Phase 4 coming soon)
- Click "Logout" - You'll be logged out and redirected to login

---

## 🔧 Troubleshooting

### Problem: `npm install` fails

**Solution:**
```bash
# Clear npm cache
npm cache clean --force

# Try again
npm install
```

---

### Problem: "Cannot connect to backend" or Network errors

**Check:**
1. Is Spring Boot running? Check terminal for "Started AnkiCloneApplication"
2. Is it running on port 8080? Check the terminal output
3. Is PostgreSQL running? (Required for backend)

**Start PostgreSQL:**
```bash
cd /Users/edesousacosta/personal/projects/Spring-boot/anki-clone
docker-compose up -d
```

---

### Problem: CORS errors in browser console

**Solution:**
Your Spring Boot SecurityConfig should allow CORS from `http://localhost:3000`.

Check your `SecurityConfig.java` has:
```java
http.cors(cors -> cors.configurationSource(request -> {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    return config;
}))
```

---

### Problem: Login successful but redirects to login again

**Check:**
1. Open browser DevTools (F12)
2. Go to "Application" tab → "Local Storage"
3. Check if `token` and `username` are stored
4. If not, there might be an issue with the backend response

**Debug:**
- Check browser console for errors
- Check Network tab in DevTools to see the `/auth/login` response

---

### Problem: "Unexpected token" or "Cannot find module" errors

**Solution:**
Make sure you're in the correct directory:
```bash
pwd
# Should show: /Users/edesousacosta/personal/projects/Spring-boot/anki-clone/frontend
```

Delete node_modules and reinstall:
```bash
rm -rf node_modules package-lock.json
npm install
```

---

## 📱 What You Should See

### Login Page (`/login`):
- Purple gradient background
- White card with login form
- Username and password fields
- "Login" button
- "Don't have an account? Register here" link

### Home Page (`/`):
- Navigation bar at top (purple gradient)
- Welcome message with your username
- Quick Start card
- Stats card (placeholders)
- Next Steps card

### Navbar:
When logged in: Home | Review | Cards | Decks | 👤 username | Logout  
When logged out: Login | Register

---

## 🎉 Success Criteria

You've successfully completed Phase 1 if you can:

✅ Register a new account  
✅ Login with credentials  
✅ See your username in the navbar  
✅ Navigate to different pages  
✅ Logout successfully  
✅ Get redirected to login when accessing protected routes without authentication

---

## 🚀 Next Steps

Now that Phase 1 works, you can:

1. **Explore the code:**
   - Look at `src/components/auth/LoginPage.jsx` to understand form handling
   - Check `src/context/AuthContext.jsx` to see how authentication state works
   - Review `src/services/api.js` to understand API interceptors

2. **Start Phase 2:**
   - Create the Review interface
   - Implement flashcard flip animation
   - Connect to `/cards/due` endpoint
   - Add review buttons (Again, Hard, Good, Easy)

3. **Read the documentation:**
   - See `FRONTEND_DEVELOPMENT_PLAN.md` for the complete roadmap
   - See `frontend/README.md` for detailed usage guide

---

## 💡 Tips for Learning React

1. **Use Browser DevTools:**
   - React DevTools extension (Chrome/Firefox)
   - Console tab for debugging
   - Network tab to see API calls

2. **Learn by experimenting:**
   - Try changing text in components
   - Modify CSS to see visual changes
   - Add console.log() to understand data flow

3. **Follow the data flow:**
   - User enters data → State updates → UI re-renders
   - Component → Context → Service → API → Backend

4. **Don't worry about mistakes:**
   - React gives helpful error messages
   - You can always undo changes (use git!)

---

**Good luck! 🎉**

If you encounter any issues, check the troubleshooting section above or refer to the full README.

