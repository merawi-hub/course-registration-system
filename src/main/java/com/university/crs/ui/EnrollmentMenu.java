package com.university.crs.ui;

import com.university.crs.dao.CourseDao;
import com.university.crs.dao.EnrollmentDao;
import com.university.crs.dao.StudentDao;
import com.university.crs.model.Course;
import com.university.crs.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Console menu for enrollment management.
 */
public class EnrollmentMenu {

    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final StudentDao    studentDao    = new StudentDao();
    private final CourseDao     courseDao     = new CourseDao();
    private final Scanner scanner;

    public EnrollmentMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     ENROLLMENT MANAGEMENT    ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Enroll Student in Course ║");
            System.out.println("║  2. Drop Student from Course ║");
            System.out.println("║  3. View Courses by Student  ║");
            System.out.println("║  4. View Students by Course  ║");
            System.out.println("║  0. Back to Dashboard        ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("  Select: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> enrollStudent();
                case "2" -> dropStudent();
                case "3" -> viewCoursesByStudent();
                case "4" -> viewStudentsByCourse();
                case "0" -> back = true;
                default  -> System.out.println("  [!] Invalid option.");
            }
        }
    }

    private void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        try {
            System.out.print("  Student ID : ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            Student student = studentDao.getStudentById(studentId);
            if (student == null) { System.out.println("  [!] Student not found."); return; }

            System.out.print("  Course ID  : ");
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Course course = courseDao.getCourseById(courseId);
            if (course == null) { System.out.println("  [!] Course not found."); return; }

            enrollmentDao.enroll(studentId, courseId);
            System.out.println("  [✓] " + student.getName() + " enrolled in " + course.getTitle());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate") || e.getMessage().contains("uq_enrollment")) {
                System.out.println("  [!] Student is already enrolled in that course.");
            } else {
                System.out.println("  [!] " + e.getMessage());
            }
        }
    }

    private void dropStudent() {
        System.out.println("\n--- Drop Student from Course ---");
        try {
            System.out.print("  Student ID : ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            Student student = studentDao.getStudentById(studentId);
            if (student == null) { System.out.println("  [!] Student not found."); return; }

            System.out.print("  Course ID  : ");
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Course course = courseDao.getCourseById(courseId);
            if (course == null) { System.out.println("  [!] Course not found."); return; }

            if (enrollmentDao.drop(studentId, courseId)) {
                System.out.println("  [✓] " + student.getName() + " dropped from " + course.getTitle());
            } else {
                System.out.println("  [!] Enrollment not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }

    private void viewCoursesByStudent() {
        System.out.println("\n--- Courses by Student ---");
        System.out.print("  Student ID: ");
        try {
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            Student student = studentDao.getStudentById(studentId);
            if (student == null) { System.out.println("  [!] Student not found."); return; }

            List<String> courses = enrollmentDao.getCoursesByStudent(studentId);
            System.out.println("  Enrolled courses for: " + student.getName());
            if (courses.isEmpty()) {
                System.out.println("  No enrollments found.");
            } else {
                courses.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }

    private void viewStudentsByCourse() {
        System.out.println("\n--- Students by Course ---");
        System.out.print("  Course ID: ");
        try {
            int courseId = Integer.parseInt(scanner.nextLine().trim());
            Course course = courseDao.getCourseById(courseId);
            if (course == null) { System.out.println("  [!] Course not found."); return; }

            List<String> students = enrollmentDao.getStudentsByCourse(courseId);
            System.out.println("  Students enrolled in: " + course.getTitle());
            if (students.isEmpty()) {
                System.out.println("  No students enrolled.");
            } else {
                students.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid ID.");
        } catch (SQLException e) {
            System.err.println("  [!] Error: " + e.getMessage());
        }
    }
}
