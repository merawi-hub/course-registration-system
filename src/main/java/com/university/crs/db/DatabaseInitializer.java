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
            
            // Instructors table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS instructors (
                    id         INT AUTO_INCREMENT PRIMARY KEY,
                    name       VARCHAR(100) NOT NULL,
                    email      VARCHAR(100) NOT NULL UNIQUE,
                    department VARCHAR(100) NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Courses table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS courses (
                    id            INT AUTO_INCREMENT PRIMARY KEY,
                    code          VARCHAR(20)  NOT NULL UNIQUE,
                    title         VARCHAR(150) NOT NULL,
                    instructor_id INT NULL,
                    credits       INT          NOT NULL,
                    capacity      INT          NOT NULL,
                    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE SET NULL
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
                    approved   BOOLEAN NOT NULL DEFAULT FALSE,
                    full_name  VARCHAR(100) NULL,
                    email      VARCHAR(100) NULL,
                    department VARCHAR(100) NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE SET NULL
                )
            """);

            // Seed default admin (admins are auto-approved)
            stmt.executeUpdate("""
                INSERT IGNORE INTO users (username, password, role, approved)
                VALUES ('admin', 'admin123', 'ADMIN', TRUE)
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
            
            // Migration: Add instructor column if it doesn't exist (for existing databases)
            try {
                // Drop old instructor text column if it exists
                stmt.executeUpdate("ALTER TABLE courses DROP COLUMN instructor");
                System.out.println("Removed old instructor text column.");
            } catch (SQLException e) {
                // Column doesn't exist, that's fine
            }
            
            // Add instructor_id foreign key if it doesn't exist
            try {
                stmt.executeUpdate("""
                    ALTER TABLE courses 
                    ADD COLUMN instructor_id INT NULL,
                    ADD FOREIGN KEY (instructor_id) REFERENCES instructors(id) ON DELETE SET NULL
                """);
                System.out.println("Added instructor_id foreign key to courses table.");
            } catch (SQLException e) {
                // Column already exists, ignore
                if (!e.getMessage().contains("Duplicate column")) {
                    System.out.println("Instructor_id column already exists or migration not needed.");
                }
            }
            
            // Migration: Add approval fields to users table if they don't exist
            try {
                stmt.executeUpdate("ALTER TABLE users ADD COLUMN approved BOOLEAN NOT NULL DEFAULT FALSE");
                System.out.println("Added approved column to users table.");
            } catch (SQLException e) {
                // Column already exists
            }
            
            try {
                stmt.executeUpdate("ALTER TABLE users ADD COLUMN full_name VARCHAR(100) NULL");
                System.out.println("Added full_name column to users table.");
            } catch (SQLException e) {
                // Column already exists
            }
            
            try {
                stmt.executeUpdate("ALTER TABLE users ADD COLUMN email VARCHAR(100) NULL");
                System.out.println("Added email column to users table.");
            } catch (SQLException e) {
                // Column already exists
            }
            
            try {
                stmt.executeUpdate("ALTER TABLE users ADD COLUMN department VARCHAR(100) NULL");
                System.out.println("Added department column to users table.");
            } catch (SQLException e) {
                // Column already exists
            }
            
            // Ensure existing admin accounts are approved
            try {
                stmt.executeUpdate("UPDATE users SET approved = TRUE WHERE role = 'ADMIN'");
                System.out.println("Ensured all admin accounts are approved.");
            } catch (SQLException e) {
                System.err.println("Error updating admin approval status: " + e.getMessage());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema: " + e.getMessage(), e);
        }
    }
}
