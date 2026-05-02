package com.university.crs.gui;

import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Student portal — full implementation coming next.
 */
public class StudentPortal {

    private final Stage stage;
    private final User  user;

    public StudentPortal(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: white;");

        Label welcome = new Label("Welcome, " + user.getUsername() + "!");
        welcome.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label sub = new Label("Student Portal — coming soon.");
        sub.setStyle("-fx-font-size: 14px; -fx-text-fill: #888888;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #2c2c2c; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 6px; -fx-padding: 10px 30px; -fx-cursor: hand;");
        logoutBtn.setOnAction(e -> new LoginScreen(stage).show());

        root.getChildren().addAll(welcome, sub, logoutBtn);
        stage.setScene(new Scene(root, 1100, 680));
        stage.setTitle("CRS — Student Portal");
    }
}
