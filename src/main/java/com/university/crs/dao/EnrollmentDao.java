package com.university.crs.dao;

import com.university.crs.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for enrollment operations.
 */
public class EnrollmentDao {

    /**
     * Enrolls a student in a course, respecting capacity limits.
     */
    public void enroll(int studentId, int courseId) throws SQLException {
        // Check current enrollment count vs capacity
        String capacityCheck = """
                SELECT c.capacity,
                       COUNT(e.id) AS enrolled
                FROM courses c
                LEFT JOIN enrollments e ON e.course_id = c.id
                WHERE c.id = ?
                GROUP BY c.id, c.capacity
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement check = conn.prepareStatement(capacityCheck)) {
            check.setInt(1, courseId);
            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    int capacity = rs.getInt("capacity");
                    int enrolled = rs.getInt("enrolled");
                    if (enrolled >= capacity) {
                        throw new SQLException("Course is full (capacity: " + capacity + ").");
                    }
                }
            }
        }

        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        }
    }

    /**
     * Drops a student from a course.
     */
    public boolean drop(int studentId, int courseId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Returns all courses a student is enrolled in.
     */
    public List<String> getCoursesByStudent(int studentId) throws SQLException {
        List<String> result = new ArrayList<>();
        String sql = """
                SELECT c.code, c.title, c.credits
                FROM enrollments e
                JOIN courses c ON c.id = e.course_id
                WHERE e.student_id = ?
                ORDER BY c.code
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(String.format("  %-10s %-30s Credits: %d",
                            rs.getString("code"), rs.getString("title"), rs.getInt("credits")));
                }
            }
        }
        return result;
    }

    /**
     * Returns all students enrolled in a course.
     */
    public List<String> getStudentsByCourse(int courseId) throws SQLException {
        List<String> result = new ArrayList<>();
        String sql = """
                SELECT s.id, s.name, s.email
                FROM enrollments e
                JOIN students s ON s.id = e.student_id
                WHERE e.course_id = ?
                ORDER BY s.name
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(String.format("  [%d] %-25s %s",
                            rs.getInt("id"), rs.getString("name"), rs.getString("email")));
                }
            }
        }
        return result;
    }

    /**
     * Returns a summary: each course with its enrollment count.
     */
    public List<String> getEnrollmentSummary() throws SQLException {
        List<String> result = new ArrayList<>();
        String sql = """
                SELECT c.code, c.title, c.capacity,
                       COUNT(e.id) AS enrolled
                FROM courses c
                LEFT JOIN enrollments e ON e.course_id = c.id
                GROUP BY c.id, c.code, c.title, c.capacity
                ORDER BY c.code
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(String.format("  %-10s %-30s Enrolled: %d / %d",
                        rs.getString("code"), rs.getString("title"),
                        rs.getInt("enrolled"), rs.getInt("capacity")));
            }
        }
        return result;
    }
}
