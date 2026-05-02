package com.university.crs.dao;

import com.university.crs.db.DatabaseConnection;
import com.university.crs.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object for admin authentication and registration.
 */
public class AdminDao {

    /**
     * Validates credentials and returns the Admin if found, null otherwise.
     */
    public Admin login(String username, String password) throws SQLException {
        String sql = "SELECT id, username, password FROM admins WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }

    /**
     * Creates a new admin account. Throws SQLException if username already exists.
     */
    public void createAccount(String username, String password) throws SQLException {
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }

    /**
     * Checks if a username already exists.
     */
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT id FROM admins WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
