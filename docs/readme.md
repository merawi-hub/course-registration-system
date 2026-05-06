Software Requirements Specification (SRS)
Course Registration System (Java-Based)
1. Introduction
1.1 Purpose

The purpose of this document is to define the requirements for a Course Registration System developed using Java. The system will allow students to register for courses, view available courses, and manage their schedules. Administrators will manage courses, students, and registrations.

1.2 Scope

The system will:

Allow students to register and log in
Enable students to enroll in courses
Allow viewing available courses
Allow dropping courses
Provide admin control to manage courses and students

This system is intended for universities or colleges to automate course registration.

1.3 Definitions
Student: A user who enrolls in courses
Admin: A user who manages the system
Course: A subject offered by the institution
Registration: The process of enrolling in a course
1.4 Overview

This document describes functional and non-functional requirements, system design overview, and constraints of the system.

2. Overall Description
2.1 Product Perspective

The system is a standalone Java desktop or console application. It may use:

Java (Core Java / JavaFX)
File system or database (MySQL recommended)
2.2 Product Functions

The system will provide:

User authentication (Login/Register)
Course management
Student course enrollment
Course drop functionality
Admin dashboard
Viewing registered courses
2.3 User Classes
1. Student
Can register/login
Can view courses
Can enroll/drop courses
2. Admin
Can add/update/delete courses
Can view all students
Can manage registrations
2.4 Operating Environment
Windows / Linux / macOS
Java JDK 8+
IDE: VS Code / IntelliJ / Eclipse
Optional: MySQL database
2.5 Constraints
Must be implemented in Java
System should handle multiple users
Secure login required
Course capacity must be respected
2.6 Assumptions
Users have basic computer knowledge
Internet not required if local system is used
Database is properly configured
3. Functional Requirements
3.1 User Authentication
FR1: System shall allow users to register
FR2: System shall allow login with username and password
FR3: System shall validate credentials
3.2 Student Functions
FR4: View available courses
FR5: Enroll in a course
FR6: Drop a course
FR7: View enrolled courses
3.3 Admin Functions
FR8: Add new courses
FR9: Update course details
FR10: Delete courses
FR11: View all registered students
FR12: Manage course capacity
3.4 Course Management
FR13: Each course shall have:
Course ID
Course Name
Instructor
Capacity
3.5 Registration Rules
FR14: Student cannot register for full courses
FR15: Student cannot register twice for same course
4. Non-Functional Requirements
4.1 Performance
System should respond within 2 seconds
Should support multiple users
4.2 Security
Passwords must be protected
Unauthorized access should be prevented
4.3 Usability
Simple and user-friendly interface
Easy navigation for students and admin
4.4 Reliability
System should not crash during normal use
Data should be stored safely
4.5 Maintainability
Code should be modular (OOP principles)
Easy to update features
5. System Features
5.1 Login System
Username/password authentication
Role-based access (Student/Admin)
5.2 Course Module
Add, delete, update courses
View course list
5.3 Registration Module
Enroll in courses
Drop courses
Check availability
6. External Interface Requirements
6.1 User Interface
Console-based or GUI (Java Swing/JavaFX)
Menus for navigation:
Login
Student dashboard
Admin dashboard
6.2 Hardware Interface
Standard computer system
No special hardware required
6.3 Software Interface
Java Runtime Environment
Optional MySQL database
7. Database Design (Optional)
Tables:
Users Table
user_id
username
password
role
Courses Table
course_id
course_name
instructor
capacity
Registrations Table
reg_id
user_id
course_id
8. Use Case Diagram (Text Version)
Student:
Login
View Courses
Register Course
Drop Course
Admin:
Login
Add Course
Edit Course
Delete Course
View Students
9. Future Enhancements
Online payment integration
Email notifications
Mobile application version
AI-based course recommendation
