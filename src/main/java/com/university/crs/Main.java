package com.university.crs;

import com.university.crs.db.DatabaseInitializer;
import com.university.crs.ui.AdminDashboard;

/**
 * Entry point for the Course Registration System.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize DB schema on startup
        DatabaseInitializer.initialize();

        // Launch admin dashboard
        new AdminDashboard().start();
    }
}
