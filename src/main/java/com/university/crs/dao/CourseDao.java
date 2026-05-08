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

    public void addCourse(String code, String title, Integer instructorId, int credits, int capacity) throws SQLException {
        String sql = "INSERT INTO courses (code, title, instructor_id, credits, capacity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, title);
            if (instructorId != null) {
                stmt.setInt(3, instructorId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, credits);
            stmt.setInt(5, capacity);
            stmt.executeUpdate();
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = """
            SELECT c.id, c.code, c.title, c.instructor_id, i.name as instructor_name, c.credits, c.capacity 
            FROM courses c
            LEFT JOIN instructors i ON c.instructor_id = i.id
            ORDER BY c.id
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Integer instructorId = rs.getInt("instructor_id");
                if (rs.wasNull()) instructorId = null;
                
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        instructorId,
                        rs.getString("instructor_name"),
                        rs.getInt("credits"),
                        rs.getInt("capacity")));
            }
        }
        return courses;
    }

    public Course getCourseById(int id) throws SQLException {
        String sql = """
            SELECT c.id, c.code, c.title, c.instructor_id, i.name as instructor_name, c.credits, c.capacity 
            FROM courses c
            LEFT JOIN instructors i ON c.instructor_id = i.id
            WHERE c.id = ?
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Integer instructorId = rs.getInt("instructor_id");
                    if (rs.wasNull()) instructorId = null;
                    
                    return new Course(
                            rs.getInt("id"),
                            rs.getString("code"),
                            rs.getString("title"),
                            instructorId,
                            rs.getString("instructor_name"),
                            rs.getInt("credits"),
                            rs.getInt("capacity"));
                }
            }
        }
        return null;
    }

    public boolean updateCourse(int id, String code, String title, Integer instructorId, int credits, int capacity) throws SQLException {
        String sql = "UPDATE courses SET code = ?, title = ?, instructor_id = ?, credits = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, title);
            if (instructorId != null) {
                stmt.setInt(3, instructorId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, credits);
            stmt.setInt(5, capacity);
            stmt.setInt(6, id);
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
