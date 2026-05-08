package com.university.crs.gui;

import javafx.scene.paint.Color;

/**
 * Centralized color scheme for the entire application.
 * Based on the modern dark blue design with colorful accents.
 */
public class ColorScheme {
    
    // ═══════════════════════════════════════════════════════════════════
    // PRIMARY COLORS (Main Theme) - Darker Blue Theme
    // ═══════════════════════════════════════════════════════════════════
    
    /** Very Dark Navy - #0A1929 - Main dark background, headers */
    public static final Color VERY_DARK_NAVY = Color.rgb(10, 25, 41);
    public static final String VERY_DARK_NAVY_HEX = "#0A1929";
    
    /** Dark Navy Blue - #0D2847 - Sidebar, cards on dark background */
    public static final Color DARK_NAVY = Color.rgb(13, 40, 71);
    public static final String DARK_NAVY_HEX = "#0D2847";
    
    /** Deep Blue - #1A3A5C - Secondary dark elements */
    public static final Color DEEP_BLUE = Color.rgb(26, 58, 92);
    public static final String DEEP_BLUE_HEX = "#1A3A5C";
    
    /** Medium Blue - #2563EB - Primary button color, main actions */
    public static final Color MEDIUM_BLUE = Color.rgb(37, 99, 235);
    public static final String MEDIUM_BLUE_HEX = "#2563EB";
    
    /** Light Blue - #3B82F6 - Hover states, links, accents */
    public static final Color LIGHT_BLUE = Color.rgb(59, 130, 246);
    public static final String LIGHT_BLUE_HEX = "#3B82F6";
    
    // ═══════════════════════════════════════════════════════════════════
    // BACKGROUND & NEUTRAL COLORS
    // ═══════════════════════════════════════════════════════════════════
    
    /** Light Gray Background - #F8FAFC - Main page background */
    public static final Color BACKGROUND = Color.rgb(248, 250, 252);
    public static final String BACKGROUND_HEX = "#F8FAFC";
    
    /** White - #FFFFFF - Cards, containers */
    public static final Color WHITE = Color.rgb(255, 255, 255);
    public static final String WHITE_HEX = "#FFFFFF";
    
    /** Soft Gray - #E2E8F0 - Borders, dividers */
    public static final Color SOFT_GRAY = Color.rgb(226, 232, 240);
    public static final String SOFT_GRAY_HEX = "#E2E8F0";
    
    /** Medium Gray - #64748B - Secondary text */
    public static final Color MEDIUM_GRAY = Color.rgb(100, 116, 139);
    public static final String MEDIUM_GRAY_HEX = "#64748B";
    
    /** Dark Text - #1E293B - Primary text color */
    public static final Color DARK_TEXT = Color.rgb(30, 41, 59);
    public static final String DARK_TEXT_HEX = "#1E293B";
    
    // ═══════════════════════════════════════════════════════════════════
    // ACCENT COLORS (Colorful Palette from Design)
    // ═══════════════════════════════════════════════════════════════════
    
    /** Palette Color 1 - Dark Blue */
    public static final Color PALETTE_DARK_BLUE = Color.rgb(30, 58, 138);
    public static final String PALETTE_DARK_BLUE_HEX = "#1E3A8A";
    
    /** Palette Color 2 - Blue */
    public static final Color PALETTE_BLUE = Color.rgb(37, 99, 235);
    public static final String PALETTE_BLUE_HEX = "#2563EB";
    
    /** Palette Color 3 - Teal */
    public static final Color PALETTE_TEAL = Color.rgb(20, 184, 166);
    public static final String PALETTE_TEAL_HEX = "#14B8A6";
    
    /** Palette Color 4 - Green */
    public static final Color PALETTE_GREEN = Color.rgb(34, 197, 94);
    public static final String PALETTE_GREEN_HEX = "#22C55E";
    
    /** Palette Color 5 - Yellow */
    public static final Color PALETTE_YELLOW = Color.rgb(234, 179, 8);
    public static final String PALETTE_YELLOW_HEX = "#EAB308";
    
    /** Palette Color 6 - Orange */
    public static final Color PALETTE_ORANGE = Color.rgb(249, 115, 22);
    public static final String PALETTE_ORANGE_HEX = "#F97316";
    
    /** Palette Color 7 - Red */
    public static final Color PALETTE_RED = Color.rgb(239, 68, 68);
    public static final String PALETTE_RED_HEX = "#EF4444";
    
    /** Palette Color 8 - Gray Blue */
    public static final Color PALETTE_GRAY_BLUE = Color.rgb(100, 116, 139);
    public static final String PALETTE_GRAY_BLUE_HEX = "#64748B";
    
    // ═══════════════════════════════════════════════════════════════════
    // SEMANTIC COLORS (UI Actions & States)
    // ═══════════════════════════════════════════════════════════════════
    
    /** Green - Success, active states */
    public static final Color SUCCESS = PALETTE_GREEN;
    public static final String SUCCESS_HEX = PALETTE_GREEN_HEX;
    
    /** Red - Delete, danger, errors */
    public static final Color DANGER = PALETTE_RED;
    public static final String DANGER_HEX = PALETTE_RED_HEX;
    
    /** Orange - Warnings, highlights */
    public static final Color WARNING = PALETTE_ORANGE;
    public static final String WARNING_HEX = PALETTE_ORANGE_HEX;
    
    /** Purple - #8B5CF6 - Cards, stats */
    public static final Color PURPLE = Color.rgb(139, 92, 246);
    public static final String PURPLE_HEX = "#8B5CF6";
    
    /** Teal/Cyan - Info, secondary accents */
    public static final Color TEAL = PALETTE_TEAL;
    public static final String TEAL_HEX = PALETTE_TEAL_HEX;
    
    // ═══════════════════════════════════════════════════════════════════
    // BUTTON COLORS
    // ═══════════════════════════════════════════════════════════════════
    
    /** Primary Button - Main action buttons */
    public static final Color BUTTON_PRIMARY = MEDIUM_BLUE;
    public static final String BUTTON_PRIMARY_HEX = MEDIUM_BLUE_HEX;
    
    /** Hover Blue - Button hover state */
    public static final Color BUTTON_HOVER = Color.rgb(29, 78, 216);
    public static final String BUTTON_HOVER_HEX = "#1D4ED8";
    
    /** Delete Button - Destructive actions */
    public static final Color BUTTON_DELETE = DANGER;
    public static final String BUTTON_DELETE_HEX = DANGER_HEX;
    
    /** Delete Button Hover - Darker red */
    public static final Color BUTTON_DELETE_HOVER = Color.rgb(220, 38, 38);
    public static final String BUTTON_DELETE_HOVER_HEX = "#DC2626";
    
    // ═══════════════════════════════════════════════════════════════════
    // GRADIENTS (CSS Strings)
    // ═══════════════════════════════════════════════════════════════════
    
    /** Dark blue gradient for branding sections */
    public static final String GRADIENT_DARK_BLUE = 
        "linear-gradient(to bottom right, " + VERY_DARK_NAVY_HEX + ", " + DARK_NAVY_HEX + ")";
    
    /** Sidebar gradient */
    public static final String GRADIENT_SIDEBAR = 
        "linear-gradient(to bottom, " + DARK_NAVY_HEX + ", " + VERY_DARK_NAVY_HEX + ")";
    
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
        return "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);";
    }
    
    /**
     * Get primary button style
     */
    public static String getPrimaryButtonStyle() {
        return "-fx-background-color: " + BUTTON_PRIMARY_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-padding: 10 20; " +
               "-fx-font-size: 14px; " +
               "-fx-font-weight: 600; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get primary button hover style
     */
    public static String getPrimaryButtonHoverStyle() {
        return "-fx-background-color: " + BUTTON_HOVER_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-padding: 10 20; " +
               "-fx-font-size: 14px; " +
               "-fx-font-weight: 600; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get delete button style
     */
    public static String getDeleteButtonStyle() {
        return "-fx-background-color: " + BUTTON_DELETE_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-padding: 8 16; " +
               "-fx-font-size: 13px; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get delete button hover style
     */
    public static String getDeleteButtonHoverStyle() {
        return "-fx-background-color: " + BUTTON_DELETE_HOVER_HEX + "; " +
               "-fx-text-fill: white; " +
               "-fx-background-radius: 8; " +
               "-fx-padding: 8 16; " +
               "-fx-font-size: 13px; " +
               "-fx-cursor: hand;";
    }
    
    /**
     * Get all palette colors as an array
     */
    public static String[] getPaletteColors() {
        return new String[]{
            PALETTE_DARK_BLUE_HEX,
            PALETTE_BLUE_HEX,
            PALETTE_TEAL_HEX,
            PALETTE_GREEN_HEX,
            PALETTE_YELLOW_HEX,
            PALETTE_ORANGE_HEX,
            PALETTE_RED_HEX,
            PALETTE_GRAY_BLUE_HEX
        };
    }
}
