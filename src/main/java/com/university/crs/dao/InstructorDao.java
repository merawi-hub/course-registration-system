package com.university.crs.dao;

import com.university.crs.db.DatabaseConnection;
import com.university.crs.model.Instructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for instructor CRUD operations.
 */
public class InstructorDao {

    public void addInstructor(String name, String email, String department) throws SQLException {
        String sql = "INSERT INTO instructors (name, email, department) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, department);
            stmt.executeUpdate();
        }
    }

    public List<Instructor> getAllInstructors() throws SQLException {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT id, name, email, department FROM instructors ORDER BY name";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                instructors.add(new Instructor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department")));
            }
        }
        return instructors;
    }

    public Instructor getInstructorById(int id) throws SQLException {
        String sql = "SELECT id, name, email, department FROM instructors WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Instructor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("department"));
                }
            }
        }
        return null;
    }

    public boolean updateInstructor(int id, String name, String email, String department) throws SQLException {
        String sql = "UPDATE instructors SET name = ?, email = ?, department = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, department);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteInstructor(int id) throws SQLException {
        String sql = "DELETE FROM instructors WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
