package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // <--- CRITICAL FIX: Missing import statement

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
        }
    }

    // Admin Functionality: Get all pending reports
    public ResultSet getAllPendingReports() throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) throw new SQLException("Database connection unavailable.");

        // FIX: The Statement class is now resolved by the new import
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery("SELECT report_id, place_id, user_id, reason, created_at FROM reports WHERE status = 'Pending'");
    }

    // Admin Functionality: Update report status
    public boolean updateStatus(long reportId, String newStatus) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return false;

        String sql = "UPDATE reports SET status = ? WHERE report_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setLong(2, reportId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while updating report status:");
            e.printStackTrace();
            return false;
        }
    }
}