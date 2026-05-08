package com.university.crs.util;

import java.util.regex.Pattern;

/**
 * Comprehensive validation utility for all input fields.
 * Ensures data integrity and security across the application.
 */
public class ValidationUtil {
    
    // ═══════════════════════════════════════════════════════════════════
    // REGEX PATTERNS
    // ═══════════════════════════════════════════════════════════════════
    
    /** Course code: Must start with 2-4 letters, followed by 3-4 digits (e.g., CS101, MATH1234) */
    private static final Pattern COURSE_CODE_PATTERN = Pattern.compile("^[A-Z]{2,4}\\d{3,4}$");
    
    /** Email: Standard email format */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    /** Username: 3-20 characters, alphanumeric and underscore only */
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,20}$");
    
    /** Name: Letters, spaces, hyphens, apostrophes only (2-50 chars) */
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s'-]{2,50}$");
    
    /** Password: At least 6 characters (can be strengthened) */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,}$");
    
    /** Strong Password: At least 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char */
    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$"
    );
    
    // ═══════════════════════════════════════════════════════════════════
    // COURSE VALIDATION
    // ═══════════════════════════════════════════════════════════════════
    
    /**
     * Validate course code format.
     * Must start with 2-4 letters followed by 3-4 digits.
     * Examples: CS101, MATH1234, ENG201
     */
    public static ValidationResult validateCourseCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return ValidationResult.error("Course code is required");
        }
        
        String trimmed = code.trim().toUpperCase();
        
        if (!COURSE_CODE_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error(
                "Course code must start with 2-4 letters followed by 3-4 digits (e.g., CS101, MATH1234)"
            );
        }
        
        return ValidationResult.success(trimmed);
    }
    
    /**
     * Validate course title.
     */
    public static ValidationResult validateCourseTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return ValidationResult.error("Course title is required");
        }
        
        String trimmed = title.trim();
        
        if (trimmed.length() < 3) {
            return ValidationResult.error("Course title must be at least 3 characters");
        }
        
        if (trimmed.length() > 100) {
            return ValidationResult.error("Course title must not exceed 100 characters");
        }
        
        return ValidationResult.success(trimmed);
    }
    
    /**
     * Validate instructor name.
     */
    public static ValidationResult validateInstructor(String instructor) {
        // Instructor is optional
        if (instructor == null || instructor.trim().isEmpty()) {
            return ValidationResult.success(null);
        }
        
        String trimmed = instructor.trim();
        
        if (trimmed.length() < 2) {
            return ValidationResult.error("Instructor name must be at least 2 characters");
        }
        
        if (trimmed.length() > 100) {
            return ValidationResult.error("Instructor name must not exceed 100 characters");
        }
        
        // Allow letters, spaces, periods, hyphens, apostrophes (for names like "Dr. O'Brien")
        if (!Pattern.matches("^[A-Za-z\\s.'-]+$", trimmed)) {
            return ValidationResult.error("Instructor name can only contain letters, spaces, periods, hyphens, and apostrophes");
        }
        
        return ValidationResult.success(trimmed);
    }
    
    /**
     * Validate course credits.
     */
    public static ValidationResult validateCredits(String creditsStr) {
        if (creditsStr == null || creditsStr.trim().isEmpty()) {
            return ValidationResult.error("Credits are required");
        }
        
        try {
            int credits = Integer.parseInt(creditsStr.trim());
            
            if (credits < 1) {
                return ValidationResult.error("Credits must be at least 1");
            }
            
            if (credits > 12) {
                return ValidationResult.error("Credits cannot exceed 12");
            }
            
            return ValidationResult.success(credits);
        } catch (NumberFormatException e) {
            return ValidationResult.error("Credits must be a valid number");
        }
    }
    
    /**
     * Validate course capacity.
     */
    public static ValidationResult validateCapacity(String capacityStr) {
        if (capacityStr == null || capacityStr.trim().isEmpty()) {
            return ValidationResult.error("Capacity is required");
        }
        
        try {
            int capacity = Integer.parseInt(capacityStr.trim());
            
            if (capacity < 1) {
                return ValidationResult.error("Capacity must be at least 1");
            }
            
            if (capacity > 500) {
                return ValidationResult.error("Capacity cannot exceed 500");
            }
            
            return ValidationResult.success(capacity);
        } catch (NumberFormatException e) {
            return ValidationResult.error("Capacity must be a valid number");
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════
    // STUDENT VALIDATION
    // ═══════════════════════════════════════════════════════════════════
    
    /**
     * Validate student/person name.
     */
    public static ValidationResult validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ValidationResult.error("Name is required");
        }
        
        String trimmed = name.trim();
        
        if (!NAME_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error(
                "Name must be 2-50 characters and contain only letters, spaces, hyphens, or apostrophes"
            );
        }
        
        return ValidationResult.success(trimmed);
    }
    
    /**
     * Validate email address.
     */
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.error("Email is required");
        }
        
        String trimmed = email.trim().toLowerCase();
        
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error("Please enter a valid email address");
        }
        
        return ValidationResult.success(trimmed);
    }
    
    // ═══════════════════════════════════════════════════════════════════
    // USER AUTHENTICATION VALIDATION
    // ═══════════════════════════════════════════════════════════════════
    
    /**
     * Validate username.
     */
    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ValidationResult.error("Username is required");
        }
        
        String trimmed = username.trim();
        
        if (!USERNAME_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error(
                "Username must be 3-20 characters and contain only letters, numbers, or underscores"
            );
        }
        
        return ValidationResult.success(trimmed);
    }
    
    /**
     * Validate password (basic).
     */
    public static ValidationResult validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return ValidationResult.error("Password is required");
        }
        
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return ValidationResult.error("Password must be at least 6 characters");
        }
        
        return ValidationResult.success(password);
    }
    
    /**
     * Validate strong password.
     */
    public static ValidationResult validateStrongPassword(String password) {
        if (password == null || password.isEmpty()) {
            return ValidationResult.error("Password is required");
        }
        
        if (!STRONG_PASSWORD_PATTERN.matcher(password).matches()) {
            return ValidationResult.error(
                "Password must be at least 8 characters with uppercase, lowercase, digit, and special character"
            );
        }
        
        return ValidationResult.success(password);
    }
    
    /**
     * Validate password confirmation.
     */
    public static ValidationResult validatePasswordMatch(String password, String confirmPassword) {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return ValidationResult.error("Please confirm your password");
        }
        
        if (!password.equals(confirmPassword)) {
            return ValidationResult.error("Passwords do not match");
        }
        
        return ValidationResult.success(password);
    }
    
    // ═══════════════════════════════════════════════════════════════════
    // VALIDATION RESULT CLASS
    // ═══════════════════════════════════════════════════════════════════
    
    /**
     * Result of a validation operation.
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;
        private final Object value;
        
        private ValidationResult(boolean valid, String errorMessage, Object value) {
            this.valid = valid;
            this.errorMessage = errorMessage;
            this.value = value;
        }
        
        public static ValidationResult success(Object value) {
            return new ValidationResult(true, null, value);
        }
        
        public static ValidationResult error(String message) {
            return new ValidationResult(false, message, null);
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }
        
        public Object getValue() {
            return value;
        }
        
        public String getStringValue() {
            return value != null ? value.toString() : null;
        }
        
        public Integer getIntValue() {
            return value instanceof Integer ? (Integer) value : null;
        }
    }
}
