package com.university.crs.ui;

import com.university.crs.dao.EnrollmentDao;
import com.university.crs.dao.StudentDao;
import com.university.crs.dao.CourseDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Console menu for reports and summaries.
 */
public class ReportsMenu {

    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final StudentDao    studentDao    = new StudentDao();
    private final CourseDao     courseDao     = new CourseDao();
    private final Scanner scanner;

    public ReportsMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║          REPORTS             ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Enrollment Summary       ║");
            System.out.println("║  2. Total Students           ║");
            System.out.println("║  3. Total Courses            ║");
            System.out.println("║  0. Back to Dashboard        ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("  Select: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> enrollmentSummary();
                case "2" -> totalStudents();
                case "3" -> totalCourses();
                case "0" -> back = true;
                default  -> System.out.println("  [!] Invalid option.");
            }
        }
    }

    private void enrollmentSummary() {
        try {
            List<String> summary = enrollmentDao.getEnrollmentSummary();
            System.out.println("\n--- Enrollment Summary ---");
            if (summary.isEmpty()) {
                System.out.println("  No courses found.");
            } else {
                System.out.printf("  %-10s %-30s %s%n", "Code", "Title", "Enrolled / Capacity");
                System.out.println("  " + "-".repeat(60));
                summary.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }

    private void totalStudents() {
        try {
            int count = studentDao.getAllStudents().size();
            System.out.println("\n  Total registered students: " + count);
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }

    private void totalCourses() {
        try {
            int count = courseDao.getAllCourses().size();
            System.out.println("\n  Total available courses: " + count);
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }
}
