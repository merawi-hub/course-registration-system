package com.university.crs.gui;

import com.university.crs.dao.UserDao;
import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginScreen {

    private final Stage stage;
    private final UserDao userDao = new UserDao();

    public LoginScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        HBox root = new HBox();
        root.setPrefSize(900, 600);
        root.getChildren().addAll(buildLeftPanel(), buildRightPanel());

        stage.setTitle("Course Registration System");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private StackPane buildLeftPanel() {
        StackPane panel = new StackPane();
        panel.setPrefWidth(360);
        panel.setStyle("-fx-background-color: #2c2c2c;");

        VBox content = new VBox(12);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(0, 40, 0, 40));

        Label logo = new Label("CRS");
        logo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label welcome = new Label("Welcome\nBack!");
        welcome.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: white;");
        welcome.setWrapText(true);

        Label sub = new Label("Course Registration System");
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #cccccc;");

        VBox.setMargin(welcome, new Insets(60, 0, 0, 0));
        content.getChildren().addAll(logo, welcome, sub);
        panel.getChildren().add(content);
        return panel;
    }

    private VBox buildRightPanel() {
        VBox panel = new VBox();
        panel.setPrefWidth(540);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(60, 70, 60, 70));
        panel.setStyle("-fx-background-color: white;");

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label subtitle = new Label("Welcome back! Please login to your account.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888;");

        Label userLabel = new Label("User Name");
        userLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #444444; -fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        styleTextField(usernameField);

        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #444444; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        styleTextField(passwordField);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        Button loginBtn = new Button("Login");
        styleButton(loginBtn);
        loginBtn.setOnAction(e -> handleLogin(
                usernameField.getText().trim(),
                passwordField.getText().trim(),
                errorLabel));

        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> loginBtn.fire());

        HBox signupRow = new HBox(6);
        signupRow.setAlignment(Pos.CENTER);
        Label newUser = new Label("New User?");
        newUser.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888;");
        Hyperlink signupLink = new Hyperlink("Create Account");
        signupLink.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a1a1a; -fx-font-weight: bold; -fx-border-color: transparent;");
        signupLink.setOnAction(e -> new CreateAccountScreen(stage).show());
        signupRow.getChildren().addAll(newUser, signupLink);

        VBox.setMargin(title,         new Insets(0, 0, 4, 0));
        VBox.setMargin(subtitle,      new Insets(0, 0, 28, 0));
        VBox.setMargin(userLabel,     new Insets(0, 0, 6, 0));
        VBox.setMargin(usernameField, new Insets(0, 0, 18, 0));
        VBox.setMargin(passLabel,     new Insets(0, 0, 6, 0));
        VBox.setMargin(passwordField, new Insets(0, 0, 6, 0));
        VBox.setMargin(errorLabel,    new Insets(0, 0, 10, 0));
        VBox.setMargin(loginBtn,      new Insets(10, 0, 20, 0));

        panel.getChildren().addAll(title, subtitle,
                userLabel, usernameField,
                passLabel, passwordField,
                errorLabel, loginBtn, signupRow);
        return panel;
    }

    private void handleLogin(String username, String password, Label errorLabel) {
        if (username.isEmpty() || password.isEmpty()) {
            showError(errorLabel, "Please fill in all fields.");
            return;
        }
        try {
            User user = userDao.login(username, password);
            if (user != null) {
                if (user.isAdmin()) {
                    new AdminDashboard(stage, user).show();
                } else {
                    new StudentPortal(stage, user).show();
                }
            } else {
                showError(errorLabel, "Invalid username or password.");
            }
        } catch (SQLException e) {
            showError(errorLabel, "Database error: " + e.getMessage());
        }
    }

    private void showError(Label label, String msg) {
        label.setText(msg);
        label.setVisible(true);
    }

    static void styleTextField(TextField field) {
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

    static void styleButton(Button btn) {
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
