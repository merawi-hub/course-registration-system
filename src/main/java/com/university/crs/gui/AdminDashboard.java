package com.university.crs.gui;

import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Admin dashboard with sidebar navigation and dynamic content area.
 */
public class AdminDashboard {

    private final Stage stage;
    private final User  user;

    // Content area — swapped when sidebar items are clicked
    private final StackPane contentArea = new StackPane();

    public AdminDashboard(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setLeft(buildSidebar());
        root.setCenter(contentArea);

        // Default page
        showPage(new OverviewPage(user).build());

        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Course Registration System - Admin Dashboard");
        stage.setResizable(true);
        stage.show();
    }

    // ── Sidebar ──────────────────────────────────────────────────────────────

    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(230);
        sidebar.setStyle("-fx-background-color: " + ColorScheme.GRADIENT_SIDEBAR + ";");

        // User Profile Section
        VBox profileBox = new VBox(8);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(30, 20, 25, 20));
        
        // Profile icon
        HBox profileHeader = new HBox(12);
        profileHeader.setAlignment(Pos.CENTER_LEFT);
        
        Circle avatar = new Circle(25);
        avatar.setFill(Color.WHITE);
        avatar.setStroke(ColorScheme.LIGHT_BLUE);
        avatar.setStrokeWidth(2);
        
        // User icon (simple person shape)
        StackPane avatarContainer = new StackPane();
        Circle head = new Circle(8);
        head.setFill(ColorScheme.DEEP_BLUE);
        head.setTranslateY(-5);
        
        Circle body = new Circle(12);
        body.setFill(ColorScheme.DEEP_BLUE);
        body.setTranslateY(8);
        
        avatarContainer.getChildren().addAll(avatar, head, body);
        
        VBox userInfo = new VBox(2);
        Label userName = new Label("Admin User");
        userName.setFont(FontLoader.getPoppinsBold(15));
        userName.setTextFill(Color.WHITE);
        
        Label userRole = new Label("Administrator");
        userRole.setFont(FontLoader.getInter(12));
        userRole.setTextFill(Color.rgb(200, 220, 240));
        
        userInfo.getChildren().addAll(userName, userRole);
        profileHeader.getChildren().addAll(avatarContainer, userInfo);
        profileBox.getChildren().add(profileHeader);

        // Nav items
        VBox navBox = new VBox(6);
        navBox.setPadding(new Insets(10, 15, 0, 15));
        
        Button dashboardBtn = navButton("🏠", "Dashboard", true, () -> showPage(new OverviewPage(user).build()));
        Button coursesBtn = navButton("📚", "Manage Courses", false, () -> showPage(new CoursesPage(stage, user).build()));
        Button studentsBtn = navButton("👥", "Manage Students", false, () -> showPage(new StudentsPage(stage, user).build()));
        Button registrationsBtn = navButton("📋", "Registrations", false, () -> showPage(new EnrollmentsPage(stage, user).build()));
        Button reportsBtn = navButton("📊", "Reports", false, () -> showPage(new ReportsPage().build()));
        Button profileBtn = navButton("👤", "Profile", false, () -> showPage(new ProfilePage(user).build()));
        Button logoutBtn = navButton("🚪", "Logout", false, () -> new LoginScreen(stage).show());
        
        navBox.getChildren().addAll(
            dashboardBtn,
            coursesBtn,
            studentsBtn,
            registrationsBtn,
            reportsBtn,
            profileBtn,
            logoutBtn
        );

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(profileBox, navBox, spacer);
        return sidebar;
    }

    private Button navButton(String icon, String text, boolean isActive, Runnable action) {
        Button btn = new Button();
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPrefHeight(45);
        
        // Create button content
        HBox content = new HBox(12);
        content.setAlignment(Pos.CENTER_LEFT);
        
        Label iconLabel = new Label(icon);
        iconLabel.setFont(FontLoader.getInter(18));
        iconLabel.setMinWidth(25);
        iconLabel.setTextFill(Color.WHITE);
        
        Label textLabel = new Label(text);
        textLabel.setFont(FontLoader.getInter(14));
        textLabel.setTextFill(Color.WHITE);
        
        content.getChildren().addAll(iconLabel, textLabel);
        btn.setGraphic(content);
        
        if (isActive) {
            btn.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.15); " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 8; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 10 15;"
            );
        } else {
            btn.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 8; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 10 15;"
            );
        }
        
        btn.setOnMouseEntered(e -> {
            if (!isActive) {
                btn.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 8; " +
                    "-fx-cursor: hand; " +
                    "-fx-padding: 10 15;"
                );
            }
        });
        
        btn.setOnMouseExited(e -> {
            if (!isActive) {
                btn.setStyle(
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 8; " +
                    "-fx-cursor: hand; " +
                    "-fx-padding: 10 15;"
                );
            }
        });
        
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void showPage(javafx.scene.Node page) {
        contentArea.getChildren().setAll(page);
    }
}
