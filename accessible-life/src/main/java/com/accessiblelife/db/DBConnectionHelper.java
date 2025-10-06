package com.accessiblelife.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {

    // Database connection info
    private static final String URL =
            "jdbc:mysql://localhost:3306/accessiblelifeapi?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";           // <-- put your Workbench username
    private static final String PASSWORD = "Elaine#1976"; // <-- put your Workbench password

    // Prevent instantiation
    private DBConnectionHelper() {}

    /**
     * Establishes and returns a new database connection.
     */
    public static Connection getConnection() throws SQLException {
        System.out.println("Attempting to connect to the MySQL database...");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Safely closes the database connection.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

