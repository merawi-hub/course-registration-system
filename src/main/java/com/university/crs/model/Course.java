package com.university.crs.model;

/**
 * Represents a course offered by the university.
 */
public class Course {
    private int id;
    private String code;
    private String title;
    private Integer instructorId;  // Foreign key to instructors table
    private String instructorName; // For display purposes (joined from instructors table)
    private int credits;
    private int capacity;

    // Constructor for creating new courses (without instructor name)
    public Course(int id, String code, String title, Integer instructorId, int credits, int capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.instructorId = instructorId;
        this.credits = credits;
        this.capacity = capacity;
    }
    
    // Constructor with instructor name (for display)
    public Course(int id, String code, String title, Integer instructorId, String instructorName, int credits, int capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.credits = credits;
        this.capacity = capacity;
    }

    public int getId()                  { return id; }
    public String getCode()             { return code; }
    public String getTitle()            { return title; }
    public Integer getInstructorId()    { return instructorId; }
    public String getInstructorName()   { return instructorName; }
    public int getCredits()             { return credits; }
    public int getCapacity()            { return capacity; }
    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public String toString() {
        return String.format("  [%d] %-10s %-30s Instructor: %-20s Credits: %d  Capacity: %d",
                id, code, title, (instructorName != null ? instructorName : "TBA"), credits, capacity);
    }
}
