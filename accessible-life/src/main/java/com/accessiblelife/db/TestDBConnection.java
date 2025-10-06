package com.accessiblelife.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DBConnectionHelper.getConnection(); // attempt connection
            System.out.println("Connected successfully to the database!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        } finally {
            DBConnectionHelper.closeConnection(conn); // close connection
        }
    }
}

