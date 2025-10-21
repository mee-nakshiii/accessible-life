package com.accessiblelife.gui;

import com.accessiblelife.service.ReportService;
import com.accessiblelife.model.Place;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener; // Ensure this is imported if used in helpers

public class ReportForm extends JFrame {

    private final ReportService reportService = new ReportService();
    private final long currentUserId;
    private final Place place;

    private JTextArea reasonArea;

    public ReportForm(long userId, Place place) {
        this.currentUserId = userId;
        this.place = place;

        setTitle("Report Inaccurate Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);

        getContentPane().setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Report Place: " + place.getName(), SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(ThemeColors.LOGOUT_RED);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ThemeColors.CARD_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel reasonLabel = new JLabel("Reason for Report:");
        reasonLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reasonLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        reasonArea = new JTextArea(10, 30);
        reasonArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reasonArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(450, 200));

        JButton submitBtn = createStyledButton("Submit Report", ThemeColors.LOGOUT_RED);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- ACTION LISTENER ---
        submitBtn.addActionListener(e -> submitReport());

        formPanel.add(reasonLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(scrollPane);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(submitBtn);

        add(formPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitReport() {
        String reason = reasonArea.getText().trim();

        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please describe the reason for the report.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Final submission call
        boolean success = reportService.submitReport(place.getId(), currentUserId, reason);

        if (success) {
            // FIX: Ensure success message and immediate window closure on success
            JOptionPane.showMessageDialog(this, "Report submitted successfully! Admins will review.", "Report Sent", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // CRITICAL: Close the window on success
        } else {
            // Failure feedback
            JOptionPane.showMessageDialog(this, "Failed to submit report. Check database connection or logs for details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method (Assuming createStyledButton is defined elsewhere or locally)
    private JButton createStyledButton(String text, Color bg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setMaximumSize(new Dimension(200, 45));
        return button;
    }
}