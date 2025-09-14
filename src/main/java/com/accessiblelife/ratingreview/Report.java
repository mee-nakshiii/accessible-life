package com.accessiblelife.ratingreview;

import java.time.LocalDateTime;

public class Report {

    private String reporterId;
    private String targetId; // Could be a place ID or review ID
    private String reason;
    private LocalDateTime timestamp;

    public Report(String reporterId, String targetId, String reason) {
        this.reporterId = reporterId;
        this.targetId = targetId;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }

    public String getReporterId() {
        return reporterId;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void printReportDetails() {
        System.out.println("Report submitted by: " + reporterId);
        System.out.println("Target ID: " + targetId);
        System.out.println("Reason: " + reason);
        System.out.println("Timestamp: " + timestamp);
    }
}