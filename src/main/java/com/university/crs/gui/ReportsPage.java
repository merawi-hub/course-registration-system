package com.university.crs.gui;

import com.university.crs.dao.CourseDao;
import com.university.crs.dao.EnrollmentDao;
import com.university.crs.dao.StudentDao;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Reports page — enrollment summary and totals.
 */
public class ReportsPage {

    public Node build() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(36));
        page.setStyle("-fx-background-color: #f5f5f5;");

        Label heading = new Label("Reports");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        HBox cards = new HBox(20);
        cards.getChildren().addAll(buildSummaryCard(), buildTotalsCard());

        page.getChildren().addAll(heading, cards);
        return page;
    }

    private VBox buildSummaryCard() {
        VBox card = new VBox(12);
        card.setPrefWidth(480);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");
        HBox.setHgrow(card, Priority.ALWAYS);

        Label title = new Label("Enrollment Summary");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefHeight(300);
        area.setStyle("-fx-font-size: 12px; -fx-font-family: monospace;");
        VBox.setVgrow(area, Priority.ALWAYS);

        try {
            List<String> summary = new EnrollmentDao().getEnrollmentSummary();
            if (summary.isEmpty()) {
                area.setText("No courses found.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-12s %-30s %s%n", "Code", "Title", "Enrolled / Capacity"));
                sb.append("-".repeat(58)).append("\n");
                summary.forEach(s -> sb.append(s).append("\n"));
                area.setText(sb.toString());
            }
        } catch (SQLException e) {
            area.setText("Error: " + e.getMessage());
        }

        card.getChildren().addAll(title, area);
        return card;
    }

    private VBox buildTotalsCard() {
        VBox card = new VBox(16);
        card.setPrefWidth(240);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

        Label title = new Label("Totals");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        try {
            int students = new StudentDao().getAllStudents().size();
            int courses  = new CourseDao().getAllCourses().size();
            card.getChildren().addAll(title,
                    totalRow("Total Students", String.valueOf(students), "#4A90D9"),
                    totalRow("Total Courses",  String.valueOf(courses),  "#27AE60"));
        } catch (SQLException e) {
            card.getChildren().addAll(title, new Label("Error: " + e.getMessage()));
        }

        return card;
    }

    private VBox totalRow(String label, String value, String color) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 8px;");

        Label val = new Label(value);
        val.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");

        box.getChildren().addAll(val, lbl);
        return box;
    }
}
