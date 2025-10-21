package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlaceForm extends JFrame {

    private final PlaceService placeService = new PlaceService();

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField locationField;
    private JTextField categoryField;
    private JCheckBox rampBox;
    private JCheckBox toiletBox;
    private JCheckBox brailleBox;
    private JCheckBox elevatorBox;

    private final long currentUserId; // Required field
    private final Place placeToEdit; // Required field

    // Main constructor for both ADDING and EDITING
    public AddPlaceForm(long userId, Place place) {
        this.currentUserId = userId;
        this.placeToEdit = place;

        // Set title based on action
        setTitle(place == null ? "Admin: Add New Place" : "Admin: Edit Place Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 800);

        getContentPane().setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new BorderLayout());

        JLabel header = new JLabel(place == null ? "Add New Accessible Place" : "Edit Place Details", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(ThemeColors.TEXT_PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Initialize Fields ---
        // (Initializes fields based on whether placeToEdit is null)
        nameField = createStyledTextField(place == null ? "Name (e.g., City Park)" : place.getName());
        locationField = createStyledTextField(place == null ? "Location (e.g., 123 Main St)" : place.getLocation());
        categoryField = createStyledTextField(place == null ? "Category (e.g., Public)" : place.getCategory());
        descriptionArea = createStyledTextArea(place == null ? "Description (e.g., Has accessible entrance and parking)" : place.getDescription());

        // Checkboxes
        rampBox = new JCheckBox("Has Ramp", place != null && place.isHasRamp());
        toiletBox = new JCheckBox("Accessible Toilet", place != null && place.isHasAccessibleToilet());
        brailleBox = new JCheckBox("Braille Signage", place != null && place.isHasBrailleSignage());
        elevatorBox = new JCheckBox("Elevator", place != null && place.isHasElevator());

        // ... (rest of checkbox/form panel setup) ...

        JPanel checkboxPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        checkboxPanel.setBackground(ThemeColors.CARD_BG);
        checkboxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (JCheckBox box : new JCheckBox[]{rampBox, toiletBox, brailleBox, elevatorBox}) {
            box.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            box.setBackground(ThemeColors.CARD_BG);
            box.setForeground(ThemeColors.TEXT_PRIMARY);
            checkboxPanel.add(box);
        }

        // Add components to form panel
        JPanel formPanel = new JPanel(); // Must be defined to add components
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ThemeColors.CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        scrollPane.getViewport().setBackground(ThemeColors.BG_PRIMARY);
        add(scrollPane, BorderLayout.CENTER);


        formPanel.add(createLabel("Place Name:"));
        formPanel.add(nameField);
        formPanel.add(createLabel("Location:"));
        formPanel.add(locationField);
        formPanel.add(createLabel("Category:"));
        formPanel.add(categoryField);
        formPanel.add(createLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(createLabel("Accessibility Features:"));
        formPanel.add(checkboxPanel);
        formPanel.add(Box.createVerticalStrut(20));


        // --- Action Buttons ---
        JButton saveBtn = ThemeButton.createPrimary(place == null ? "Save Place" : "Update Place", ThemeColors.ACCENT_PRIMARY);
        JButton cancelBtn = ThemeButton.createPrimary("Cancel", ThemeColors.LOGOUT_RED);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(ThemeColors.CARD_BG);
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalStrut(20));


        // --- Action Listeners ---
        saveBtn.addActionListener(e -> saveOrUpdatePlace());
        cancelBtn.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // NEW CONSTRUCTOR for simple Add Place call (no existing Place object)
    public AddPlaceForm(long userId) {
        this(userId, null);
    }

    private void saveOrUpdatePlace() {
        // Validation...
        if (nameField.getText().trim().isEmpty() || locationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Place Name and Location are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create the Place object from current form data
        Place currentPlace = new Place(
                placeToEdit != null ? placeToEdit.getId() : 0,
                nameField.getText().trim(),
                descriptionArea.getText().trim(),
                locationField.getText().trim(),
                categoryField.getText().trim(),
                rampBox.isSelected(),
                toiletBox.isSelected(),
                brailleBox.isSelected(),
                elevatorBox.isSelected()
        );

        boolean success;

        if (placeToEdit == null) {
            // New place submission
            success = placeService.savePlace(currentPlace);
        } else {
            // Existing place update
            success = placeService.updatePlace(currentPlace);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Place saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save place.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(ThemeColors.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        return label;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setMaximumSize(new Dimension(500, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JTextArea createStyledTextArea(String placeholder) {
        JTextArea area = new JTextArea(placeholder, 4, 40);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return area;
    }
}