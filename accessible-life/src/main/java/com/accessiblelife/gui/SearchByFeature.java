package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByFeature extends JFrame {

    private final PlaceService placeService = new PlaceService();
    private List<Place> lastSearchResults; // To hold results for reporting/editing

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color FIELD_BORDER = ThemeColors.BORDER_GRAY;

    private final JTextField locationField;
    private final JCheckBox rampBox;
    private final JCheckBox toiletBox;
    private final JCheckBox brailleBox;
    private final JCheckBox elevatorBox;
    private final JTextArea resultsArea;
    private final JButton reportBtn; // NEW BUTTON

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
        add(filterWrapperPanel, BorderLayout.WEST);

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

        // --- Action Buttons (Bottom) ---
        JButton searchBtn = createStyledButton("Search", ACCENT_COLOR, ThemeColors.PRIMARY_CONTRAST_TEXT);
        JButton closeBtn = createStyledButton("Close", new Color(180, 180, 180), TEXT_COLOR);
        reportBtn = createStyledButton("⚠ Report Inaccurate Info", ThemeColors.LOGOUT_RED, ThemeColors.PRIMARY_CONTRAST_TEXT);

        // Initially hide the report button
        reportBtn.setVisible(false);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setBackground(BG_COLOR);
        buttonRow.add(searchBtn);
        buttonRow.add(closeBtn);
        buttonRow.add(reportBtn); // Add report button to the row

        resultsWrapper.add(scrollPane, BorderLayout.CENTER);
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
        reportBtn.addActionListener(e -> reportSelectedPlace());

        setVisible(true);
    }

    private void performSearch() {
        resultsArea.setText("Searching...\n");
        String locationQuery = locationField.getText().trim();
        reportBtn.setVisible(false); // Hide button before search

        try {
            List<Place> results = placeService.searchPlacesByFeatures(
                    rampBox.isSelected(),
                    toiletBox.isSelected(),
                    brailleBox.isSelected(),
                    elevatorBox.isSelected()
            );

            // Filter results by location query
            if (!locationQuery.isEmpty() && !locationQuery.equals("Search by Name or Location...")) {
                results = results.stream()
                        .filter(p -> p.getName().toLowerCase().contains(locationQuery.toLowerCase()) ||
                                p.getLocation().toLowerCase().contains(locationQuery.toLowerCase()))
                        .toList();
            }

            // Store results globally for reporting/editing actions
            lastSearchResults = results;

            if (results.isEmpty()) {
                resultsArea.setText("No accessible places found matching your criteria.");
            } else {
                StringBuilder sb = new StringBuilder("--- Found " + results.size() + " Places ---\n\n");

                // Display results with a clear index for selection
                int index = 1;
                for (Place place : results) {
                    sb.append(index++).append(". 📍 ").append(place.getName()).append(" (").append(place.getCategory()).append(")\n");
                    sb.append("   Location: ").append(place.getLocation()).append("\n");
                    sb.append("   Features: RAMP: ").append(place.isHasRamp() ? "Yes" : "No").append(" | TOILET: ").append(place.isHasAccessibleToilet() ? "Yes" : "No").append("\n");
                    sb.append("   Description: ").append(place.getDescription()).append("\n\n");
                }
                resultsArea.setText(sb.toString());

                // Show report button if results are available
                reportBtn.setVisible(true);
            }

        } catch (Exception ex) {
            resultsArea.setText("Error during search: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void reportSelectedPlace() {
        if (lastSearchResults == null || lastSearchResults.isEmpty()) return;

        // Prompt user to select an index number from the displayed results
        String input = JOptionPane.showInputDialog(this,
                "Enter the number (1, 2, 3...) of the place you want to report:",
                "Report Place", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                int index = Integer.parseInt(input.trim()) - 1;
                if (index >= 0 && index < lastSearchResults.size()) {
                    Place selectedPlace = lastSearchResults.get(index);

                    // Open the Report Form, passing the selected place and user ID
                    // NOTE: Assumes MainWindow has a getter for the current user's ID
                    long currentUserId = ((MainWindow) SwingUtilities.getWindowAncestor(this)).getCurrentUser().getId();

                    new ReportForm(currentUserId, selectedPlace).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid number entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassCastException e) {
                // Handles case where parent is not MainWindow (e.g., direct launch)
                JOptionPane.showMessageDialog(this, "Cannot determine current user for reporting.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        return button;
    }
}