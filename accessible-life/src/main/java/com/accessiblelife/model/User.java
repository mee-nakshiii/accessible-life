package com.accessiblelife.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(name = "password_hash")
    private String passwordHash;

    private String location;

    // Default constructor for JPA
    public User() {}

    // Custom constructor for GUI usage
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.passwordHash = password;
    }

    // Jackson mapping so frontend can send "password"
    @JsonProperty("passwordHash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @JsonProperty("passwordHash")
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    // GUI-friendly setter
    public void setPassword(String password) {
        this.passwordHash = password;
    }

    // ---------------- Getters & Setters ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}