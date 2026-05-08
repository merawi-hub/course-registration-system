package com.university.crs.ui;

import com.university.crs.dao.CourseDao;
import com.university.crs.model.Course;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Console menu for course management.
 */
public class CourseMenu {

    private final CourseDao courseDao = new CourseDao();
    private final Scanner scanner;

    public CourseMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║       COURSE MANAGEMENT      ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. View All Courses         ║");
            System.out.println("║  2. Add Course               ║");
            System.out.println("║  3. Update Course            ║");
            System.out.println("║  4. Delete Course            ║");
            System.out.println("║  0. Back to Dashboard        ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("  Select: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> viewAll();
                case "2" -> addCourse();
                case "3" -> updateCourse();
                case "4" -> deleteCourse();
                case "0" -> back = true;
                default  -> System.out.println("  [!] Invalid option.");
            }
        }
    }

    private void viewAll() {
        try {
            List<Course> courses = courseDao.getAllCourses();
            System.out.println("\n--- All Courses (" + courses.size() + ") ---");
            if (courses.isEmpty()) {
                System.out.println("  No courses found.");
            } else {
                System.out.printf("  %-5s %-10s %-30s %-10s %-10s%n", "ID", "Code", "Title", "Credits", "Capacity");
                System.out.println("  " + "-".repeat(65));
                courses.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }

    private void addCourse() {
        System.out.println("\n--- Add New Course ---");
        try {
            System.out.print("  Code     : ");
            String code = scanner.nextLine().trim().toUpperCase();
            System.out.print("  Title    : ");
            String title = scanner.nextLine().trim();
            System.out.print("  Instructor (optional): ");
            String instructor = scanner.nextLine().trim();
            if (instructor.isEmpty()) instructor = null;
            System.out.print("  Credits  : ");
            int credits = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("  Capacity : ");
            int capacity = Integer.parseInt(scanner.nextLine().trim());

            if (code.isEmpty() || title.isEmpty()) {
                System.out.println("  [!] Code and title cannot be empty.");
                return;
            }
            courseDao.addCourse(code, title, instructor, credits, capacity);
            System.out.println("  [✓] Course added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("  [!] Credits and capacity must be numbers.");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                System.out.println("  [!] A course with that code already exists.");
            } else {
                System.err.println("  [!] Error: " + e.getMessage());
            }
        }
    }

    private void updateCourse() {
        System.out.println("\n--- Update Course ---");
        System.out.print("  Enter Course ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Course existing = courseDao.getCourseById(id);
            if (existing == null) {
                System.out.println("  [!] Course not found.");
                return;
            }
            System.out.println("  Current: " + existing);
            System.out.print("  New Code     (leave blank to keep): ");
            String code = scanner.nextLine().trim().toUpperCase();
            System.out.print("  New Title    (leave blank to keep): ");
            String title = scanner.nextLine().trim();
            System.out.print("  New Instructor (leave blank to keep): ");
            String instructor = scanner.nextLine().trim();
            System.out.print("  New Credits  (leave blank to keep): ");
            String creditsStr = scanner.nextLine().trim();
            System.out.print("  New Capacity (leave blank to keep): ");
            String capacityStr = scanner.nextLine().trim();

            code     = code.isEmpty()     ? existing.getCode()     : code;
            title    = title.isEmpty()    ? existing.getTitle()    : title;
            instructor = instructor.isEmpty() ? existing.getInstructor() : instructor;
            int credits  = creditsStr.isEmpty()  ? existing.getCredits()  : Integer.parseInt(creditsStr);
            int capacity = capacityStr.isEmpty() ? existing.getCapacity() : Integer.parseInt(capacityStr);

            if (courseDao.updateCourse(id, code, title, instructor, credits, capacity)) {
                System.out.println("  [✓] Course updated successfully.");
            } else {
                System.out.println("  [!] Update failed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid number input.");
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("\n--- Delete Course ---");
        System.out.print("  Enter Course ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Course existing = courseDao.getCourseById(id);
            if (existing == null) {
                System.out.println("  [!] Course not found.");
                return;
            }
            System.out.print("  Confirm delete '" + existing.getTitle() + "'? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                if (courseDao.deleteCourse(id)) {
                    System.out.println("  [✓] Course deleted.");
                } else {
                    System.out.println("  [!] Delete failed.");
                }
            } else {
                System.out.println("  Cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }
}
