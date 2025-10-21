package com.accessiblelife.service;

import com.accessiblelife.repository.ReportRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportService {
    private final ReportRepository reportRepository = new ReportRepository();

    public boolean submitReport(long placeId, long userId, String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            System.err.println("Report reason cannot be empty.");
            return false;
        }
        return reportRepository.submitReport(placeId, userId, reason);
    }

    public ResultSet getAllPendingReports() throws SQLException {
        return reportRepository.getAllPendingReports();
    }

    public boolean updateStatus(long reportId, String newStatus) {
        return reportRepository.updateStatus(reportId, newStatus);
    }
}