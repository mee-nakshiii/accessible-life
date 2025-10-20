package com.accessiblelife.gui;

import com.accessiblelife.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchByFeature extends JFrame {
    public SearchByFeature() {
        setTitle("Search by Accessibility Features");
        setUndecorated(true); // Remove window borders

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Search Places by Accessibility Features", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        filterPanel.setBackground(new Color(240, 248, 255));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JCheckBox rampBox = new JCheckBox("Has Ramp");
        JCheckBox toiletBox = new JCheckBox("Accessible Toilet");
        JCheckBox brailleBox = new JCheckBox("Braille Signage");
        JCheckBox elevatorBox = new JCheckBox("Elevator");

        for (JCheckBox box : new JCheckBox[]{rampBox, toiletBox, brailleBox, elevatorBox}) {
            box.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            filterPanel.add(box);
        }

        // Results area
        JTextArea results = new JTextArea();
        results.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        results.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(results);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));

        // Search button
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        searchBtn.setBackground(new Color(100, 149, 237)); // CornflowerBlue
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setPreferredSize(new Dimension(200, 50));
        searchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(filterPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(searchBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // Action listener
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

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }
}