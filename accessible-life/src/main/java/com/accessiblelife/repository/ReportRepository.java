package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// NOTE: You must create a 'reports' table in your database for this to work:
// CREATE TABLE reports (report_id BIGINT AUTO_INCREMENT PRIMARY KEY, place_id BIGINT, user_id BIGINT, reason TEXT, status VARCHAR(50) DEFAULT 'Pending');

public class ReportRepository {

    // NEW FUNCTIONALITY: Submit a report about a place
    public boolean submitReport(long placeId, long userId, String reason) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return false;

        String sql = "INSERT INTO reports (place_id, user_id, reason) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, placeId);
            stmt.setLong(2, userId);
            stmt.setString(3, reason);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while submitting report:");
            e.printStackTrace();
            return false;
        }
    }
}