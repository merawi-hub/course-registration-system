package com.university.crs.model;

/**
 * Represents an instructor/teacher in the system.
 * Managed by admin, assigned to courses.
 */
public class Instructor {
    private int id;
    private String name;
    private String email;
    private String department;

    public Instructor(int id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public int getId()              { return id; }
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public String getDepartment()   { return department; }

    @Override
    public String toString() {
        return String.format("  [%d] %-30s %-30s Department: %s",
                id, name, email, (department != null ? department : "N/A"));
    }
}
