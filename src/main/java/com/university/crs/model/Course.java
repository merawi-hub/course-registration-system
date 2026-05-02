package com.university.crs.model;

/**
 * Represents a course offered by the university.
 */
public class Course {
    private int id;
    private String code;
    private String title;
    private int credits;
    private int capacity;

    public Course(int id, String code, String title, int credits, int capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
    }

    public int getId()          { return id; }
    public String getCode()     { return code; }
    public String getTitle()    { return title; }
    public int getCredits()     { return credits; }
    public int getCapacity()    { return capacity; }

    @Override
    public String toString() {
        return String.format("  [%d] %-10s %-30s Credits: %d  Capacity: %d",
                id, code, title, credits, capacity);
    }
}
