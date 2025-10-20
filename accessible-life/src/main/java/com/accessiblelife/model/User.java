package com.accessiblelife.model;

public class User {
    private long userId;
    private String name;
    private String email;
    private String passwordHash;
    private boolean isAdmin;


    public long getId() {
        return userId;
    }

    public User(String name, String email, String passwordHash, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public User(long userId, String name, String email, String passwordHash, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isAdmin() { return isAdmin; }
}