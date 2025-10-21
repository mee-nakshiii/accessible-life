package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportRepository {

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
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection after report submission: " + e.getMessage());
            }
        }
    }
}