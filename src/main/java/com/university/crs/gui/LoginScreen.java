package com.university.crs.gui;

import com.university.crs.dao.UserDao;
import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

public class LoginScreen {

    private final Stage stage;
    private final UserDao userDao = new UserDao();

    public LoginScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #ffffff;"); // Pure white background
        
        // Main card container with enhanced shadow
        HBox card = new HBox();
        card.setMaxWidth(950);
        card.setMaxHeight(600);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 25; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 40, 0.0, 0, 15);"
        );

        // Left section - Branding (Navy Blue)
        VBox brandingSection = buildBrandingSection();
        
        // Right section - Login Form (White)
        VBox formSection = buildFormSection();

        card.getChildren().addAll(brandingSection, formSection);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Course Registration System - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private VBox buildBrandingSection() {
        VBox section = new VBox(30);
        section.setPrefWidth(450);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(60, 50, 60, 50));
        section.setStyle(
            "-fx-background-color: " + ColorScheme.GRADIENT_DARK_BLUE + "; " +
            "-fx-background-radius: 25 0 0 25;"
        );

        // Graduation cap icon
        StackPane icon = createGraduationCapIcon();
        
        // System name with letter spacing
        Label systemName = new Label("COURSE REGISTRATION");
        systemName.setFont(FontLoader.getPoppinsBold(28));
        systemName.setTextFill(Color.WHITE);
        systemName.setStyle("-fx-letter-spacing: 2px;");
        systemName.setAlignment(Pos.CENTER);
        
        Label systemName2 = new Label("SYSTEM");
        systemName2.setFont(FontLoader.getPoppinsBold(28));
        systemName2.setTextFill(Color.WHITE);
        systemName2.setStyle("-fx-letter-spacing: 2px;");
        systemName2.setAlignment(Pos.CENTER);
        
        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(systemName, systemName2);

        // Tagline with subtle styling
        Label tagline = new Label("Empowering Education Through Technology");
        tagline.setFont(FontLoader.getInter(16));
        tagline.setTextFill(Color.rgb(220, 230, 240));
        tagline.setWrapText(true);
        tagline.setAlignment(Pos.CENTER);
        tagline.setMaxWidth(350);
        tagline.setStyle("-fx-text-alignment: center; -fx-opacity: 0.95;");

        // Features list with enhanced styling
        VBox features = new VBox(18);
        features.setAlignment(Pos.CENTER_LEFT);
        features.setPadding(new Insets(25, 0, 0, 0));
        
        Label feature1 = createFeatureLabel("✓ Easy Course Registration");
        Label feature2 = createFeatureLabel("✓ Real-time Enrollment Updates");
        Label feature3 = createFeatureLabel("✓ Secure & Reliable Platform");
        
        features.getChildren().addAll(feature1, feature2, feature3);

        section.getChildren().addAll(icon, titleBox, tagline, features);
        return section;
    }

    private Label createFeatureLabel(String text) {
        Label label = new Label(text);
        label.setFont(FontLoader.getInter(15));
        label.setTextFill(Color.rgb(230, 240, 250));
        label.setStyle("-fx-opacity: 0.9;");
        return label;
    }

    private StackPane createGraduationCapIcon() {
        StackPane iconContainer = new StackPane();
        
        // Try to load image first
        javafx.scene.image.ImageView imageView = ImageLoader.loadImage("graduation-cap.png", 100, 100);
        
        if (imageView != null) {
            // Image loaded successfully
            iconContainer.getChildren().add(imageView);
        } else {
            // Fallback to drawn icon
            Polygon capBoard = new Polygon();
            capBoard.getPoints().addAll(
                0.0, -12.0,
                55.0, 0.0,
                0.0, 12.0,
                -55.0, 0.0
            );
            capBoard.setFill(Color.WHITE);
            capBoard.setStroke(Color.rgb(200, 220, 240));
            capBoard.setStrokeWidth(2.5);
            
            Ellipse capBase = new Ellipse(0, 0, 20, 6);
            capBase.setFill(Color.WHITE);
            capBase.setStroke(Color.rgb(200, 220, 240));
            capBase.setStrokeWidth(2);
            
            Line tasselString = new Line(28, 0, 38, 20);
            tasselString.setStroke(Color.WHITE);
            tasselString.setStrokeWidth(2);
            
            Circle tasselKnot = new Circle(38, 20, 4);
            tasselKnot.setFill(Color.WHITE);
            
            iconContainer.getChildren().addAll(capBoard, capBase, tasselString, tasselKnot);
        }
        
        return iconContainer;
    }

    private VBox buildFormSection() {
        VBox section = new VBox(25);
        section.setPrefWidth(500);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(60, 60, 60, 60));
        section.setStyle("-fx-background-color: white; -fx-background-radius: 0 20 20 0;");

        // Welcome heading
        Label welcome = new Label("Welcome Back!");
        welcome.setFont(FontLoader.getPoppinsBold(36));
        welcome.setTextFill(Color.rgb(26, 26, 26));

        Label subtitle = new Label("Please login to continue");
        subtitle.setFont(FontLoader.getInter(15));
        subtitle.setTextFill(Color.rgb(120, 120, 120));

        VBox headerBox = new VBox(8);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.getChildren().addAll(welcome, subtitle);

        // Username field
        VBox usernameGroup = new VBox(8);
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(FontLoader.getInter(14));
        usernameLabel.setTextFill(Color.rgb(60, 60, 60));
        usernameLabel.setStyle("-fx-font-weight: 600;");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle(
            "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
            "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 14 18; " +
            "-fx-font-size: 15px; " +
            "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
        );
        usernameField.setPrefHeight(50);
        usernameField.setMaxWidth(Double.MAX_VALUE);
        
        // Focus effect
        usernameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                usernameField.setStyle(
                    "-fx-background-color: white; " +
                    "-fx-border-color: " + ColorScheme.MEDIUM_BLUE_HEX + "; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 10; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 14 18; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                );
            } else {
                usernameField.setStyle(
                    "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
                    "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
                    "-fx-border-radius: 10; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 14 18; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                );
            }
        });
        
        usernameGroup.getChildren().addAll(usernameLabel, usernameField);

        // Password field
        VBox passwordGroup = new VBox(8);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(FontLoader.getInter(14));
        passwordLabel.setTextFill(Color.rgb(60, 60, 60));
        passwordLabel.setStyle("-fx-font-weight: 600;");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
            "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
            "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 14 18; " +
            "-fx-font-size: 15px; " +
            "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
        );
        passwordField.setPrefHeight(50);
        passwordField.setMaxWidth(Double.MAX_VALUE);
        
        // Focus effect
        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordField.setStyle(
                    "-fx-background-color: white; " +
                    "-fx-border-color: " + ColorScheme.MEDIUM_BLUE_HEX + "; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 10; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 14 18; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                );
            } else {
                passwordField.setStyle(
                    "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
                    "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
                    "-fx-border-radius: 10; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 14 18; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                );
            }
        });
        
        passwordGroup.getChildren().addAll(passwordLabel, passwordField);

        // Remember me and Forgot password
        HBox optionsRow = new HBox();
        optionsRow.setAlignment(Pos.CENTER);
        
        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setFont(FontLoader.getInter(13));
        rememberMe.setTextFill(Color.rgb(100, 100, 100));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Hyperlink forgotPassword = new Hyperlink("Forgot Password?");
        forgotPassword.setFont(FontLoader.getInter(13));
        forgotPassword.setTextFill(ColorScheme.MEDIUM_BLUE);
        forgotPassword.setStyle(
            "-fx-border-color: transparent; " +
            "-fx-padding: 0; " +
            "-fx-cursor: hand;"
        );
        forgotPassword.setUnderline(false);
        
        // Hover effects
        forgotPassword.setOnMouseEntered(e -> {
            forgotPassword.setTextFill(ColorScheme.LIGHT_BLUE);
            forgotPassword.setUnderline(true);
        });
        forgotPassword.setOnMouseExited(e -> {
            forgotPassword.setTextFill(ColorScheme.MEDIUM_BLUE);
            forgotPassword.setUnderline(false);
        });
        
        optionsRow.getChildren().addAll(rememberMe, spacer, forgotPassword);

        // Error label
        Label errorLabel = new Label();
        errorLabel.setFont(FontLoader.getInter(13));
        errorLabel.setStyle("-fx-text-fill: " + ColorScheme.DANGER_HEX + "; -fx-background-color: #f8d7da; -fx-padding: 10 15; -fx-background-radius: 8; -fx-border-color: #f5c6cb; -fx-border-radius: 8;");
        errorLabel.setVisible(false);
        errorLabel.setMaxWidth(Double.MAX_VALUE);

        // Login button
        Button loginBtn = new Button("LOGIN");
        loginBtn.setFont(FontLoader.getPoppinsBold(14));
        loginBtn.setTextFill(Color.WHITE);
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(48);
        loginBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_PRIMARY_HEX + "; " +
            "-fx-background-radius: 12; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(31, 90, 150, 0.3), 10, 0, 0, 4);"
        );
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_HOVER_HEX + "; " +
            "-fx-background-radius: 12; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(31, 90, 150, 0.4), 15, 0, 0, 6);"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_PRIMARY_HEX + "; " +
            "-fx-background-radius: 12; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(31, 90, 150, 0.3), 10, 0, 0, 4);"
        ));
        
        loginBtn.setOnAction(e -> handleLogin(
                usernameField.getText().trim(),
                passwordField.getText().trim(),
                errorLabel));

        // Register link
        HBox registerRow = new HBox(5);
        registerRow.setAlignment(Pos.CENTER);
        Label noAccount = new Label("Don't have an account?");
        noAccount.setFont(FontLoader.getInter(14));
        noAccount.setTextFill(Color.rgb(100, 100, 100));
        
        Hyperlink registerLink = new Hyperlink("Register here");
        registerLink.setFont(FontLoader.getInter(14));
        registerLink.setTextFill(ColorScheme.MEDIUM_BLUE);
        registerLink.setStyle(
            "-fx-border-color: transparent; " +
            "-fx-padding: 0; " +
            "-fx-cursor: hand;"
        );
        registerLink.setUnderline(false);
        
        // Hover effects
        registerLink.setOnMouseEntered(e -> {
            registerLink.setTextFill(ColorScheme.LIGHT_BLUE);
            registerLink.setUnderline(true);
        });
        registerLink.setOnMouseExited(e -> {
            registerLink.setTextFill(ColorScheme.MEDIUM_BLUE);
            registerLink.setUnderline(false);
        });
        
        // Click action
        registerLink.setOnAction(e -> {
            System.out.println("Register link clicked!"); // Debug message
            new CreateAccountScreen(stage).show();
        });
        
        registerRow.getChildren().addAll(noAccount, registerLink);

        // Add spacing
        VBox.setMargin(headerBox, new Insets(0, 0, 10, 0));
        VBox.setMargin(loginBtn, new Insets(5, 0, 5, 0));

        section.getChildren().addAll(
            headerBox,
            usernameGroup,
            passwordGroup,
            optionsRow,
            errorLabel,
            loginBtn,
            registerRow
        );
        
        // Focus on username field
        usernameField.requestFocus();
        
        // Enter key navigation
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> loginBtn.fire());
        
        return section;
    }

    private void handleLogin(String username, String password, Label errorLabel) {
        // Clear previous error
        errorLabel.setVisible(false);
        
        // Validate username
        if (username.isEmpty()) {
            showError(errorLabel, "Please enter your username.");
            return;
        }
        
        if (username.length() < 3) {
            showError(errorLabel, "Username must be at least 3 characters long.");
            return;
        }
        
        // Check for invalid characters in username
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            showError(errorLabel, "Username can only contain letters, numbers, and underscores.");
            return;
        }
        
        // Validate password
        if (password.isEmpty()) {
            showError(errorLabel, "Please enter your password.");
            return;
        }
        
        if (password.length() < 6) {
            showError(errorLabel, "Password must be at least 6 characters long.");
            return;
        }
        
        // Attempt login
        try {
            User user = userDao.login(username, password);
            if (user != null) {
                if (user.isAdmin()) {
                    new AdminDashboard(stage, user).show();
                } else {
                    new StudentPortal(stage, user).show();
                }
            } else {
                showError(errorLabel, "Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            showError(errorLabel, "Database error: Unable to connect. Please try again later.");
            e.printStackTrace();
        }
    }

    private void showError(Label label, String msg) {
        label.setText(msg);
        label.setVisible(true);
    }
}
