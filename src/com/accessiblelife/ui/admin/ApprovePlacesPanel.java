package com.accessiblelife.ui.admin;

import com.accessiblelife.controller.ApiClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ApprovePlacesPanel extends JFrame {

    private JTable placesTable;

    public ApprovePlacesPanel() {
        setTitle("Approve Places");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table with mock data
        String[] columnNames = {"Place ID", "Name", "Description"};
        Object[][] data = {
                {1, "Wheelchair Accessible Cafe", "Cafe with ramps"},
                {2, "Accessible Library", "Library with elevators"}
        };
        placesTable = new JTable(new DefaultTableModel(data, columnNames));

        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(approveBtn);
        buttonPanel.add(rejectBtn);

        add(new JScrollPane(placesTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ✅ Approve button action
        approveBtn.addActionListener(e -> {
            int selectedRow = placesTable.getSelectedRow();
            if (selectedRow >= 0) {
                int placeId = (int) placesTable.getValueAt(selectedRow, 0);
                String message = ApiClient.approvePlace(placeId);
                JOptionPane.showMessageDialog(this, message);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a place to approve.");
            }
        });

        // ✅ Reject button action
        rejectBtn.addActionListener(e -> {
            int selectedRow = placesTable.getSelectedRow();
            if (selectedRow >= 0) {
                int placeId = (int) placesTable.getValueAt(selectedRow, 0);
                String message = ApiClient.rejectPlace(placeId);
                JOptionPane.showMessageDialog(this, message);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a place to reject.");
            }
        });
    }
}
