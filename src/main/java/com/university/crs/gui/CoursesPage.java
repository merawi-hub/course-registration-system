package com.university.crs.gui;

import com.university.crs.dao.CourseDao;
import com.university.crs.model.Course;
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
 * Courses management page — table with Add / Edit / Delete.
 */
public class CoursesPage {

    private final Stage stage;
    private final User  user;
    private final CourseDao courseDao = new CourseDao();

    private TableView<Course> table;
    private TextField codeField, titleField, creditsField, capacityField;
    private Label     formMessage;
    private Button    saveBtn;
    private Course    editingCourse = null;

    public CoursesPage(Stage stage, User user) {
        this.stage = stage;
        this.user  = user;
    }

    public Node build() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(36));
        page.setStyle("-fx-background-color: #f5f5f5;");

        Label heading = new Label("Manage Courses");
        heading.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        HBox content = new HBox(24);
        VBox.setVgrow(content, Priority.ALWAYS);
        content.getChildren().addAll(buildTable(), buildForm());

        page.getChildren().addAll(heading, content);
        return page;
    }

    private VBox buildTable() {
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(560);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Course, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        idCol.setMaxWidth(50);

        TableColumn<Course, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCode()));
        codeCol.setMaxWidth(90);

        TableColumn<Course, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTitle()));

        TableColumn<Course, String> creditsCol = new TableColumn<>("Credits");
        creditsCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCredits())));
        creditsCol.setMaxWidth(70);

        TableColumn<Course, String> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCapacity())));
        capCol.setMaxWidth(80);

        table.getColumns().addAll(idCol, codeCol, titleCol, creditsCol, capCol);
        refreshTable();

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) populateForm(sel);
        });

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

    private VBox buildForm() {
        VBox form = new VBox(12);
        form.setPrefWidth(280);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

        Label formTitle = new Label("Course Details");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1a1a1a;");

        codeField     = formField("Course Code", "e.g. CS101");
        titleField    = formField("Title",       "Course title");
        creditsField  = formField("Credits",     "e.g. 3");
        capacityField = formField("Capacity",    "e.g. 40");

        formMessage = new Label();
        formMessage.setWrapText(true);
        formMessage.setStyle("-fx-font-size: 11px;");
        formMessage.setVisible(false);

        saveBtn = new Button("Save Course");
        styleActionBtn(saveBtn, "#2c2c2c");
        saveBtn.setOnAction(e -> saveCourse());

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
                new Label("Code") {{ setStyle("-fx-font-size:12px;-fx-text-fill:#555;"); }}, codeField,
                new Label("Title") {{ setStyle("-fx-font-size:12px;-fx-text-fill:#555;"); }}, titleField,
                new Label("Credits") {{ setStyle("-fx-font-size:12px;-fx-text-fill:#555;"); }}, creditsField,
                new Label("Capacity") {{ setStyle("-fx-font-size:12px;-fx-text-fill:#555;"); }}, capacityField,
                formMessage, saveBtn, clearBtn);
        return form;
    }

    private TextField formField(String label, String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        StyleUtil.styleTextField(f);
        return f;
    }

    private void refreshTable() {
        try {
            List<Course> courses = courseDao.getAllCourses();
            table.setItems(FXCollections.observableArrayList(courses));
        } catch (SQLException e) {
            showMsg("Error loading courses: " + e.getMessage(), false);
        }
    }

    private void populateForm(Course c) {
        editingCourse = c;
        codeField.setText(c.getCode());
        titleField.setText(c.getTitle());
        creditsField.setText(String.valueOf(c.getCredits()));
        capacityField.setText(String.valueOf(c.getCapacity()));
        saveBtn.setText("Update Course");
        formMessage.setVisible(false);
    }

    private void clearForm() {
        editingCourse = null;
        codeField.clear(); titleField.clear();
        creditsField.clear(); capacityField.clear();
        saveBtn.setText("Save Course");
        formMessage.setVisible(false);
        table.getSelectionModel().clearSelection();
    }

    private void saveCourse() {
        String code  = codeField.getText().trim().toUpperCase();
        String title = titleField.getText().trim();
        String cStr  = creditsField.getText().trim();
        String capStr = capacityField.getText().trim();

        if (code.isEmpty() || title.isEmpty() || cStr.isEmpty() || capStr.isEmpty()) {
            showMsg("All fields are required.", false); return;
        }
        try {
            int credits  = Integer.parseInt(cStr);
            int capacity = Integer.parseInt(capStr);
            if (editingCourse == null) {
                courseDao.addCourse(code, title, credits, capacity);
                showMsg("Course added successfully.", true);
            } else {
                courseDao.updateCourse(editingCourse.getId(), code, title, credits, capacity);
                showMsg("Course updated successfully.", true);
            }
            clearForm();
            refreshTable();
        } catch (NumberFormatException e) {
            showMsg("Credits and capacity must be numbers.", false);
        } catch (SQLException e) {
            showMsg(e.getMessage().contains("Duplicate")
                    ? "Course code already exists." : "Error: " + e.getMessage(), false);
        }
    }

    private void deleteSelected() {
        Course sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) { showMsg("Select a course to delete.", false); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete course '" + sel.getTitle() + "'?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    courseDao.deleteCourse(sel.getId());
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
