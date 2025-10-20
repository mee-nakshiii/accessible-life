package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlaceForm extends JFrame {

    private final PlaceService placeService = new PlaceService();

    // FIX: Added 'final' keyword to all fields
    private final JTextField nameField;
    private final JTextArea descriptionArea;
    private final JTextField locationField;
    private final JTextField categoryField;
    private final JCheckBox rampBox;
    private final JCheckBox toiletBox;
    private final JCheckBox brailleBox;
    private final JCheckBox elevatorBox;

    public AddPlaceForm() {
        setTitle("Admin: Add New Place");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 800);

        getContentPane().setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Add New Accessible Place", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(ThemeColors.TEXT_PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Form Panel (Card Background) ---
        JPanel formPanel = new JPanel();
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

        // --- Initialize Fields ---
        nameField = createStyledTextField("Name (e.g., City Park)");
        locationField = createStyledTextField("Location (e.g., 123 Main St)");
        categoryField = createStyledTextField("Category (e.g., Public)");
        descriptionArea = createStyledTextArea("Description (e.g., Has accessible entrance and parking)");

        // Checkboxes
        rampBox = new JCheckBox("Has Ramp");
        toiletBox = new JCheckBox("Accessible Toilet");
        brailleBox = new JCheckBox("Braille Signage");
        elevatorBox = new JCheckBox("Elevator");

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
        JButton saveBtn = ThemeButton.createPrimary("Save Place", ThemeColors.ACCENT_PRIMARY);
        JButton cancelBtn = ThemeButton.createPrimary("Cancel", ThemeColors.LOGOUT_RED);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(ThemeColors.CARD_BG);
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalStrut(20));


        // --- Action Listeners ---
        saveBtn.addActionListener(e -> savePlace());
        cancelBtn.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
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

    private void savePlace() {
        // Simple validation
        if (nameField.getText().trim().isEmpty() || locationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Place Name and Location are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // FIX: The Place constructor call now correctly uses the 8-argument version.
        // The unused variable warning is also implicitly resolved by omitting the unused line.
        boolean success = placeService.savePlace(new Place(
                nameField.getText().trim(),
                descriptionArea.getText().trim(),
                locationField.getText().trim(),
                categoryField.getText().trim(),
                rampBox.isSelected(),
                toiletBox.isSelected(),
                brailleBox.isSelected(),
                elevatorBox.isSelected()
        ));


        if (success) {
            JOptionPane.showMessageDialog(this, "New place added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save place. Check database connection/permissions.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}