package com.university.crs.gui;

import com.university.crs.dao.StudentDao;
import com.university.crs.model.Student;
import com.university.crs.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * Students management page — table with Add / Edit / Delete.
 */
public class StudentsPage {

    private final Stage stage;
    private final User  user;
    private final StudentDao studentDao = new StudentDao();

    private TableView<Student> table;
    private ObservableList<Student> data;

    // Form fields (reused for add & edit)
    private TextField nameField, emailField;
    private Label     formMessage;
    private Button    saveBtn;
    private Student   editingStudent = null;

    public StudentsPage(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public Node build() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(36));
        page.setStyle("-fx-background-color: #f5f5f5;");

        Label heading = new Label("Manage Students");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        HBox content = new HBox(24);
        VBox.setVgrow(content, Priority.ALWAYS);
        content.getChildren().addAll(buildTable(), buildForm());

        page.getChildren().addAll(heading, content);
        return page;
    }

    // ── Table ────────────────────────────────────────────────────────────────

    private VBox buildTable() {
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(560);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        idCol.setMaxWidth(60);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));

        table.getColumns().addAll(idCol, nameCol, emailCol);
        refreshTable();

        // Click row → populate form for editing
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) populateForm(selected);
        });

        // Toolbar
        Button addNewBtn = new Button("+ Add New");
        styleActionBtn(addNewBtn, "#2c2c2c");
        addNewBtn.setOnAction(e -> clearForm());

        Button deleteBtn = new Button("Delete");
        styleActionBtn(deleteBtn, "#c0392b");
        deleteBtn.setOnAction(e -> deleteSelected());

        HBox toolbar = new HBox(10, addNewBtn, deleteBtn);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(10, toolbar, table);
        VBox.setVgrow(table, Priority.ALWAYS);
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    // ── Form ─────────────────────────────────────────────────────────────────

    private VBox buildForm() {
        VBox form = new VBox(12);
        form.setPrefWidth(280);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

        Label formTitle = new Label("Student Details");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        Label nameLabel = new Label("Full Name");
        nameLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        nameField = new TextField();
        nameField.setPromptText("Enter full name");
        LoginScreen.styleTextField(nameField);

        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
        emailField = new TextField();
        emailField.setPromptText("Enter email");
        LoginScreen.styleTextField(emailField);

        formMessage = new Label();
        formMessage.setWrapText(true);
        formMessage.setStyle("-fx-font-size: 11px;");
        formMessage.setVisible(false);

        saveBtn = new Button("Save Student");
        styleActionBtn(saveBtn, "#2c2c2c");
        saveBtn.setOnAction(e -> saveStudent());

        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("""
                -fx-background-color: transparent;
                -fx-border-color: #cccccc;
                -fx-border-radius: 6px;
                -fx-background-radius: 6px;
                -fx-font-size: 13px;
                -fx-cursor: hand;
                """);
        clearBtn.setMaxWidth(Double.MAX_VALUE);
        clearBtn.setPrefHeight(38);
        clearBtn.setOnAction(e -> clearForm());

        form.getChildren().addAll(formTitle,
                nameLabel, nameField,
                emailLabel, emailField,
                formMessage, saveBtn, clearBtn);
        return form;
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    private void refreshTable() {
        try {
            List<Student> students = studentDao.getAllStudents();
            data = FXCollections.observableArrayList(students);
            table.setItems(data);
        } catch (SQLException e) {
            showMsg("Error loading students: " + e.getMessage(), false);
        }
    }

    private void populateForm(Student s) {
        editingStudent = s;
        nameField.setText(s.getName());
        emailField.setText(s.getEmail());
        saveBtn.setText("Update Student");
        formMessage.setVisible(false);
    }

    private void clearForm() {
        editingStudent = null;
        nameField.clear();
        emailField.clear();
        saveBtn.setText("Save Student");
        formMessage.setVisible(false);
        table.getSelectionModel().clearSelection();
    }

    private void saveStudent() {
        String name  = nameField.getText().trim();
        String email = emailField.getText().trim();
        if (name.isEmpty() || email.isEmpty()) {
            showMsg("Name and email are required.", false); return;
        }
        try {
            if (editingStudent == null) {
                studentDao.addStudent(name, email);
                showMsg("Student added successfully.", true);
            } else {
                studentDao.updateStudent(editingStudent.getId(), name, email);
                showMsg("Student updated successfully.", true);
            }
            clearForm();
            refreshTable();
        } catch (SQLException e) {
            showMsg(e.getMessage().contains("Duplicate")
                    ? "Email already exists." : "Error: " + e.getMessage(), false);
        }
    }

    private void deleteSelected() {
        Student selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) { showMsg("Select a student to delete.", false); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete student '" + selected.getName() + "'?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    studentDao.deleteStudent(selected.getId());
                    clearForm();
                    refreshTable();
                } catch (SQLException e) {
                    showMsg("Error: " + e.getMessage(), false);
                }
            }
        });
    }

    private void showMsg(String msg, boolean success) {
        formMessage.setText(msg);
        formMessage.setStyle("-fx-font-size: 11px; -fx-text-fill: " + (success ? "#27ae60;" : "#e74c3c;"));
        formMessage.setVisible(true);
    }

    private void styleActionBtn(Button btn, String color) {
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 6px; -fx-cursor: hand;");
        btn.setPrefHeight(38);
        btn.setMaxWidth(Double.MAX_VALUE);
    }
}
