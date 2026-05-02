package com.university.crs.model;

/**
 * Represents a student in the system.
 */
public class Student {
    private int id;
    private String name;
    private String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId()          { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }

    @Override
    public String toString() {
        return String.format("  [%d] %-25s %s", id, name, email);
    }
}
