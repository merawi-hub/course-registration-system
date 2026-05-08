package com.university.crs.ui;

import com.university.crs.dao.UserDao;
import com.university.crs.model.Admin;
import com.university.crs.model.User;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Admin login and main dashboard menu.
 */
public class AdminDashboard {

    private final UserDao userDao = new UserDao();
    private final Scanner scanner   = new Scanner(System.in);

    public void start() {
        Admin admin = promptLogin();
        if (admin == null) {
            System.out.println("\n  [!] Too many failed attempts. Exiting.");
            return;
        }
        showDashboard(admin);
    }

    // ── Login ────────────────────────────────────────────────────────────────

    private Admin promptLogin() {
        int attempts = 0;
        while (attempts < 3) {
            printLoginBanner();
            System.out.print("  Username : ");
            String username = scanner.nextLine().trim();
            System.out.print("  Password : ");
            String password = scanner.nextLine().trim();

            try {
                User user = userDao.login(username, password);
                if (user != null && user.isAdmin()) {
                    // Create an Admin object from the User
                    Admin admin = new Admin(user.getId(), user.getUsername(), password);
                    System.out.println("\n  [✓] Welcome, " + admin.getUsername() + "!");
                    return admin;
                } else {
                    attempts++;
                    System.out.println("  [!] Invalid credentials. Attempts left: " + (3 - attempts));
                }
            } catch (SQLException e) {
                System.err.println("  [!] Login error: " + e.getMessage());
                attempts++;
            }
        }
        return null;
    }

    // ── Dashboard ────────────────────────────────────────────────────────────

    private void showDashboard(Admin admin) {
        StudentMenu    studentMenu    = new StudentMenu(scanner);
        CourseMenu     courseMenu     = new CourseMenu(scanner);
        EnrollmentMenu enrollmentMenu = new EnrollmentMenu(scanner);
        ReportsMenu    reportsMenu    = new ReportsMenu(scanner);

        boolean running = true;
        while (running) {
            printDashboardMenu(admin);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> studentMenu.show();
                case "2" -> courseMenu.show();
                case "3" -> enrollmentMenu.show();
                case "4" -> reportsMenu.show();
                case "0" -> {
                    System.out.println("\n  Goodbye, " + admin.getUsername() + "!");
                    running = false;
                }
                default -> System.out.println("  [!] Invalid option.");
            }
        }
    }

    // ── UI Helpers ───────────────────────────────────────────────────────────

    private void printLoginBanner() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   COURSE REGISTRATION SYSTEM         ║");
        System.out.println("║          Admin Login                 ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private void printDashboardMenu(Admin admin) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         ADMIN DASHBOARD              ║");
        System.out.println("║  Logged in as: " + padRight(admin.getUsername(), 21) + "║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Manage Students                  ║");
        System.out.println("║  2. Manage Courses                   ║");
        System.out.println("║  3. Manage Enrollments               ║");
        System.out.println("║  4. Reports                          ║");
        System.out.println("║  0. Logout                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("  Select: ");
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
