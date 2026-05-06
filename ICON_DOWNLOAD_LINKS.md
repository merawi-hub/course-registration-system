# Direct Download Links for Graduation Cap Icons

## 🎓 Recommended Free Icons

### Option 1: Flaticon (Best Quality)
1. Go to: https://www.flaticon.com/free-icon/graduation-cap_3976625
2. Click "Download" (Free)
3. Choose PNG format, 512px
4. Rename to `graduation-cap.png`
5. Move to `src/main/resources/images/`

### Option 2: Icons8 (Easy Download)
1. Go to: https://icons8.com/icon/12246/graduation-cap
2. Select "White" color
3. Choose size: 256px
4. Download PNG
5. Rename to `graduation-cap.png`
6. Move to `src/main/resources/images/`

### Option 3: Iconfinder (Professional)
1. Go to: https://www.iconfinder.com/icons/211871/graduation_cap_icon
2. Download free PNG
3. Rename to `graduation-cap.png`
4. Move to `src/main/resources/images/`

### Option 4: Font Awesome (Simple)
1. Go to: https://fontawesome.com/icons/graduation-cap?f=classic&s=solid
2. Download SVG or PNG
3. If SVG, convert to PNG using: https://cloudconvert.com/svg-to-png
4. Rename to `graduation-cap.png`
5. Move to `src/main/resources/images/`

---

## 🎨 Icon Specifications

### What You Need:
- **Format:** PNG
- **Background:** Transparent
- **Color:** White (for dark blue background)
- **Size:** 128px, 256px, or 512px
- **Style:** Simple, clean, recognizable

### Where to Put It:
```
course-registration-system/
└── src/
    └── main/
        └── resources/
            └── images/
                └── graduation-cap.png  ← HERE
```

---

## 🚀 Quick Setup

### Windows PowerShell:
```powershell
# After downloading your icon to Downloads folder
cd C:\Users\hp\OneDrive\Desktop\course_reg\course-registration-system
copy "$env:USERPROFILE\Downloads\your-downloaded-icon.png" "src\main\resources\images\graduation-cap.png"
```

### Git Bash:
```bash
cd course-registration-system
cp ~/Downloads/your-downloaded-icon.png src/main/resources/images/graduation-cap.png
```

---

## ✅ Verify It Works

```bash
# Compile and run
mvn javafx:run
```

If the icon loads successfully:
- ✅ You'll see your custom icon in the splash screen
- ✅ You'll see your custom icon in the login page branding section
- ✅ No error messages in console

If the icon doesn't load:
- ❌ You'll see the drawn fallback icon (still looks good!)
- ❌ Console may show: "Could not load image: graduation-cap.png"

---

## 🎯 Alternative: Use Your Own Icon

If you have a specific icon you want to use:

1. **Prepare the icon:**
   - Open in image editor (Paint, GIMP, Photoshop)
   - Resize to 256x256px
   - Save as PNG with transparent background
   - If background is not transparent, make it white

2. **Save it:**
   - Name: `graduation-cap.png`
   - Location: `src/main/resources/images/`

3. **Run:**
   ```bash
   mvn javafx:run
   ```

---

## 💡 Pro Tips

### Make Icon White (if it's not):
1. Open in image editor
2. Select the icon
3. Change color to white (#FFFFFF)
4. Keep background transparent
5. Save as PNG

### Convert SVG to PNG:
- Use: https://cloudconvert.com/svg-to-png
- Or: https://svgtopng.com/
- Choose size: 256px or 512px

### Remove Background:
- Use: https://www.remove.bg/
- Upload your icon
- Download PNG with transparent background

---

## 📦 What's Included

The application now has:
- ✅ `ImageLoader.java` - Loads images from resources
- ✅ Automatic fallback to drawn icon
- ✅ Support for custom icons
- ✅ No errors if icon is missing

---

## 🎉 You're All Set!

Just download any graduation cap icon, rename it to `graduation-cap.png`, put it in `src/main/resources/images/`, and run the app!
