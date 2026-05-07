package com.university.crs.gui;

import com.university.crs.dao.StudentDao;
import com.university.crs.model.Student;
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
import java.util.stream.Collectors;

/**
 * Students management page — clean table with search and inline actions.
 */
public class StudentsPage {

    private final Stage stage;
    private final User  user;
    private final StudentDao studentDao = new StudentDao();
    private List<Student> allStudents;
    private VBox tableRowsContainer;

    public StudentsPage(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public Node build() {
        VBox page = new VBox(30);
        page.setPadding(new Insets(40, 50, 40, 50));
        page.setStyle("-fx-background-color: " + ColorScheme.BACKGROUND_HEX + ";");

        // Header with title
        Label heading = new Label("Manage Students");
        heading.setFont(FontLoader.getPoppinsBold(28));
        heading.setTextFill(ColorScheme.DARK_TEXT);

        // Search bar
        HBox searchBox = buildSearchBar();

        // Table container
        VBox tableContainer = buildTableContainer();

        page.getChildren().addAll(heading, searchBox, tableContainer);
        
        ScrollPane scrollPane = new ScrollPane(page);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + ColorScheme.BACKGROUND_HEX + "; -fx-background-color: " + ColorScheme.BACKGROUND_HEX + ";");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        return scrollPane;
    }

    private HBox buildSearchBar() {
        HBox searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(15, 20, 15, 20));
        searchBox.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 3);"
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search students...");
        searchField.setPrefHeight(40);
        searchField.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-border-color: transparent; " +
            "-fx-font-size: 14px; " +
            "-fx-font-family: " + FontLoader.getInterFontFamily() + "; " +
            "-fx-prompt-text-fill: #999999;"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Search icon
        Label searchIcon = new Label("🔍");
        searchIcon.setFont(FontLoader.getInter(18));
        searchIcon.setStyle("-fx-text-fill: #999999;");

        searchBox.getChildren().addAll(searchField, searchIcon);

        // Search functionality
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterStudents(newVal);
        });

        return searchBox;
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
        
        Label col1 = createHeaderLabel("Student ID", 120);
        Label col2 = createHeaderLabel("Name", 250);
        Label col3 = createHeaderLabel("Email", 300);
        Label col4 = createHeaderLabel("Department", 150);
        Label col5 = createHeaderLabel("Actions", 120);
        
        headerRow.getChildren().addAll(col1, col2, col3, col4, col5);

        // Table rows container
        tableRowsContainer = new VBox(0);
        refreshTableRows();

        container.getChildren().addAll(headerRow, tableRowsContainer);
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

    private void refreshTableRows() {
        tableRowsContainer.getChildren().clear();
        try {
            allStudents = studentDao.getAllStudents();
            for (Student student : allStudents) {
                tableRowsContainer.getChildren().add(createTableRow(student));
            }
        } catch (SQLException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
    }

    private void filterStudents(String searchText) {
        tableRowsContainer.getChildren().clear();
        if (allStudents == null) return;

        List<Student> filtered = allStudents.stream()
            .filter(s -> searchText == null || searchText.isEmpty() ||
                s.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                s.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                String.valueOf(s.getId()).contains(searchText))
            .collect(Collectors.toList());

        for (Student student : filtered) {
            tableRowsContainer.getChildren().add(createTableRow(student));
        }
    }

    private HBox createTableRow(Student student) {
        HBox row = new HBox();
        row.setSpacing(20);
        row.setPadding(new Insets(15, 0, 15, 0));
        row.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");
        
        Label col1 = createCellLabel("S" + String.format("%03d", student.getId()), 120);
        Label col2 = createCellLabel(student.getName(), 250);
        Label col3 = createCellLabel(student.getEmail(), 300);
        Label col4 = createCellLabel(getDepartment(student), 150);
        
        // Actions (Edit and Delete icons)
        HBox actions = new HBox(15);
        actions.setPrefWidth(120);
        actions.setAlignment(Pos.CENTER_LEFT);
        
        // Edit button
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
        editBtn.setOnAction(e -> showEditStudentDialog(student));
        
        // Delete button
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
        deleteBtn.setOnAction(e -> deleteStudent(student));
        
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

    private String getDepartment(Student student) {
        // Placeholder - you can add department field to Student model
        String[] departments = {"CS", "IT", "ECE", "ME", "EE"};
        return departments[student.getId() % departments.length];
    }

    private void showEditStudentDialog(Student student) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");
        dialog.setHeaderText("Update student details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField(student.getName());
        TextField emailField = new TextField(student.getEmail());

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();

                    if (name.isEmpty() || email.isEmpty()) {
                        showAlert("Error", "Name and email are required.");
                        return;
                    }

                    studentDao.updateStudent(student.getId(), name, email);
                    refreshTableRows();
                } catch (SQLException e) {
                    showAlert("Error", "Failed to update student: " + e.getMessage());
                }
            }
        });
    }

    private void deleteStudent(Student student) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Student");
        confirm.setHeaderText("Are you sure you want to delete this student?");
        confirm.setContentText("Student: " + student.getName() + " (" + student.getEmail() + ")");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    studentDao.deleteStudent(student.getId());
                    refreshTableRows();
                } catch (SQLException e) {
                    showAlert("Error", "Failed to delete student: " + e.getMessage());
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
