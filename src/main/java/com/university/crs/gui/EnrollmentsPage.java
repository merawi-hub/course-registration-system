package com.university.crs.gui;

import com.university.crs.dao.CourseDao;
import com.university.crs.dao.EnrollmentDao;
import com.university.crs.dao.StudentDao;
import com.university.crs.model.Course;
import com.university.crs.model.Student;
import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Enrollments management page.
 */
public class EnrollmentsPage {

    private final Stage stage;
    private final User  user;
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final StudentDao    studentDao    = new StudentDao();
    private final CourseDao     courseDao     = new CourseDao();

    private ComboBox<Student> studentCombo;
    private ComboBox<Course>  courseCombo;
    private TextArea          resultArea;

    public EnrollmentsPage(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public Node build() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(36));
        page.setStyle("-fx-background-color: #f5f5f5;");

        Label heading = new Label("Manage Enrollments");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        HBox content = new HBox(24);
        content.getChildren().addAll(buildEnrollPanel(), buildViewPanel());

        page.getChildren().addAll(heading, content);
        return page;
    }

    // ── Enroll / Drop panel ──────────────────────────────────────────────────

    private VBox buildEnrollPanel() {
        VBox panel = new VBox(14);
        panel.setPrefWidth(340);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

        Label title = new Label("Enroll / Drop");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label sLabel = new Label("Select Student");
        sLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        studentCombo = new ComboBox<>();
        studentCombo.setMaxWidth(Double.MAX_VALUE);
        studentCombo.setPromptText("Choose student...");
        loadStudents();

        Label cLabel = new Label("Select Course");
        cLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        courseCombo = new ComboBox<>();
        courseCombo.setMaxWidth(Double.MAX_VALUE);
        courseCombo.setPromptText("Choose course...");
        loadCourses();

        Label msgLabel = new Label();
        msgLabel.setWrapText(true);
        msgLabel.setStyle("-fx-font-size: 11px;");
        msgLabel.setVisible(false);

        Button enrollBtn = new Button("Enroll");
        enrollBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 6px; -fx-cursor: hand;");
        enrollBtn.setMaxWidth(Double.MAX_VALUE);
        enrollBtn.setPrefHeight(38);
        enrollBtn.setOnAction(e -> handleEnroll(msgLabel));

        Button dropBtn = new Button("Drop");
        dropBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 6px; -fx-cursor: hand;");
        dropBtn.setMaxWidth(Double.MAX_VALUE);
        dropBtn.setPrefHeight(38);
        dropBtn.setOnAction(e -> handleDrop(msgLabel));

        panel.getChildren().addAll(title, sLabel, studentCombo, cLabel, courseCombo,
                msgLabel, enrollBtn, dropBtn);
        return panel;
    }

    // ── View panel ───────────────────────────────────────────────────────────

    private VBox buildViewPanel() {
        VBox panel = new VBox(14);
        panel.setPrefWidth(400);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");
        HBox.setHgrow(panel, Priority.ALWAYS);

        Label title = new Label("View Enrollments");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        // View courses by student
        Label sLabel = new Label("Courses enrolled by student:");
        sLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        ComboBox<Student> viewStudentCombo = new ComboBox<>();
        viewStudentCombo.setMaxWidth(Double.MAX_VALUE);
        viewStudentCombo.setPromptText("Choose student...");
        loadStudentsInto(viewStudentCombo);

        Button viewByStudentBtn = new Button("View Courses");
        viewByStudentBtn.setStyle("-fx-background-color: #2c2c2c; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 6px; -fx-cursor: hand;");
        viewByStudentBtn.setMaxWidth(Double.MAX_VALUE);
        viewByStudentBtn.setPrefHeight(36);
        viewByStudentBtn.setOnAction(e -> {
            Student s = viewStudentCombo.getValue();
            if (s == null) return;
            try {
                List<String> courses = enrollmentDao.getCoursesByStudent(s.getId());
                resultArea.setText("Courses for " + s.getName() + ":\n\n" +
                        (courses.isEmpty() ? "No enrollments." : String.join("\n", courses)));
            } catch (SQLException ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        // View students by course
        Label cLabel = new Label("Students enrolled in course:");
        cLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        ComboBox<Course> viewCourseCombo = new ComboBox<>();
        viewCourseCombo.setMaxWidth(Double.MAX_VALUE);
        viewCourseCombo.setPromptText("Choose course...");
        loadCoursesInto(viewCourseCombo);

        Button viewByCourseBtn = new Button("View Students");
        viewByCourseBtn.setStyle("-fx-background-color: #2c2c2c; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 6px; -fx-cursor: hand;");
        viewByCourseBtn.setMaxWidth(Double.MAX_VALUE);
        viewByCourseBtn.setPrefHeight(36);
        viewByCourseBtn.setOnAction(e -> {
            Course c = viewCourseCombo.getValue();
            if (c == null) return;
            try {
                List<String> students = enrollmentDao.getStudentsByCourse(c.getId());
                resultArea.setText("Students in " + c.getTitle() + ":\n\n" +
                        (students.isEmpty() ? "No students enrolled." : String.join("\n", students)));
            } catch (SQLException ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefHeight(180);
        resultArea.setStyle("-fx-font-size: 12px; -fx-background-color: #f9f9f9;");
        VBox.setVgrow(resultArea, Priority.ALWAYS);

        panel.getChildren().addAll(title,
                sLabel, viewStudentCombo, viewByStudentBtn,
                cLabel, viewCourseCombo, viewByCourseBtn,
                resultArea);
        return panel;
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    private void handleEnroll(Label msgLabel) {
        Student s = studentCombo.getValue();
        Course  c = courseCombo.getValue();
        if (s == null || c == null) {
            showMsg(msgLabel, "Select both a student and a course.", false); return;
        }
        try {
            enrollmentDao.enroll(s.getId(), c.getId());
            showMsg(msgLabel, s.getName() + " enrolled in " + c.getTitle(), true);
        } catch (SQLException e) {
            showMsg(msgLabel, e.getMessage().contains("uq_enrollment") || e.getMessage().contains("Duplicate")
                    ? "Already enrolled." : e.getMessage(), false);
        }
    }

    private void handleDrop(Label msgLabel) {
        Student s = studentCombo.getValue();
        Course  c = courseCombo.getValue();
        if (s == null || c == null) {
            showMsg(msgLabel, "Select both a student and a course.", false); return;
        }
        try {
            if (enrollmentDao.drop(s.getId(), c.getId())) {
                showMsg(msgLabel, s.getName() + " dropped from " + c.getTitle(), true);
            } else {
                showMsg(msgLabel, "Enrollment not found.", false);
            }
        } catch (SQLException e) {
            showMsg(msgLabel, "Error: " + e.getMessage(), false);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void loadStudents() { loadStudentsInto(studentCombo); }
    private void loadCourses()  { loadCoursesInto(courseCombo); }

    private void loadStudentsInto(ComboBox<Student> combo) {
        try {
            combo.getItems().setAll(studentDao.getAllStudents());
            combo.setConverter(new javafx.util.StringConverter<>() {
                public String toString(Student s)   { return s == null ? "" : s.getName(); }
                public Student fromString(String s) { return null; }
            });
        } catch (SQLException ignored) {}
    }

    private void loadCoursesInto(ComboBox<Course> combo) {
        try {
            combo.getItems().setAll(courseDao.getAllCourses());
            combo.setConverter(new javafx.util.StringConverter<>() {
                public String toString(Course c)   { return c == null ? "" : c.getCode() + " - " + c.getTitle(); }
                public Course fromString(String s) { return null; }
            });
        } catch (SQLException ignored) {}
    }

    private void showMsg(Label label, String msg, boolean success) {
        label.setText(msg);
        label.setStyle("-fx-font-size: 11px; -fx-text-fill: " + (success ? "#27ae60;" : "#e74c3c;"));
        label.setVisible(true);
    }
}
