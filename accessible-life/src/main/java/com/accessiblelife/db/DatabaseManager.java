package com.accessiblelife.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/accessiblelifeapi";
    private static final String USER = "root";
    private static final String PASSWORD = "@123MySQL123"; // match your MySQL root password

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to MySQL:");
            e.printStackTrace();
            return null;
        }
    }
}