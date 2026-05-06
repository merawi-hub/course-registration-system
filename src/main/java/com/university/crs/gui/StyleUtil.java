package com.university.crs.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Utility class for consistent UI styling across the application.
 */
public class StyleUtil {

    public static void styleTextField(TextField field) {
        field.setStyle("""
                -fx-background-color: white;
                -fx-border-color: #cccccc;
                -fx-border-radius: 6px;
                -fx-background-radius: 6px;
                -fx-padding: 10px 14px;
                -fx-font-size: 13px;
                """);
        field.setPrefHeight(42);
        field.setMaxWidth(Double.MAX_VALUE);
    }

    public static void styleButton(Button btn) {
        String base = """
                -fx-background-color: #2c2c2c;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 6px;
                -fx-cursor: hand;
                """;
        String hover = """
                -fx-background-color: #444444;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 6px;
                -fx-cursor: hand;
                """;
        btn.setStyle(base);
        btn.setPrefHeight(44);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }
}
