package com.accessiblelife.service;

import com.accessiblelife.repository.ReportRepository;

public class ReportService {
    private final ReportRepository reportRepository = new ReportRepository();

    public boolean submitReport(long placeId, long userId, String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            System.err.println("Report reason cannot be empty.");
            return false;
        }
        return reportRepository.submitReport(placeId, userId, reason);
    }
}