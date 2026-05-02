package com.university.crs.gui;

import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
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

        stage.setScene(new Scene(root, 1100, 680));
        stage.setTitle("CRS — Admin Dashboard");
        stage.setResizable(true);
        stage.show();
    }

    // ── Sidebar ──────────────────────────────────────────────────────────────

    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #1e1e1e;");

        // Brand
        Label brand = new Label("CRS");
        brand.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label role = new Label("Administrator");
        role.setStyle("-fx-font-size: 11px; -fx-text-fill: #aaaaaa;");
        VBox brandBox = new VBox(4, brand, role);
        brandBox.setPadding(new Insets(28, 20, 24, 20));

        // Nav items
        Button[] navBtns = {
            navButton("🏠  Overview",     () -> showPage(new OverviewPage(user).build())),
            navButton("👤  Students",      () -> showPage(new StudentsPage(stage, user).build())),
            navButton("📚  Courses",       () -> showPage(new CoursesPage(stage, user).build())),
            navButton("📋  Enrollments",   () -> showPage(new EnrollmentsPage(stage, user).build())),
            navButton("📊  Reports",       () -> showPage(new ReportsPage().build())),
        };

        VBox navBox = new VBox(4);
        navBox.setPadding(new Insets(0, 12, 0, 12));
        navBox.getChildren().addAll(navBtns);

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // User info + logout
        Label userLabel = new Label("👤 " + user.getUsername());
        userLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12px;");
        Button logoutBtn = navButton("🚪  Logout", () -> new LoginScreen(stage).show());

        VBox bottomBox = new VBox(6, userLabel, logoutBtn);
        bottomBox.setPadding(new Insets(0, 12, 20, 12));

        sidebar.getChildren().addAll(brandBox, navBox, spacer, bottomBox);
        return sidebar;
    }

    private Button navButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        String base = """
                -fx-background-color: transparent;
                -fx-text-fill: #cccccc;
                -fx-font-size: 13px;
                -fx-padding: 10px 16px;
                -fx-background-radius: 6px;
                -fx-cursor: hand;
                """;
        String hover = """
                -fx-background-color: #2e2e2e;
                -fx-text-fill: white;
                -fx-font-size: 13px;
                -fx-padding: 10px 16px;
                -fx-background-radius: 6px;
                -fx-cursor: hand;
                """;
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private void showPage(javafx.scene.Node page) {
        contentArea.getChildren().setAll(page);
    }
}
