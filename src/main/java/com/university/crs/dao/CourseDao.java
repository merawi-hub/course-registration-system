package com.university.crs.dao;

import com.university.crs.db.DatabaseConnection;
import com.university.crs.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for course CRUD operations.
 */
public class CourseDao {

    public void addCourse(String code, String title, int credits, int capacity) throws SQLException {
        String sql = "INSERT INTO courses (code, title, credits, capacity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, title);
            stmt.setInt(3, credits);
            stmt.setInt(4, capacity);
            stmt.executeUpdate();
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT id, code, title, credits, capacity FROM courses ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"), rs.getString("code"),
                        rs.getString("title"), rs.getInt("credits"), rs.getInt("capacity")));
            }
        }
        return courses;
    }

    public Course getCourseById(int id) throws SQLException {
        String sql = "SELECT id, code, title, credits, capacity FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(rs.getInt("id"), rs.getString("code"),
                            rs.getString("title"), rs.getInt("credits"), rs.getInt("capacity"));
                }
            }
        }
        return null;
    }

    public boolean updateCourse(int id, String code, String title, int credits, int capacity) throws SQLException {
        String sql = "UPDATE courses SET code = ?, title = ?, credits = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, title);
            stmt.setInt(3, credits);
            stmt.setInt(4, capacity);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCourse(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
