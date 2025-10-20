package com.accessiblelife.gui;

import com.accessiblelife.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchByFeature extends JFrame {
    public SearchByFeature() {
        setTitle("Search by Accessibility Features");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new GridLayout(0, 2));
        JCheckBox rampBox = new JCheckBox("Has Ramp");
        JCheckBox toiletBox = new JCheckBox("Accessible Toilet");
        JCheckBox brailleBox = new JCheckBox("Braille Signage");
        JCheckBox elevatorBox = new JCheckBox("Elevator");

        filterPanel.add(rampBox);
        filterPanel.add(toiletBox);
        filterPanel.add(brailleBox);
        filterPanel.add(elevatorBox);

        JButton searchBtn = new JButton("Search");
        JTextArea results = new JTextArea();
        results.setEditable(false);

        searchBtn.addActionListener(e -> {
            StringBuilder query = new StringBuilder("SELECT place_name, location, description FROM places WHERE 1=1");

            if (rampBox.isSelected()) query.append(" AND has_ramp = TRUE");
            if (toiletBox.isSelected()) query.append(" AND has_accessible_toilet = TRUE");
            if (brailleBox.isSelected()) query.append(" AND has_braille_signage = TRUE");
            if (elevatorBox.isSelected()) query.append(" AND has_elevator = TRUE");

            results.setText("");

            try (Connection conn = DatabaseManager.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query.toString())) {

                while (rs.next()) {
                    results.append(rs.getString("place_name") + " - " + rs.getString("location") + "\n" +
                            rs.getString("description") + "\n\n");
                }

                if (results.getText().isEmpty()) {
                    results.setText("No places found with selected features.");
                }

            } catch (SQLException ex) {
                results.setText("Error: " + ex.getMessage());
            }
        });

        add(filterPanel, BorderLayout.NORTH);
        add(searchBtn, BorderLayout.CENTER);
        add(new JScrollPane(results), BorderLayout.SOUTH);
    }
}