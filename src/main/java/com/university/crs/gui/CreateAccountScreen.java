package com.university.crs.gui;

import com.university.crs.dao.UserDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateAccountScreen {

    private final Stage stage;
    private final UserDao userDao = new UserDao();

    public CreateAccountScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        HBox root = new HBox();
        root.setPrefSize(900, 600);
        root.getChildren().addAll(buildLeftPanel(), buildRightPanel());

        stage.setScene(new Scene(root));
        stage.setTitle("Course Registration System — Create Account");
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

        Label welcome = new Label("Join\nUs!");
        welcome.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label sub = new Label("Create your account");
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
        panel.setPadding(new Insets(50, 70, 50, 70));
        panel.setStyle("-fx-background-color: white;");

        Label title = new Label("Create Account");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label subtitle = new Label("Fill in the details below to register.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888;");

        // Role selector
        Label roleLabel = new Label("Account Type");
        roleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #444444; -fx-font-weight: bold;");
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton adminBtn   = new RadioButton("Admin");
        RadioButton studentBtn = new RadioButton("Student");
        adminBtn.setToggleGroup(roleGroup);
        studentBtn.setToggleGroup(roleGroup);
        adminBtn.setSelected(true);
        adminBtn.setStyle("-fx-font-size: 13px;");
        studentBtn.setStyle("-fx-font-size: 13px;");
        HBox roleRow = new HBox(20, adminBtn, studentBtn);

        Label userLabel = new Label("User Name");
        userLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #444444; -fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose a username");
        LoginScreen.styleTextField(usernameField);

        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #444444; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Choose a password");
        LoginScreen.styleTextField(passwordField);

        Label confirmLabel = new Label("Confirm Password");
        confirmLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #444444; -fx-font-weight: bold;");
        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Re-enter your password");
        LoginScreen.styleTextField(confirmField);

        Label messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setVisible(false);

        Button createBtn = new Button("Create Account");
        LoginScreen.styleButton(createBtn);
        createBtn.setOnAction(e -> {
            String role = adminBtn.isSelected() ? "ADMIN" : "STUDENT";
            handleCreate(usernameField.getText().trim(),
                    passwordField.getText().trim(),
                    confirmField.getText().trim(),
                    role, messageLabel);
        });

        HBox loginRow = new HBox(6);
        loginRow.setAlignment(Pos.CENTER);
        Label existing = new Label("Already have an account?");
        existing.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888;");
        Hyperlink loginLink = new Hyperlink("Login");
        loginLink.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a1a1a; -fx-font-weight: bold; -fx-border-color: transparent;");
        loginLink.setOnAction(e -> new LoginScreen(stage).show());
        loginRow.getChildren().addAll(existing, loginLink);

        VBox.setMargin(title,         new Insets(0, 0, 4, 0));
        VBox.setMargin(subtitle,      new Insets(0, 0, 20, 0));
        VBox.setMargin(roleLabel,     new Insets(0, 0, 6, 0));
        VBox.setMargin(roleRow,       new Insets(0, 0, 16, 0));
        VBox.setMargin(userLabel,     new Insets(0, 0, 6, 0));
        VBox.setMargin(usernameField, new Insets(0, 0, 14, 0));
        VBox.setMargin(passLabel,     new Insets(0, 0, 6, 0));
        VBox.setMargin(passwordField, new Insets(0, 0, 14, 0));
        VBox.setMargin(confirmLabel,  new Insets(0, 0, 6, 0));
        VBox.setMargin(confirmField,  new Insets(0, 0, 6, 0));
        VBox.setMargin(messageLabel,  new Insets(4, 0, 4, 0));
        VBox.setMargin(createBtn,     new Insets(10, 0, 20, 0));

        panel.getChildren().addAll(title, subtitle,
                roleLabel, roleRow,
                userLabel, usernameField,
                passLabel, passwordField,
                confirmLabel, confirmField,
                messageLabel, createBtn, loginRow);
        return panel;
    }

    private void handleCreate(String username, String password,
                               String confirm, String role, Label messageLabel) {
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showMsg(messageLabel, "Please fill in all fields.", false); return;
        }
        if (username.length() < 3) {
            showMsg(messageLabel, "Username must be at least 3 characters.", false); return;
        }
        if (password.length() < 6) {
            showMsg(messageLabel, "Password must be at least 6 characters.", false); return;
        }
        if (!password.equals(confirm)) {
            showMsg(messageLabel, "Passwords do not match.", false); return;
        }
        try {
            if (userDao.usernameExists(username)) {
                showMsg(messageLabel, "Username already taken.", false); return;
            }
            userDao.createAccount(username, password, role);
            showMsg(messageLabel, "Account created! Redirecting to login...", true);
            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(() -> new LoginScreen(stage).show());
            }).start();
        } catch (SQLException e) {
            showMsg(messageLabel, "Error: " + e.getMessage(), false);
        }
    }

    private void showMsg(Label label, String msg, boolean success) {
        label.setText(msg);
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: " + (success ? "#27ae60;" : "#e74c3c;"));
        label.setVisible(true);
    }
}
