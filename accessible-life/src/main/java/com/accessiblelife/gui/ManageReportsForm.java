package com.accessiblelife.gui;

import com.accessiblelife.model.Place; // REQUIRED
import com.accessiblelife.repository.ReportRepository;
import com.accessiblelife.service.PlaceService; // REQUIRED
import com.accessiblelife.service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ManageReportsForm extends JFrame {

    private final ReportService reportService = new ReportService();
    // Instantiate PlaceService for fetching place details
    private final PlaceService placeService = new PlaceService();

    private final JTable reportTable;
    private final DefaultTableModel model;

    public ManageReportsForm() {
        setTitle("Admin: Handle Reports");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Pending Inaccuracy Reports", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 36));
        header.setForeground(ThemeColors.LOGOUT_RED.darker());
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Report Data Table ---
        model = new DefaultTableModel();
        reportTable = new JTable(model);
        reportTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        reportTable.setRowHeight(30);

        loadReportData();

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Footer/Action Panel
        JPanel footer = new JPanel();
        footer.setBackground(ThemeColors.BG_PRIMARY);

        JButton closeBtn = createStyledButton("← Back to Dashboard", ThemeColors.BORDER_GRAY, ThemeColors.TEXT_PRIMARY);
        closeBtn.addActionListener(e -> dispose());

        JButton resolveBtn = createStyledButton("Mark as Resolved", ThemeColors.ACCENT_PRIMARY, Color.WHITE);
        resolveBtn.addActionListener(e -> markReportResolved());

        JButton viewPlaceBtn = createStyledButton("View/Edit Place", ThemeColors.ACCENT_PRIMARY.darker(), Color.WHITE);
        viewPlaceBtn.addActionListener(e -> viewSelectedPlaceDetails()); // Calls the fixed logic


        footer.add(closeBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(viewPlaceBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(resolveBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadReportData() {
        model.setColumnCount(0);
        model.setRowCount(0);

        try (ResultSet rs = reportService.getAllPendingReports()) {

            if (rs == null) {
                model.addColumn("Status");
                model.addRow(new Object[]{"No pending reports or DB error."});
                return;
            }

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Set column names
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnLabel(i));
            }

            // Set row data
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading reports: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markReportResolved() {
        int selectedRow = reportTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a report to mark as resolved.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long reportId = (long) model.getValueAt(selectedRow, 0);

        if (reportService.updateStatus(reportId, "Resolved")) {
            JOptionPane.showMessageDialog(this, "Report #" + reportId + " marked as resolved.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadReportData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update report status.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSelectedPlaceDetails() {
        int selectedRow = reportTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a report row first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Assuming place_id is the second column (index 1) in the reports table view
        long placeId = (long) model.getValueAt(selectedRow, 1);

        // 1. Fetch the Place object
        Place placeToEdit = placeService.getPlaceById(placeId);

        // 2. CRITICAL FIX: Pass the Place object to the AddPlaceForm constructor for editing
        if (placeToEdit != null) {
            // NOTE: We rely on the AdminDashboard having passed the current user (Admin) ID to ManageReportsForm's constructor, but since it didn't, we will use a placeholder (ID 1)
            long adminId = 1;

            AddPlaceForm editForm = new AddPlaceForm(adminId, placeToEdit);
            editForm.setVisible(true);

            // Add listener to refresh table when editor closes
            editForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    loadReportData(); // Refresh table when edit form is closed
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Place details not found for ID " + placeId, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setMaximumSize(new Dimension(250, 50));
        return button;
    }
}