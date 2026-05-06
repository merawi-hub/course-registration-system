package com.university.crs.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Utility class for loading and managing custom fonts.
 * Uses Poppins for headings and Inter for body text.
 * Falls back to system fonts if custom fonts are not available.
 */
public class FontLoader {
    
    private static final String POPPINS_REGULAR = "Poppins";
    private static final String POPPINS_BOLD = "Poppins";
    private static final String INTER_REGULAR = "Inter";
    
    // Fallback fonts
    private static final String FALLBACK_HEADING = "Segoe UI";
    private static final String FALLBACK_BODY = "Segoe UI";
    
    /**
     * Get Poppins font for headings
     */
    public static Font getPoppins(double size) {
        Font font = Font.font(POPPINS_REGULAR, size);
        if (font.getFamily().equals(POPPINS_REGULAR)) {
            return font;
        }
        return Font.font(FALLBACK_HEADING, size);
    }
    
    /**
     * Get Poppins Bold font for headings
     */
    public static Font getPoppinsBold(double size) {
        Font font = Font.font(POPPINS_BOLD, FontWeight.BOLD, size);
        if (font.getFamily().equals(POPPINS_BOLD)) {
            return font;
        }
        return Font.font(FALLBACK_HEADING, FontWeight.BOLD, size);
    }
    
    /**
     * Get Inter font for body text
     */
    public static Font getInter(double size) {
        Font font = Font.font(INTER_REGULAR, size);
        if (font.getFamily().equals(INTER_REGULAR)) {
            return font;
        }
        return Font.font(FALLBACK_BODY, size);
    }
    
    /**
     * Get font family name for CSS
     */
    public static String getPoppinsFontFamily() {
        return "'" + POPPINS_REGULAR + "', '" + FALLBACK_HEADING + "'";
    }
    
    /**
     * Get font family name for CSS
     */
    public static String getInterFontFamily() {
        return "'" + INTER_REGULAR + "', '" + FALLBACK_BODY + "'";
    }
}
