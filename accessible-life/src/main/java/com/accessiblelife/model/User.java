package com.accessiblelife.model;

public class User {
    private long id;
    private String name;
    private String email;
    private String passwordHash;
    private boolean isAdmin;

    // Full Constructor
    public User(long id, String name, String email, String passwordHash, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    // Minimal constructor for new registrations (ID is auto-generated)
    public User(String name, String email, String passwordHash, boolean isAdmin) {
        this(0, name, email, passwordHash, isAdmin);
    }

    // Getters
    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isAdmin() { return isAdmin; }
}