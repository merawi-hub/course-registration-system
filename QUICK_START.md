# Quick Start Guide

## ✨ What's New

### Typography
- **Poppins** font for all headings and buttons
- **Inter** font for all body text
- Automatic fallback to Segoe UI if fonts not installed

### Splash Screen
- **Graduation cap icon only** (clean, simple design)
- Professional gradient background
- Smooth animations

### Login Screen
- Updated with Poppins/Inter fonts
- Larger, more accessible elements
- Modern, clean design

---

## 🚀 Run the Application

### Option 1: Quick Run (No Font Installation)
The app works perfectly without installing fonts (uses Segoe UI fallback):

```bash
cd course-registration-system
mvn javafx:run
```

Or with full path:
```bash
& "C:\Program Files\Apache\maven\bin\mvn.cmd" javafx:run
```

### Option 2: With Custom Fonts (Recommended)

**1. Install Fonts:**
- Download **Poppins**: https://fonts.google.com/specimen/Poppins
- Download **Inter**: https://fonts.google.com/specimen/Inter
- Extract and install `.ttf` files (right-click → Install for all users)

**2. Run Application:**
```bash
mvn javafx:run
```

---

## 📋 What You'll See

### 1. Splash Screen (3 seconds)
```
┌─────────────────────────────────┐
│                                 │
│      🎓 Graduation Cap          │
│                                 │
│   COURSE REGISTRATION           │
│         SYSTEM                  │
│                                 │
│ Smart way to register your      │
│         future                  │
│                                 │
│         ⟳ Loading...            │
│                                 │
└─────────────────────────────────┘
```

### 2. Login Screen
```
┌─────────────────────────────────┐
│         👤 User Icon            │
│                                 │
│      Welcome Back!              │
│ Please login to your account    │
│                                 │
│  👤 Username                    │
│  🔒 Password                    │
│                                 │
│  ☐ Remember me  Forgot Pass?   │
│                                 │
│       [LOGIN BUTTON]            │
│                                 │
│  Don't have an account?         │
│      Register here              │
└─────────────────────────────────┘
```

---

## 🎨 Design Features

✅ **Poppins** for headings (bold, modern)  
✅ **Inter** for body text (screen-optimized)  
✅ **Graduation cap** icon (education symbol)  
✅ **Gradient background** (professional depth)  
✅ **Smooth animations** (scale-in, fade-out)  
✅ **High contrast** (excellent readability)  
✅ **Responsive layout** (centered, balanced)  
✅ **Automatic fallback** (works without custom fonts)  

---

## 📁 Key Files

- `SplashScreen.java` - Splash screen with graduation cap
- `LoginScreen.java` - Login screen with Poppins/Inter
- `FontLoader.java` - Font management utility
- `App.java` - Application entry point

---

## 🔧 Troubleshooting

### Fonts not showing?
1. Install Poppins and Inter from Google Fonts
2. Restart the application
3. If still not working, app uses Segoe UI (looks great too!)

### Application won't start?
1. Check Java 17+ is installed: `java -version`
2. Check Maven is installed: `mvn -version`
3. Check MySQL is running
4. Verify `db.properties` is configured

### Database connection error?
1. Open DBeaver and verify `javapro` database exists
2. Check `src/main/resources/db.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/javapro
   db.username=root
   db.password=YOUR_PASSWORD
   ```

---

## 📚 Documentation

- `UPDATED_DESIGN.md` - Complete design documentation
- `FONT_INSTALLATION_GUIDE.md` - Detailed font installation
- `SPLASH_LOGIN_UPDATES.md` - Previous updates
- `README.md` - Project overview

---

## 🎯 Default Login

Once the app starts:
- **Username:** `admin`
- **Password:** `admin123`

---

## ⚡ Quick Commands

```bash
# Compile only
mvn compile

# Run application
mvn javafx:run

# Clean and compile
mvn clean compile

# Run tests
mvn test
```

---

## 💡 Tips

1. **Fonts are optional** - App looks great with Segoe UI fallback
2. **Install fonts system-wide** - Right-click → "Install for all users"
3. **Restart after font installation** - Fonts load on app startup
4. **Use DBeaver for database** - Easier than command line
5. **Check console for errors** - Helpful for debugging

---

## 🎉 Enjoy!

Your Course Registration System now has:
- Professional typography (Poppins + Inter)
- Clean graduation cap icon
- Modern, attractive design
- Smooth user experience

Run it and see the difference! 🚀
