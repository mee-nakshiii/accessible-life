package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors; // Necessary import for .toList() conversion

public class SearchByFeature extends JFrame {

    private final PlaceService placeService = new PlaceService();

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color FIELD_BORDER = ThemeColors.BORDER_GRAY;

    private final JTextField locationField; // New field for location search
    private final JCheckBox rampBox;
    private final JCheckBox toiletBox;
    private final JCheckBox brailleBox;
    private final JCheckBox elevatorBox;
    private final JTextArea resultsArea;

    public SearchByFeature() {
        setTitle("Search by Accessibility Features");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Search Places by Accessibility Features", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(ACCENT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // --- Filter Panel ---
        JPanel filterWrapperPanel = new JPanel();
        filterWrapperPanel.setLayout(new BoxLayout(filterWrapperPanel, BoxLayout.Y_AXIS));
        filterWrapperPanel.setBackground(BG_COLOR);
        filterWrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        // Location Search Field
        locationField = createStyledTextField("Search by Name or Location...");
        locationField.setMaximumSize(new Dimension(800, 40));

        JPanel checkboxPanel = new JPanel(new GridLayout(2, 2, 40, 40));
        checkboxPanel.setBackground(BG_COLOR);

        rampBox = createStyledCheckBox("Has Ramp");
        toiletBox = createStyledCheckBox("Accessible Toilet");
        brailleBox = createStyledCheckBox("Braille Signage");
        elevatorBox = createStyledCheckBox("Elevator");

        checkboxPanel.add(rampBox);
        checkboxPanel.add(toiletBox);
        checkboxPanel.add(brailleBox);
        checkboxPanel.add(elevatorBox);

        filterWrapperPanel.add(locationField);
        filterWrapperPanel.add(Box.createVerticalStrut(20));
        filterWrapperPanel.add(checkboxPanel);
        add(filterWrapperPanel, BorderLayout.WEST); // Place filters on the left

        // --- Results Area ---
        resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        resultsArea.setEditable(false);
        resultsArea.setForeground(TEXT_COLOR);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(TEXT_COLOR), "Search Results",
                0, 0, new Font("Segoe UI", Font.BOLD, 16), TEXT_COLOR));

        JPanel resultsWrapper = new JPanel(new BorderLayout());
        resultsWrapper.setBackground(BG_COLOR);
        resultsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 100));
        resultsWrapper.add(scrollPane, BorderLayout.CENTER);

        // --- Action Buttons ---
        JButton searchBtn = createStyledButton("Search", ACCENT_COLOR, ThemeColors.PRIMARY_CONTRAST_TEXT);
        JButton closeBtn = createStyledButton("Close", new Color(180, 180, 180), TEXT_COLOR);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setBackground(BG_COLOR);
        buttonRow.add(searchBtn);
        buttonRow.add(closeBtn);

        resultsWrapper.add(buttonRow, BorderLayout.SOUTH);

        // Combine Filters and Results into the Center
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.add(filterWrapperPanel);
        centerPanel.add(resultsWrapper);

        add(centerPanel, BorderLayout.CENTER);

        // Action listener: Calls the PlaceService and updates the results area
        searchBtn.addActionListener(e -> performSearch());
        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void performSearch() {
        resultsArea.setText("Searching...\n");
        String locationQuery = locationField.getText().trim();

        try {
            List<Place> results = placeService.searchPlacesByFeatures(
                    rampBox.isSelected(),
                    toiletBox.isSelected(),
                    brailleBox.isSelected(),
                    elevatorBox.isSelected()
            );

            // Filter results by location query if provided (simple in-memory filter for now)
            if (!locationQuery.isEmpty() && !locationQuery.equals("Search by Name or Location...")) {
                results = results.stream()
                        .filter(p -> p.getName().toLowerCase().contains(locationQuery.toLowerCase()) ||
                                p.getLocation().toLowerCase().contains(locationQuery.toLowerCase()))
                        .collect(Collectors.toList()); // Fix for .toList()
            }

            if (results.isEmpty()) {
                resultsArea.setText("No accessible places found matching your criteria.");
            } else {
                StringBuilder sb = new StringBuilder("--- Found " + results.size() + " Places ---\n\n");
                for (Place place : results) {
                    sb.append("📍 ").append(place.getName()).append(" (").append(place.getCategory()).append(")\n");
                    sb.append("   Location: ").append(place.getLocation()).append("\n");
                    sb.append("   Features: RAMP: ").append(place.isHasRamp() ? "Yes" : "No").append(" | TOILET: ").append(place.isHasAccessibleToilet() ? "Yes" : "No").append("\n");
                    sb.append("   Description: ").append(place.getDescription()).append("\n\n");
                }
                resultsArea.setText(sb.toString());
            }

        } catch (Exception ex) {
            resultsArea.setText("Error during search: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // --- Helper Methods ---
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox box = new JCheckBox(text);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        box.setBackground(BG_COLOR);
        box.setForeground(TEXT_COLOR);
        return box;
    }

    // FIX: Simplified button creation to resolve symbol error and rely on ThemeButton
    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Rely on ThemeButton for main styling
        return button;
    }
}