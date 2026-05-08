package com.university.crs.model;

/**
 * Represents a course offered by the university.
 */
public class Course {
    private int id;
    private String code;
    private String title;
    private String instructor;
    private int credits;
    private int capacity;

    public Course(int id, String code, String title, String instructor, int credits, int capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.credits = credits;
        this.capacity = capacity;
    }

    public int getId()              { return id; }
    public String getCode()         { return code; }
    public String getTitle()        { return title; }
    public String getInstructor()   { return instructor; }
    public int getCredits()         { return credits; }
    public int getCapacity()        { return capacity; }

    @Override
    public String toString() {
        return String.format("  [%d] %-10s %-30s Instructor: %-20s Credits: %d  Capacity: %d",
                id, code, title, (instructor != null ? instructor : "TBA"), credits, capacity);
    }
}
