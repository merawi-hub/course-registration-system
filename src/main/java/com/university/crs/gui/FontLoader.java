package com.university.crs.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Utility class for loading and managing custom fonts.
 * Uses Outfit font from Google Fonts for the entire system.
 * Falls back to system fonts if custom fonts are not available.
 */
public class FontLoader {
    
    private static final String OUTFIT = "Outfit";
    
    // Fallback font
    private static final String FALLBACK = "Segoe UI";
    
    /**
     * Get Outfit font (regular weight)
     */
    public static Font getOutfit(double size) {
        Font font = Font.font(OUTFIT, size);
        if (font.getFamily().equals(OUTFIT)) {
            return font;
        }
        return Font.font(FALLBACK, size);
    }
    
    /**
     * Get Outfit font (bold weight)
     */
    public static Font getOutfitBold(double size) {
        Font font = Font.font(OUTFIT, FontWeight.BOLD, size);
        if (font.getFamily().equals(OUTFIT)) {
            return font;
        }
        return Font.font(FALLBACK, FontWeight.BOLD, size);
    }
    
    /**
     * Get Outfit font (semi-bold weight)
     */
    public static Font getOutfitSemiBold(double size) {
        Font font = Font.font(OUTFIT, FontWeight.SEMI_BOLD, size);
        if (font.getFamily().equals(OUTFIT)) {
            return font;
        }
        return Font.font(FALLBACK, FontWeight.SEMI_BOLD, size);
    }
    
    /**
     * Get Outfit font (medium weight)
     */
    public static Font getOutfitMedium(double size) {
        Font font = Font.font(OUTFIT, FontWeight.MEDIUM, size);
        if (font.getFamily().equals(OUTFIT)) {
            return font;
        }
        return Font.font(FALLBACK, FontWeight.MEDIUM, size);
    }
    
    /**
     * Get font family name for CSS
     */
    public static String getOutfitFontFamily() {
        return "'" + OUTFIT + "', '" + FALLBACK + "'";
    }
    
    // Legacy method names for backward compatibility
    public static Font getPoppins(double size) { return getOutfit(size); }
    public static Font getPoppinsBold(double size) { return getOutfitBold(size); }
    public static Font getInter(double size) { return getOutfit(size); }
    public static String getPoppinsFontFamily() { return getOutfitFontFamily(); }
    public static String getInterFontFamily() { return getOutfitFontFamily(); }
}
