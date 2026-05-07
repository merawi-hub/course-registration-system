package com.university.crs.gui;

import com.university.crs.dao.CourseDao;
import com.university.crs.dao.EnrollmentDao;
import com.university.crs.dao.StudentDao;
import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;

/**
 * Overview / home page showing summary stat cards and recent registrations.
 */
public class OverviewPage {

    private final User user;

    public OverviewPage(User user) {
        this.user = user;
    }

    public Node build() {
        VBox page = new VBox(30);
        page.setPadding(new Insets(40, 50, 40, 50));
        page.setStyle("-fx-background-color: " + ColorScheme.BACKGROUND_HEX + ";");

        // Page heading
        Label heading = new Label("Admin Dashboard");
        heading.setFont(FontLoader.getPoppinsBold(28));
        heading.setTextFill(ColorScheme.DARK_TEXT);

        // Stat cards
        int students = 0, courses = 0, enrollments = 0, departments = 8;
        try {
            students    = new StudentDao().getAllStudents().size();
            courses     = new CourseDao().getAllCourses().size();
            enrollments = new EnrollmentDao().getEnrollmentSummary().size();
        } catch (SQLException ignored) {}

        HBox cards = new HBox(25);
        cards.getChildren().addAll(
                statCard("👥", "Total Students", String.valueOf(students), ColorScheme.LIGHT_BLUE_HEX),
                statCard("📚", "Total Courses", String.valueOf(courses), ColorScheme.SUCCESS_HEX),
                statCard("📋", "Total Registrations", String.valueOf(enrollments), ColorScheme.PURPLE_HEX),
                statCard("🏢", "Departments", String.valueOf(departments), ColorScheme.WARNING_HEX)
        );

        // Recent Registrations Section
        VBox recentSection = buildRecentRegistrations();

        page.getChildren().addAll(heading, cards, recentSection);
        
        ScrollPane scrollPane = new ScrollPane(page);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + ColorScheme.BACKGROUND_HEX + "; -fx-background-color: " + ColorScheme.BACKGROUND_HEX + ";");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        return scrollPane;
    }

    private VBox statCard(String icon, String title, String value, String color) {
        VBox card = new VBox(12);
        card.setPrefSize(220, 120);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 3);"
        );

        // Icon with colored background
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(50, 50);
        iconContainer.setStyle(
            "-fx-background-color: " + color + "; " +
            "-fx-background-radius: 10;"
        );
        
        Label iconLabel = new Label(icon);
        iconLabel.setFont(FontLoader.getInter(26));
        iconLabel.setTextFill(Color.WHITE);
        iconContainer.getChildren().add(iconLabel);

        // Title
        Label titleLabel = new Label(title);
        titleLabel.setFont(FontLoader.getInter(13));
        titleLabel.setTextFill(Color.rgb(120, 120, 120));

        // Value
        Label valueLabel = new Label(value);
        valueLabel.setFont(FontLoader.getPoppinsBold(32));
        valueLabel.setTextFill(ColorScheme.DARK_TEXT);

        card.getChildren().addAll(iconContainer, titleLabel, valueLabel);
        return card;
    }

    private VBox buildRecentRegistrations() {
        VBox section = new VBox(20);
        
        Label sectionTitle = new Label("Recent Registrations");
        sectionTitle.setFont(FontLoader.getPoppinsBold(20));
        sectionTitle.setTextFill(ColorScheme.DARK_TEXT);

        // Table container
        VBox tableContainer = new VBox();
        tableContainer.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 3);"
        );
        tableContainer.setPadding(new Insets(25));

        // Table header
        HBox header = new HBox();
        header.setSpacing(20);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        
        Label col1 = createHeaderLabel("Student Name", 250);
        Label col2 = createHeaderLabel("Course Name", 350);
        Label col3 = createHeaderLabel("Date", 150);
        
        header.getChildren().addAll(col1, col2, col3);

        // Table rows
        VBox rows = new VBox(0);
        rows.getChildren().addAll(
            createTableRow("John Doe", "CS101 - Introduction to CS", "2024-06-20"),
            createTableRow("Jane Smith", "MATH201 - Discrete Math", "2024-06-20"),
            createTableRow("Mike Johnson", "ENG101 - English Comm.", "2024-05-19"),
            createTableRow("Sarah Williams", "PHY101 - Physics Fund.", "2024-05-19")
        );

        // View All button
        Button viewAllBtn = new Button("View All");
        viewAllBtn.setFont(FontLoader.getPoppinsBold(14));
        viewAllBtn.setTextFill(Color.WHITE);
        viewAllBtn.setPrefWidth(150);
        viewAllBtn.setPrefHeight(45);
        viewAllBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_PRIMARY_HEX + "; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        viewAllBtn.setOnMouseEntered(e -> viewAllBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_HOVER_HEX + "; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        ));
        viewAllBtn.setOnMouseExited(e -> viewAllBtn.setStyle(
            "-fx-background-color: " + ColorScheme.BUTTON_PRIMARY_HEX + "; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        ));

        HBox buttonContainer = new HBox(viewAllBtn);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));

        tableContainer.getChildren().addAll(header, rows, buttonContainer);
        section.getChildren().addAll(sectionTitle, tableContainer);
        
        return section;
    }

    private Label createHeaderLabel(String text, double width) {
        Label label = new Label(text);
        label.setFont(FontLoader.getInter(13));
        label.setTextFill(Color.rgb(100, 100, 100));
        label.setStyle("-fx-font-weight: 600;");
        label.setPrefWidth(width);
        return label;
    }

    private HBox createTableRow(String studentName, String courseName, String date) {
        HBox row = new HBox();
        row.setSpacing(20);
        row.setPadding(new Insets(15, 0, 15, 0));
        row.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");
        
        Label col1 = createCellLabel(studentName, 250);
        Label col2 = createCellLabel(courseName, 350);
        Label col3 = createCellLabel(date, 150);
        
        row.getChildren().addAll(col1, col2, col3);
        
        // Hover effect
        row.setOnMouseEntered(e -> row.setStyle(
            "-fx-background-color: #f9fafb; " +
            "-fx-border-color: #f3f4f6; " +
            "-fx-border-width: 0 0 1 0; " +
            "-fx-cursor: hand;"
        ));
        row.setOnMouseExited(e -> row.setStyle(
            "-fx-border-color: #f3f4f6; " +
            "-fx-border-width: 0 0 1 0;"
        ));
        
        return row;
    }

    private Label createCellLabel(String text, double width) {
        Label label = new Label(text);
        label.setFont(FontLoader.getInter(14));
        label.setTextFill(Color.rgb(60, 60, 60));
        label.setPrefWidth(width);
        return label;
    }
}
