package com.accessiblelife.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// NOTE: We do NOT need 'import java.sql.Statement;' because we created tables externally.

public class DBConnectionHelper {

    // 🚨 1. UPDATE THIS PASSWORD to match the one you set for MySQL root user!
    private static final String URL = "jdbc:mysql://localhost:3306/accessiblelifeapi";
    private static final String USER = "root";
    private static final String PASSWORD = "root password of sql"; // <--- 🚨 CRITICAL CHANGE HERE

    // This is a utility class, so we prevent instantiation
    private DBConnectionHelper() {}

    /**
     * Establishes and returns a new database connection.
     * @return a Connection object
     * @throws SQLException if the connection fails (e.g., wrong password, DB not running)
     */
    public static Connection getConnection() throws SQLException {
        // The DriverManager uses the MySQL connector (from pom.xml) to establish the link.
        System.out.println("Attempting to connect to the MySQL database...");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Safely closes the database connection to release resources.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}