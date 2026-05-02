package com.university.crs.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates all required tables if they do not already exist.
 * Call once at application startup.
 */
public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Students table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS students (
                    id         INT AUTO_INCREMENT PRIMARY KEY,
                    name       VARCHAR(100) NOT NULL,
                    email      VARCHAR(100) NOT NULL UNIQUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Courses table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS courses (
                    id         INT AUTO_INCREMENT PRIMARY KEY,
                    code       VARCHAR(20)  NOT NULL UNIQUE,
                    title      VARCHAR(150) NOT NULL,
                    credits    INT          NOT NULL,
                    capacity   INT          NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Users table (admins + students login)
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id         INT AUTO_INCREMENT PRIMARY KEY,
                    username   VARCHAR(50)  NOT NULL UNIQUE,
                    password   VARCHAR(255) NOT NULL,
                    role       ENUM('ADMIN','STUDENT') NOT NULL DEFAULT 'ADMIN',
                    student_id INT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE SET NULL
                )
            """);

            // Seed default admin
            stmt.executeUpdate("""
                INSERT IGNORE INTO users (username, password, role)
                VALUES ('admin', 'admin123', 'ADMIN')
            """);

            // Enrollments table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS enrollments (
                    id          INT AUTO_INCREMENT PRIMARY KEY,
                    student_id  INT NOT NULL,
                    course_id   INT NOT NULL,
                    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                    FOREIGN KEY (course_id)  REFERENCES courses(id)  ON DELETE CASCADE,
                    UNIQUE KEY uq_enrollment (student_id, course_id)
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema: " + e.getMessage(), e);
        }
    }
}
