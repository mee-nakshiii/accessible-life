package com.accessiblelife.dao;

import com.accessiblelife.db.DBConnectionHelper;
import com.accessiblelife.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    // --- REGISTER (Insert a new user) ---
    public boolean register(User user) {
        String sql = "INSERT INTO users (name, email, password_hash, location) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); // NOTE: Should be a secure hash!
            ps.setString(4, user.getLocation());

            int rowsAffected = ps.executeUpdate();

            // Set the generated ID back onto the com.accessiblelife.model.User object (using String setter)
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserID(String.valueOf(rs.getInt(1)));
                    }
                }
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("UserDAO - Registration failed: " + e.getMessage());
            return false;
        }
    }

    // --- LOGIN (Verify credentials and retrieve user data) ---
    public User login(String email, String password) {
        String sql = "SELECT user_id, name, location, password_hash FROM users WHERE email = ?";
        User user = null;

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPasswordHash = rs.getString("password_hash");

                    // Check if input password matches stored hash (simple comparison here)
                    if (storedPasswordHash.equals(password)) {
                        user = new User();
                        user.setUserID(String.valueOf(rs.getInt("user_id")));
                        user.setName(rs.getString("name"));
                        user.setEmail(email);
                        user.setLocation(rs.getString("location"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("UserDAO - Login error: " + e.getMessage());
        }
        return user;
    }
}