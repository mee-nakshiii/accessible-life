package com.accessiblelife;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLTest {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/accessiblelifeapi?allowPublicKeyRetrieval=true&useSSL=false",
                "root",
                "Elaine#1976");
        System.out.println("Connected!");
        conn.close();
    }
}
