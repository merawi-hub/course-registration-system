package com.university.crs.gui;

import com.university.crs.dao.UserDao;
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

import java.sql.SQLException;

public class CreateAccountScreen {

    private final Stage stage;
    private final UserDao userDao = new UserDao();

    public CreateAccountScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f0f2f5;");
        
        // Main card container with two sections
        HBox card = new HBox();
        card.setMaxWidth(950);
        card.setMaxHeight(650);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 30, 0, 0, 10);");

        // Left section - Branding (Navy Blue) - Same as login
        VBox brandingSection = buildBrandingSection();
        
        // Right section - Registration Form (White) wrapped in ScrollPane
        ScrollPane formScrollPane = buildFormSection();

        card.getChildren().addAll(brandingSection, formScrollPane);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Course Registration System - Register");
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
            "-fx-background-color: " + ColorScheme.GRADIENT_BLUE + "; " +
            "-fx-background-radius: 20 0 0 20;"
        );

        // Graduation cap icon (same as login)
        StackPane icon = createGraduationCapIcon();
        
        // System name
        Label systemName = new Label("COURSE REGISTRATION");
        systemName.setFont(FontLoader.getPoppinsBold(28));
        systemName.setTextFill(Color.WHITE);
        systemName.setStyle("-fx-letter-spacing: 1px;");
        systemName.setAlignment(Pos.CENTER);
        
        Label systemName2 = new Label("SYSTEM");
        systemName2.setFont(FontLoader.getPoppinsBold(28));
        systemName2.setTextFill(Color.WHITE);
        systemName2.setStyle("-fx-letter-spacing: 1px;");
        systemName2.setAlignment(Pos.CENTER);
        
        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(systemName, systemName2);

        // Tagline
        Label tagline = new Label("Join Our Academic Community");
        tagline.setFont(FontLoader.getInter(16));
        tagline.setTextFill(Color.rgb(200, 220, 240));
        tagline.setWrapText(true);
        tagline.setAlignment(Pos.CENTER);
        tagline.setMaxWidth(350);
        tagline.setStyle("-fx-text-alignment: center;");

        // Features list
        VBox features = new VBox(15);
        features.setAlignment(Pos.CENTER_LEFT);
        features.setPadding(new Insets(20, 0, 0, 0));
        
        Label feature1 = createFeatureLabel("✓ Quick & Easy Registration");
        Label feature2 = createFeatureLabel("✓ Access to All Courses");
        Label feature3 = createFeatureLabel("✓ Secure Account Management");
        
        features.getChildren().addAll(feature1, feature2, feature3);

        section.getChildren().addAll(icon, titleBox, tagline, features);
        return section;
    }

    private Label createFeatureLabel(String text) {
        Label label = new Label(text);
        label.setFont(FontLoader.getInter(15));
        label.setTextFill(Color.rgb(220, 230, 240));
        return label;
    }

    private StackPane createGraduationCapIcon() {
        StackPane iconContainer = new StackPane();
        
        // Try to load image first
        javafx.scene.image.ImageView imageView = ImageLoader.loadImage("graduation-cap.png", 100, 100);
        
        if (imageView != null) {
            iconContainer.getChildren().add(imageView);
        } else {
            // Fallback to drawn icon
            Polygon capBoard = new Polygon();
            capBoard.getPoints().addAll(0.0, -12.0, 55.0, 0.0, 0.0, 12.0, -55.0, 0.0);
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

    private ScrollPane buildFormSection() {
        // Create content VBox
        VBox content = new VBox(15);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 50, 40, 50));

        // Welcome heading
        Label welcome = new Label("Create Student Account");
        welcome.setFont(FontLoader.getPoppinsBold(28));
        welcome.setTextFill(Color.rgb(26, 26, 26));

        Label subtitle = new Label("Fill in the details to create your account");
        subtitle.setFont(FontLoader.getInter(13));
        subtitle.setTextFill(Color.rgb(120, 120, 120));

        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.getChildren().addAll(welcome, subtitle);

        // Full Name field
        VBox fullNameGroup = createInputGroup("Full Name", "Enter your full name");
        TextField fullNameField = (TextField) ((VBox) fullNameGroup.getChildren().get(1)).getChildren().get(0);

        // Username field
        VBox usernameGroup = createInputGroup("Username", "Choose a username");
        TextField usernameField = (TextField) ((VBox) usernameGroup.getChildren().get(1)).getChildren().get(0);

        // Email field
        VBox emailGroup = createInputGroup("Email", "Enter your email address");
        TextField emailField = (TextField) ((VBox) emailGroup.getChildren().get(1)).getChildren().get(0);

        // Password field
        VBox passwordGroup = createPasswordGroup("Password", "Create a password");
        PasswordField passwordField = (PasswordField) ((VBox) passwordGroup.getChildren().get(1)).getChildren().get(0);

        // Confirm Password field
        VBox confirmGroup = createPasswordGroup("Confirm Password", "Re-enter your password");
        PasswordField confirmField = (PasswordField) ((VBox) confirmGroup.getChildren().get(1)).getChildren().get(0);

        // Department dropdown
        VBox departmentGroup = new VBox(6);
        Label departmentLabel = new Label("Select Department");
        departmentLabel.setFont(FontLoader.getInter(14));
        departmentLabel.setTextFill(Color.rgb(60, 60, 60));
        departmentLabel.setStyle("-fx-font-weight: 600;");
        
        ComboBox<String> departmentCombo = new ComboBox<>();
        departmentCombo.getItems().addAll(
            "Computer Science",
            "Engineering",
            "Business Administration",
            "Mathematics",
            "Physics",
            "Chemistry",
            "Biology",
            "English Literature",
            "History",
            "Psychology"
        );
        departmentCombo.setPromptText("Select Department");
        departmentCombo.setMaxWidth(Double.MAX_VALUE);
        departmentCombo.setPrefHeight(45);
        departmentCombo.setStyle(
            "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
            "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-font-size: 15px; " +
            "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
        );
        
        departmentGroup.getChildren().addAll(departmentLabel, departmentCombo);

        // Error/Success message
        Label messageLabel = new Label();
        messageLabel.setFont(FontLoader.getInter(13));
        messageLabel.setWrapText(true);
        messageLabel.setVisible(false);
        messageLabel.setMaxWidth(Double.MAX_VALUE);

        // Register button
        Button registerBtn = new Button("REGISTER");
        registerBtn.setFont(FontLoader.getPoppinsBold(14));
        registerBtn.setTextFill(Color.WHITE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setPrefHeight(48);
        registerBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_PRIMARY_HEX + "; " +
            "-fx-background-radius: 12; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(31, 90, 150, 0.3), 10, 0, 0, 4);"
        );
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_HOVER_HEX + "; " +
            "-fx-background-radius: 12; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(31, 90, 150, 0.4), 15, 0, 0, 6);"
        ));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_PRIMARY_HEX + "; " +
            "-fx-background-radius: 12; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(31, 90, 150, 0.3), 10, 0, 0, 4);"
        ));
        
        registerBtn.setOnAction(e -> handleCreate(
                fullNameField.getText().trim(),
                usernameField.getText().trim(),
                emailField.getText().trim(),
                passwordField.getText().trim(),
                confirmField.getText().trim(),
                departmentCombo.getValue(),
                messageLabel));

        // Login link
        HBox loginRow = new HBox(5);
        loginRow.setAlignment(Pos.CENTER);
        Label hasAccount = new Label("Already have an account?");
        hasAccount.setFont(FontLoader.getInter(13));
        hasAccount.setTextFill(Color.rgb(100, 100, 100));
        
        Hyperlink loginLink = new Hyperlink("Login here");
        loginLink.setFont(FontLoader.getInter(13));
        loginLink.setStyle("-fx-text-fill: " + ColorScheme.MEDIUM_BLUE_HEX + "; -fx-border-color: transparent; -fx-font-weight: 600;");
        loginLink.setOnMouseEntered(e -> loginLink.setStyle("-fx-text-fill: " + ColorScheme.LIGHT_BLUE_HEX + "; -fx-border-color: transparent; -fx-font-weight: 600; -fx-underline: true;"));
        loginLink.setOnMouseExited(e -> loginLink.setStyle("-fx-text-fill: " + ColorScheme.MEDIUM_BLUE_HEX + "; -fx-border-color: transparent; -fx-font-weight: 600;"));
        loginLink.setOnAction(e -> new LoginScreen(stage).show());
        
        loginRow.getChildren().addAll(hasAccount, loginLink);

        // Add spacing
        VBox.setMargin(headerBox, new Insets(0, 0, 10, 0));
        VBox.setMargin(registerBtn, new Insets(5, 0, 5, 0));

        content.getChildren().addAll(
            headerBox,
            fullNameGroup,
            usernameGroup,
            emailGroup,
            passwordGroup,
            confirmGroup,
            departmentGroup,
            messageLabel,
            registerBtn,
            loginRow
        );
        
        // Focus on first field
        fullNameField.requestFocus();
        
        // Wrap content in ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setPrefWidth(500);
        scrollPane.setStyle(
            "-fx-background: white; " +
            "-fx-background-color: white; " +
            "-fx-border-color: transparent; " +
            "-fx-background-radius: 0 20 20 0;"
        );
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        return scrollPane;
    }

    private VBox createInputGroup(String labelText, String promptText) {
        VBox group = new VBox(6);
        Label label = new Label(labelText);
        label.setFont(FontLoader.getInter(14));
        label.setTextFill(Color.rgb(60, 60, 60));
        label.setStyle("-fx-font-weight: 600;");
        
        VBox fieldContainer = new VBox();
        TextField field = new TextField();
        field.setPromptText(promptText);
        styleInputField(field);
        fieldContainer.getChildren().add(field);
        
        group.getChildren().addAll(label, fieldContainer);
        return group;
    }

    private VBox createPasswordGroup(String labelText, String promptText) {
        VBox group = new VBox(6);
        Label label = new Label(labelText);
        label.setFont(FontLoader.getInter(14));
        label.setTextFill(Color.rgb(60, 60, 60));
        label.setStyle("-fx-font-weight: 600;");
        
        VBox fieldContainer = new VBox();
        PasswordField field = new PasswordField();
        field.setPromptText(promptText);
        styleInputField(field);
        fieldContainer.getChildren().add(field);
        
        group.getChildren().addAll(label, fieldContainer);
        return group;
    }

    private void styleInputField(TextField field) {
        field.setStyle(
            "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
            "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10; " +
            "-fx-padding: 12 16; " +
            "-fx-font-size: 15px; " +
            "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
        );
        field.setPrefHeight(45);
        field.setMaxWidth(Double.MAX_VALUE);
        
        // Focus effect
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle(
                    "-fx-background-color: white; " +
                    "-fx-border-color: " + ColorScheme.MEDIUM_BLUE_HEX + "; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 10; " +
                    "-fx-background-radius: 10; " +
                    "-fx-padding: 12 16; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                );
            } else {
                // Validate on blur
                String text = field.getText().trim();
                boolean isValid = !text.isEmpty();
                
                if (isValid) {
                    field.setStyle(
                        "-fx-background-color: " + ColorScheme.BACKGROUND_HEX + "; " +
                        "-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 12 16; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                    );
                } else {
                    field.setStyle(
                        "-fx-background-color: #fff5f5; " +
                        "-fx-border-color: " + ColorScheme.DANGER_HEX + "; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 12 16; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
                    );
                }
            }
        });
    }

    private void handleCreate(String fullName, String username, String email,
                               String password, String confirm, String department,
                               Label messageLabel) {
        // Clear previous message
        messageLabel.setVisible(false);
        
        // Validate full name
        if (fullName.isEmpty()) {
            showMsg(messageLabel, "Please enter your full name.", false);
            return;
        }
        if (fullName.length() < 3) {
            showMsg(messageLabel, "Full name must be at least 3 characters long.", false);
            return;
        }
        if (!fullName.matches("^[a-zA-Z\\s]+$")) {
            showMsg(messageLabel, "Full name can only contain letters and spaces.", false);
            return;
        }
        
        // Validate username
        if (username.isEmpty()) {
            showMsg(messageLabel, "Please enter a username.", false);
            return;
        }
        if (username.length() < 3) {
            showMsg(messageLabel, "Username must be at least 3 characters long.", false);
            return;
        }
        if (username.length() > 20) {
            showMsg(messageLabel, "Username must not exceed 20 characters.", false);
            return;
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            showMsg(messageLabel, "Username can only contain letters, numbers, and underscores.", false);
            return;
        }
        
        // Validate email
        if (email.isEmpty()) {
            showMsg(messageLabel, "Please enter your email address.", false);
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            showMsg(messageLabel, "Please enter a valid email address (e.g., user@example.com).", false);
            return;
        }
        
        // Validate password
        if (password.isEmpty()) {
            showMsg(messageLabel, "Please enter a password.", false);
            return;
        }
        if (password.length() < 6) {
            showMsg(messageLabel, "Password must be at least 6 characters long.", false);
            return;
        }
        if (password.length() > 50) {
            showMsg(messageLabel, "Password must not exceed 50 characters.", false);
            return;
        }
        // Check password strength
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        
        if (!hasUpper || !hasLower || !hasDigit) {
            showMsg(messageLabel, "Password must contain at least one uppercase letter, one lowercase letter, and one number.", false);
            return;
        }
        
        // Validate confirm password
        if (confirm.isEmpty()) {
            showMsg(messageLabel, "Please confirm your password.", false);
            return;
        }
        if (!password.equals(confirm)) {
            showMsg(messageLabel, "Passwords do not match. Please try again.", false);
            return;
        }
        
        // Validate department
        if (department == null || department.isEmpty()) {
            showMsg(messageLabel, "Please select a department.", false);
            return;
        }
        
        // Attempt to create account
        try {
            if (userDao.usernameExists(username)) {
                showMsg(messageLabel, "Username already taken. Please choose a different username.", false);
                return;
            }
            
            // Create student account (role is always STUDENT for this form)
            userDao.createAccount(username, password, "STUDENT");
            showMsg(messageLabel, "Account created successfully! Redirecting to login...", true);
            
            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(() -> new LoginScreen(stage).show());
            }).start();
        } catch (SQLException e) {
            showMsg(messageLabel, "Error creating account. Please try again later.", false);
            e.printStackTrace();
        }
    }

    private void showMsg(Label label, String msg, boolean success) {
        label.setText(msg);
        if (success) {
            label.setStyle(
                "-fx-text-fill: " + ColorScheme.SUCCESS_HEX + "; " +
                "-fx-background-color: #d4edda; " +
                "-fx-padding: 10 15; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #c3e6cb; " +
                "-fx-border-radius: 8; " +
                "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
            );
        } else {
            label.setStyle(
                "-fx-text-fill: " + ColorScheme.DANGER_HEX + "; " +
                "-fx-background-color: #f8d7da; " +
                "-fx-padding: 10 15; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #f5c6cb; " +
                "-fx-border-radius: 8; " +
                "-fx-font-family: " + FontLoader.getInterFontFamily() + ";"
            );
        }
        label.setVisible(true);
    }
}
