package com.university.crs.gui;

import com.university.crs.dao.CourseDao;
import com.university.crs.model.Course;
import com.university.crs.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Courses management page — clean table with Add / Edit / Delete.
 */
public class CoursesPage {

    private final Stage stage;
    private final User  user;
    private final CourseDao courseDao = new CourseDao();

    public CoursesPage(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public Node build() {
        VBox page = new VBox(30);
        page.setPadding(new Insets(40, 50, 40, 50));
        page.setStyle("-fx-background-color: " + ColorScheme.BACKGROUND_HEX + ";");

        // Header with title and Add Course button
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label heading = new Label("Manage Courses");
        heading.setFont(FontLoader.getPoppinsBold(28));
        heading.setTextFill(ColorScheme.DARK_TEXT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button addCourseBtn = new Button("+ Add Course");
        addCourseBtn.setFont(FontLoader.getPoppinsBold(14));
        addCourseBtn.setTextFill(ColorScheme.WHITE);
        addCourseBtn.setPrefHeight(45);
        addCourseBtn.setPrefWidth(150);
        addCourseBtn.setStyle(ColorScheme.getPrimaryButtonStyle());
        addCourseBtn.setOnMouseEntered(e -> addCourseBtn.setStyle(ColorScheme.getPrimaryButtonHoverStyle()));
        addCourseBtn.setOnMouseExited(e -> addCourseBtn.setStyle(ColorScheme.getPrimaryButtonStyle()));
        addCourseBtn.setOnAction(e -> showAddCourseDialog());
        
        header.getChildren().addAll(heading, spacer, addCourseBtn);

        // Table container
        VBox tableContainer = buildTableContainer();

        page.getChildren().addAll(header, tableContainer);
        
        ScrollPane scrollPane = new ScrollPane(page);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + ColorScheme.BACKGROUND_HEX + "; -fx-background-color: " + ColorScheme.BACKGROUND_HEX + ";");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        return scrollPane;
    }

    private VBox buildTableContainer() {
        VBox container = new VBox();
        container.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 3);"
        );
        container.setPadding(new Insets(25));

        // Table header
        HBox headerRow = new HBox();
        headerRow.setSpacing(20);
        headerRow.setPadding(new Insets(0, 0, 15, 0));
        headerRow.setStyle("-fx-border-color: " + ColorScheme.SOFT_GRAY_HEX + "; -fx-border-width: 0 0 1 0;");
        
        Label col1 = createHeaderLabel("Course Code", 150);
        Label col2 = createHeaderLabel("Course Name", 350);
        Label col3 = createHeaderLabel("Instructor", 200);
        Label col4 = createHeaderLabel("Seats", 100);
        Label col5 = createHeaderLabel("Actions", 120);
        
        headerRow.getChildren().addAll(col1, col2, col3, col4, col5);

        // Table rows
        VBox rows = new VBox(0);
        refreshTableRows(rows);

        container.getChildren().addAll(headerRow, rows);
        return container;
    }

    private Label createHeaderLabel(String text, double width) {
        Label label = new Label(text);
        label.setFont(FontLoader.getInter(13));
        label.setTextFill(Color.rgb(100, 100, 100));
        label.setStyle("-fx-font-weight: 600;");
        label.setPrefWidth(width);
        return label;
    }

    private void refreshTableRows(VBox rows) {
        rows.getChildren().clear();
        try {
            List<Course> courses = courseDao.getAllCourses();
            for (Course course : courses) {
                rows.getChildren().add(createTableRow(course, rows));
            }
        } catch (SQLException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
    }

    private HBox createTableRow(Course course, VBox parentRows) {
        HBox row = new HBox();
        row.setSpacing(20);
        row.setPadding(new Insets(15, 0, 15, 0));
        row.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");
        
        Label col1 = createCellLabel(course.getCode(), 150);
        Label col2 = createCellLabel(course.getTitle(), 350);
        Label col3 = createCellLabel("Dr. Smith", 200); // Placeholder instructor
        Label col4 = createCellLabel(String.valueOf(course.getCapacity()), 100);
        
        // Actions (Edit and Delete icons)
        HBox actions = new HBox(15);
        actions.setPrefWidth(120);
        actions.setAlignment(Pos.CENTER_LEFT);
        
        // Edit button (pencil icon)
        Button editBtn = new Button("✏️");
        editBtn.setFont(FontLoader.getInter(18));
        editBtn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5;"
        );
        editBtn.setOnMouseEntered(e -> editBtn.setStyle(
            "-fx-background-color: rgba(74, 144, 226, 0.1); " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5; " +
            "-fx-background-radius: 5;"
        ));
        editBtn.setOnMouseExited(e -> editBtn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5;"
        ));
        editBtn.setOnAction(e -> showEditCourseDialog(course, parentRows));
        
        // Delete button (trash icon)
        Button deleteBtn = new Button("🗑️");
        deleteBtn.setFont(FontLoader.getInter(18));
        deleteBtn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5;"
        );
        deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle(
            "-fx-background-color: rgba(234, 84, 85, 0.1); " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5; " +
            "-fx-background-radius: 5;"
        ));
        deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 5;"
        ));
        deleteBtn.setOnAction(e -> deleteCourse(course, parentRows));
        
        actions.getChildren().addAll(editBtn, deleteBtn);
        
        row.getChildren().addAll(col1, col2, col3, col4, actions);
        
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

    private void showAddCourseDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Course");
        dialog.setHeaderText("Enter course details");

        // Create form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField codeField = new TextField();
        codeField.setPromptText("e.g., CS101");
        TextField titleField = new TextField();
        titleField.setPromptText("e.g., Introduction to CS");
        TextField creditsField = new TextField();
        creditsField.setPromptText("e.g., 3");
        TextField capacityField = new TextField();
        capacityField.setPromptText("e.g., 30");

        grid.add(new Label("Course Code:"), 0, 0);
        grid.add(codeField, 1, 0);
        grid.add(new Label("Course Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Credits:"), 0, 2);
        grid.add(creditsField, 1, 2);
        grid.add(new Label("Capacity:"), 0, 3);
        grid.add(capacityField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    String code = codeField.getText().trim().toUpperCase();
                    String title = titleField.getText().trim();
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    int capacity = Integer.parseInt(capacityField.getText().trim());

                    if (code.isEmpty() || title.isEmpty()) {
                        showAlert("Error", "Course code and title are required.");
                        return;
                    }

                    courseDao.addCourse(code, title, credits, capacity);
                    refreshPage();
                } catch (NumberFormatException e) {
                    showAlert("Error", "Credits and capacity must be valid numbers.");
                } catch (SQLException e) {
                    showAlert("Error", "Failed to add course: " + e.getMessage());
                }
            }
        });
    }

    private void showEditCourseDialog(Course course, VBox parentRows) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Course");
        dialog.setHeaderText("Update course details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField codeField = new TextField(course.getCode());
        TextField titleField = new TextField(course.getTitle());
        TextField creditsField = new TextField(String.valueOf(course.getCredits()));
        TextField capacityField = new TextField(String.valueOf(course.getCapacity()));

        grid.add(new Label("Course Code:"), 0, 0);
        grid.add(codeField, 1, 0);
        grid.add(new Label("Course Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Credits:"), 0, 2);
        grid.add(creditsField, 1, 2);
        grid.add(new Label("Capacity:"), 0, 3);
        grid.add(capacityField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    String code = codeField.getText().trim().toUpperCase();
                    String title = titleField.getText().trim();
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    int capacity = Integer.parseInt(capacityField.getText().trim());

                    if (code.isEmpty() || title.isEmpty()) {
                        showAlert("Error", "Course code and title are required.");
                        return;
                    }

                    courseDao.updateCourse(course.getId(), code, title, credits, capacity);
                    refreshTableRows(parentRows);
                } catch (NumberFormatException e) {
                    showAlert("Error", "Credits and capacity must be valid numbers.");
                } catch (SQLException e) {
                    showAlert("Error", "Failed to update course: " + e.getMessage());
                }
            }
        });
    }

    private void deleteCourse(Course course, VBox parentRows) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Course");
        confirm.setHeaderText("Are you sure you want to delete this course?");
        confirm.setContentText("Course: " + course.getCode() + " - " + course.getTitle());

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    courseDao.deleteCourse(course.getId());
                    refreshTableRows(parentRows);
                } catch (SQLException e) {
                    showAlert("Error", "Failed to delete course: " + e.getMessage());
                }
            }
        });
    }

    private void refreshPage() {
        // Rebuild the entire page
        stage.getScene().setRoot(new StackPane(build()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
