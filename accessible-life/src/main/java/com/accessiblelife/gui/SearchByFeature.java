package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class SearchByFeature extends JFrame {

    private final PlaceService placeService = new PlaceService();

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240); // Honeydew
    private static final Color ACCENT_COLOR = new Color(144, 238, 144); // Light Green
    private static final Color TEXT_COLOR = new Color(47, 79, 79); // Dark Slate Gray
    private static final Color FIELD_BORDER = new Color(180, 180, 180);

    private JCheckBox rampBox;
    private JCheckBox toiletBox;
    private JCheckBox brailleBox;
    private JCheckBox elevatorBox;
    private JTextArea resultsArea;

    public SearchByFeature() {
        setTitle("Search by Accessibility Features");
        setUndecorated(true);

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Search Places by Accessibility Features", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(ACCENT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 2, 40, 40));
        filterPanel.setBackground(BG_COLOR);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        rampBox = createStyledCheckBox("Has Ramp");
        toiletBox = createStyledCheckBox("Accessible Toilet");
        brailleBox = createStyledCheckBox("Braille Signage");
        elevatorBox = createStyledCheckBox("Elevator");

        filterPanel.add(rampBox);
        filterPanel.add(toiletBox);
        filterPanel.add(brailleBox);
        filterPanel.add(elevatorBox);

        // Results area
        resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        resultsArea.setEditable(false);
        resultsArea.setForeground(TEXT_COLOR);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(TEXT_COLOR), "Search Results",
                0, 0, new Font("Segoe UI", Font.BOLD, 16), TEXT_COLOR));

        // Search button
        JButton searchBtn = createStyledButton("Search", ACCENT_COLOR, Color.WHITE);
        searchBtn.setPreferredSize(new Dimension(250, 50));
        searchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton closeBtn = createStyledButton("Close", new Color(180, 180, 180), TEXT_COLOR);
        closeBtn.setPreferredSize(new Dimension(250, 50));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.add(filterPanel);

        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(BG_COLOR);
        buttonRow.add(searchBtn);
        buttonRow.add(Box.createHorizontalStrut(20));
        buttonRow.add(closeBtn);

        centerPanel.add(buttonRow);
        centerPanel.add(Box.createVerticalStrut(20));

        JPanel scrollWrapper = new JPanel(new BorderLayout());
        scrollWrapper.setBackground(BG_COLOR);
        scrollWrapper.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));
        scrollWrapper.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(scrollWrapper);

        add(centerPanel, BorderLayout.CENTER);

        // Action listener: Calls the PlaceService and updates the results area
        searchBtn.addActionListener(e -> performSearch());

        closeBtn.addActionListener(e -> dispose());

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }

    private void performSearch() {
        resultsArea.setText("");

        try {
            List<Place> results = placeService.searchPlacesByFeatures(
                    rampBox.isSelected(),
                    toiletBox.isSelected(),
                    brailleBox.isSelected(),
                    elevatorBox.isSelected()
            );

            if (results.isEmpty()) {
                resultsArea.setText("No accessible places found matching your criteria.");
            } else {
                StringBuilder sb = new StringBuilder("--- Found " + results.size() + " Places ---\n\n");
                for (Place place : results) {
                    sb.append("📍 ").append(place.getName()).append(" (").append(place.getCategory()).append(")\n");
                    sb.append("   Location: ").append(place.getLocation()).append("\n");
                    sb.append("   Description: ").append(place.getDescription()).append("\n\n");
                }
                resultsArea.setText(sb.toString());
            }

        } catch (Exception ex) {
            resultsArea.setText("Error during search: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox box = new JCheckBox(text);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        box.setBackground(BG_COLOR);
        box.setForeground(TEXT_COLOR);
        return box;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        return button;
    }
}