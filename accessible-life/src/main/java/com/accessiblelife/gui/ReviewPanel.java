package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.model.Review;
import com.accessiblelife.service.PlaceService; // New Import
import com.accessiblelife.service.ReviewService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap; // New Import
import java.util.List; // New Import
import java.util.Map; // New Import

public class ReviewPanel extends JPanel {
    private final MainWindow parent;
    private final ReviewService reviewService = new ReviewService();
    private final PlaceService placeService = new PlaceService(); // New Service instance
    private final long currentUserId;

    private JComboBox<String> placeComboBox; // Dropdown for places
    private final Map<String, Long> placeMap = new HashMap<>(); // Map name to ID

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color FIELD_BORDER = ThemeColors.BORDER_GRAY;

    public ReviewPanel(MainWindow parent, long userId) {
        this.parent = parent;
        this.currentUserId = userId;

        setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new GridBagLayout());

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ThemeColors.CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1),
                BorderFactory.createEmptyBorder(50, 100, 50, 100)
        ));
        formPanel.setPreferredSize(new Dimension(600, 650));

        JLabel title = new JLabel("Submit Your Review", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // --- NEW PLACE SELECTION COMPONENT ---
        JLabel placeLabel = createLabel("Select Place:");
        placeComboBox = new JComboBox<>();
        placeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        placeComboBox.setMaximumSize(new Dimension(400, 40));
        loadPlacesIntoComboBox(); // Load data on initialization

        JLabel ratingLabel = createLabel("Rating (1–5):");
        JTextField ratingField = createStyledTextField();
        ratingField.setMaximumSize(new Dimension(400, 40));

        JLabel commentLabel = createLabel("Comment:");
        JTextArea commentArea = createStyledTextArea();
        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentScroll.setMaximumSize(new Dimension(400, 150));
        commentScroll.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));


        JButton submitBtn = createStyledButton("Submit Review", ACCENT_COLOR, ThemeColors.PRIMARY_CONTRAST_TEXT);
        submitBtn.setMaximumSize(new Dimension(250, 50));

        JButton backBtn = createStyledButton("← Back to Home", new Color(180, 180, 180), TEXT_COLOR);
        backBtn.setMaximumSize(new Dimension(250, 40));

        // Action listeners
        submitBtn.addActionListener(e -> submitReview(ratingField, commentArea));
        backBtn.addActionListener(e -> parent.showPanel("home"));

        // Add components to form panel
        formPanel.add(title);
        formPanel.add(placeLabel);
        formPanel.add(placeComboBox); // Add the JComboBox
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(ratingLabel);
        formPanel.add(ratingField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(commentLabel);
        formPanel.add(commentScroll);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(submitBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backBtn);

        add(formPanel, new GridBagConstraints());
    }

    private void loadPlacesIntoComboBox() {
        List<Place> places = placeService.getAllPlaces();
        if (places.isEmpty()) {
            placeComboBox.addItem("No places available");
            return;
        }

        for (Place place : places) {
            String placeName = place.getName();
            placeComboBox.addItem(placeName);
            placeMap.put(placeName, place.getId()); // Store ID mapping
        }
    }

    private void submitReview(JTextField ratingField, JTextArea commentArea) {
        String selectedName = (String) placeComboBox.getSelectedItem();

        if (selectedName == null || selectedName.equals("No places available")) {
            JOptionPane.showMessageDialog(this, "Please select a valid place.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long placeId = placeMap.get(selectedName); // Retrieve the actual ID

        try {
            int rating = Integer.parseInt(ratingField.getText().trim());
            String comment = commentArea.getText().trim();

            if (rating < 1 || rating > 5 || comment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid rating (1-5) and comment.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Review review = new Review(currentUserId, placeId, rating, comment);
            boolean success = reviewService.submitReview(review);

            if (success) {
                JOptionPane.showMessageDialog(this, "Review submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                parent.showPanel("home");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit review.", "DB Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Rating must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));
        return field;
    }

    private JTextArea createStyledTextArea() {
        JTextArea area = new JTextArea(5, 40);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));
        return area;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new ThemeButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        return button;
    }
}