package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;
import com.accessiblelife.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public User findByEmailAndPassword(String email, String password) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("❌ Connection is null. Aborting login.");
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password_hash = ?")) {

            stmt.setString(1, email.trim());
            stmt.setString(2, password.trim());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error during login:");
            e.printStackTrace();
        }

        return null;
    }

    public boolean saveUser(User user) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("❌ Connection is null. Cannot save user.");
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (name, email, password_hash, is_admin) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setBoolean(4, user.isAdmin());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while saving user:");
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return users;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT user_id, name, email, is_admin FROM users")) {

            while (rs.next()) {
                User user = new User(
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        null,
                        rs.getBoolean("is_admin")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error while fetching all users:");
            e.printStackTrace();
        }
        return users;
    }

    // NEW FUNCTIONALITY: Delete User by ID
    public boolean deleteUser(long userId) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return false;

        // NOTE: Must delete dependent records (reviews) first due to FOREIGN KEY constraint
        try (PreparedStatement deleteReviews = conn.prepareStatement("DELETE FROM reviews WHERE user_id = ?");
             PreparedStatement deleteUser = conn.prepareStatement("DELETE FROM users WHERE user_id = ?")) {

            deleteReviews.setLong(1, userId);
            deleteReviews.executeUpdate();

            deleteUser.setLong(1, userId);
            return deleteUser.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while deleting user:");
            e.printStackTrace();
            return false;
        }
    }
}