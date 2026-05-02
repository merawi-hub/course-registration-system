package com.university.crs.gui;

import com.university.crs.dao.CourseDao;
import com.university.crs.dao.EnrollmentDao;
import com.university.crs.dao.StudentDao;
import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.sql.SQLException;

/**
 * Overview / home page showing summary stat cards.
 */
public class OverviewPage {

    private final User user;

    public OverviewPage(User user) {
        this.user = user;
    }

    public Node build() {
        VBox page = new VBox(24);
        page.setPadding(new Insets(36, 36, 36, 36));
        page.setStyle("-fx-background-color: #f5f5f5;");

        Label heading = new Label("Dashboard Overview");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label sub = new Label("Welcome back, " + user.getUsername() + "!");
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888;");

        // Stat cards
        int students = 0, courses = 0, enrollments = 0;
        try {
            students    = new StudentDao().getAllStudents().size();
            courses     = new CourseDao().getAllCourses().size();
            enrollments = new EnrollmentDao().getEnrollmentSummary().size();
        } catch (SQLException ignored) {}

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                statCard("Total Students",   String.valueOf(students),    "#4A90D9"),
                statCard("Total Courses",    String.valueOf(courses),     "#27AE60"),
                statCard("Active Courses",   String.valueOf(enrollments), "#E67E22")
        );

        page.getChildren().addAll(heading, sub, cards);
        return page;
    }

    private VBox statCard(String title, String value, String color) {
        VBox card = new VBox(8);
        card.setPrefSize(200, 110);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

        Label bar = new Label();
        bar.setPrefSize(36, 4);
        bar.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 2px;");

        Label val = new Label(value);
        val.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");

        card.getChildren().addAll(bar, val, lbl);
        return card;
    }
}
