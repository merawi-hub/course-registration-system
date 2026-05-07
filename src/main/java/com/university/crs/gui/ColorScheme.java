package com.university.crs.gui;

import javafx.scene.paint.Color;

/**
 * Centralized color scheme for the entire application.
 * Material-style professional palette.
 */
public class ColorScheme {
    
    // ═══════════════════════════════════════════════════════════════════
    // PRIMARY COLORS (Main Theme)
    // ═══════════════════════════════════════════════════════════════════
    
    /** Dark Navy Blue - #0D2B4F - Used for headers, dark backgrounds */
    public static final Color DARK_NAVY = Color.rgb(13, 43, 79);
    public static final String DARK_NAVY_HEX = "#0D2B4F";
    
    /** Deep Blue - #123A63 - Used for sidebar, secondary headers */
    public static final Color DEEP_BLUE = Color.rgb(18, 58, 99);
    public static final String DEEP_BLUE_HEX = "#123A63";
    
    /** Medium Blue - #1F5A96 - Primary button color, main actions */
    public static final Color MEDIUM_BLUE = Color.rgb(31, 90, 150);
    public static final String MEDIUM_BLUE_HEX = "#1F5A96";
    
    /** Light Blue - #4A90E2 - Hover states, links, accents */
    public static final Color LIGHT_BLUE = Color.rgb(74, 144, 226);
    public static final String LIGHT_BLUE_HEX = "#4A90E2";
    
    // ═══════════════════════════════════════════════════════════════════
    // BACKGROUND & NEUTRAL COLORS
    // ═══════════════════════════════════════════════════════════════════
    
    /** Light Gray Background - #F4F6F9 - Main page background */
    public static final Color BACKGROUND = Color.rgb(244, 246, 249);
    public static final String BACKGROUND_HEX = "#F4F6F9";
    
    /** White - #FFFFFF - Cards, containers */
    public static final Color WHITE = Color.rgb(255, 255, 255);
    public static final String WHITE_HEX = "#FFFFFF";
    
    /** Soft Gray - #E0E0E0 - Borders, dividers */
    public static final Color SOFT_GRAY = Color.rgb(224, 224, 224);
    public static final String SOFT_GRAY_HEX = "#E0E0E0";
    
    /** Dark Text - #1F2937 - Primary text color */
    public static final Color DARK_TEXT = Color.rgb(31, 41, 55);
    public static final String DARK_TEXT_HEX = "#1F2937";
    
    // ═══════════════════════════════════════════════════════════════════
    // ACCENT COLORS (UI Actions & States)
    // ═══════════════════════════════════════════════════════════════════
    
    /** Green - #28C76F - Success, active states */
    public static final Color SUCCESS = Color.rgb(40, 199, 111);
    public static final String SUCCESS_HEX = "#28C76F";
    
    /** Red - #EA5455 - Delete, danger, errors */
    public static final Color DANGER = Color.rgb(234, 84, 85);
    public static final String DANGER_HEX = "#EA5455";
    
    /** Orange - #FF9F43 - Warnings, highlights */
    public static final Color WARNING = Color.rgb(255, 159, 67);
    public static final String WARNING_HEX = "#FF9F43";
    
    /** Purple - #7367F0 - Cards, stats */
    public static final Color PURPLE = Color.rgb(115, 103, 240);
    public static final String PURPLE_HEX = "#7367F0";
    
    /** Teal/Cyan - #00CFE8 - Info, secondary accents */
    public static final Color TEAL = Color.rgb(0, 207, 232);
    public static final String TEAL_HEX = "#00CFE8";
    
    // ═══════════════════════════════════════════════════════════════════
    // BUTTON COLORS
    // ═══════════════════════════════════════════════════════════════════
    
    /** Primary Button - #1F5A96 - Main action buttons */
    public static final Color BUTTON_PRIMARY = MEDIUM_BLUE;
    public static final String BUTTON_PRIMARY_HEX = MEDIUM_BLUE_HEX;
    
    /** Hover Blue - #174A7C - Button hover state */
    public static final Color BUTTON_HOVER = Color.rgb(23, 74, 124);
    public static final String BUTTON_HOVER_HEX = "#174A7C";
    
    /** Delete Button - #EA5455 - Destructive actions */
    public static final Color BUTTON_DELETE = DANGER;
    public static final String BUTTON_DELETE_HEX = DANGER_HEX;
    
    /** Delete Button Hover - Darker red */
    public static final Color BUTTON_DELETE_HOVER = Color.rgb(220, 60, 61);
    public static final String BUTTON_DELETE_HOVER_HEX = "#DC3C3D";
    
    // ═══════════════════════════════════════════════════════════════════
    // GRADIENTS (CSS Strings)
    // ═══════════════════════════════════════════════════════════════════
    
    /** Blue gradient for branding sections */
    public static final String GRADIENT_BLUE = 
        "linear-gradient(to bottom right, " + DARK_NAVY_HEX + ", " + DEEP_BLUE_HEX + ")";
    
    /** Sidebar gradient */
    public static final String GRADIENT_SIDEBAR = 
        "linear-gradient(to bottom, " + DEEP_BLUE_HEX + ", " + DARK_NAVY_HEX + ")";
    
    /** Button gradient (optional) */
    public static final String GRADIENT_BUTTON = 
        "linear-gradient(to right, " + MEDIUM_BLUE_HEX + ", " + LIGHT_BLUE_HEX + ")";
    
    // ═══════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════
    
    /**
     * Convert Color to CSS hex string
     */
    public static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    /**
     * Create a semi-transparent version of a color
     */
    public static String toRGBA(Color color, double opacity) {
        return String.format("rgba(%d, %d, %d, %.2f)",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255),
            opacity);
    }
    
    /**
     * Get shadow CSS for cards
     */
    public static String getCardShadow() {
        return "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 3);";
    }
    
    /**
     * Get primary button style
     */
    public static String getPrimaryButtonStyle() {
        return "-fx-background-color: " + BUTTON_PRIMARY_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get primary button hover style
     */
    public static String getPrimaryButtonHoverStyle() {
        return "-fx-background-color: " + BUTTON_HOVER_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get delete button style
     */
    public static String getDeleteButtonStyle() {
        return "-fx-background-color: " + BUTTON_DELETE_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get delete button hover style
     */
    public static String getDeleteButtonHoverStyle() {
        return "-fx-background-color: " + BUTTON_DELETE_HOVER_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-cursor: hand;";
    }
}
