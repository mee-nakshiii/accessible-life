package com.accessiblelife.gui;

import com.accessiblelife.model.Place; // FIX: Added import for Place
import com.accessiblelife.service.PlaceService; // FIX: Added import for PlaceService

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private final MainWindow parent;

    // Instantiate the service for use in report logic
    private final PlaceService placeService = new PlaceService(); // FIX: Added service instance

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color BORDER_GRAY = ThemeColors.BORDER_GRAY;

    // FIX: Parameter 'userName' is now used in the Welcome JLabel construction
    public HomePanel(MainWindow parent, String userName) {
        this.parent = parent;

        setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // --- 1. Main Welcome/Title ---
        // FIX: Using userName parameter in the greeting
        JLabel title = new JLabel("Welcome, " + userName + "!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(TEXT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        // --- 2. Central Content Card (Centered) ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BG_COLOR);

        JPanel featureCard = new JPanel();
        featureCard.setLayout(new BoxLayout(featureCard, BoxLayout.Y_AXIS));
        featureCard.setBackground(ThemeColors.CARD_BG);

        featureCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        // --- Components for the Card ---
        JLabel instruction = new JLabel("Main Features", SwingConstants.CENTER);
        instruction.setFont(new Font("Segoe UI", Font.BOLD, 26));
        instruction.setForeground(TEXT_COLOR);
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        instruction.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Buttons
        JButton searchBtn = createStyledButton("🔍 Search Accessible Places", ACCENT_COLOR);
        JButton reviewBtn = createStyledButton("✍️ Submit a Review", ACCENT_COLOR);
        JButton myPlacesBtn = createStyledButton("📍 My Submitted Places", ACCENT_COLOR);
        JButton reportBtn = createStyledButton("⚠ Report Inaccurate Info", ThemeColors.LOGOUT_RED);
        JButton feedbackBtn = createStyledButton("💬 Give Feedback", ACCENT_COLOR);

        // Layout
        featureCard.add(instruction);
        featureCard.add(Box.createVerticalStrut(20));
        featureCard.add(searchBtn);
        featureCard.add(Box.createVerticalStrut(10));
        featureCard.add(reviewBtn);
        featureCard.add(Box.createVerticalStrut(10));
        featureCard.add(myPlacesBtn);
        featureCard.add(Box.createVerticalStrut(25));
        featureCard.add(reportBtn);
        featureCard.add(Box.createVerticalStrut(10));
        featureCard.add(feedbackBtn);

        contentPanel.add(featureCard);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setBackground(ThemeColors.BG_PRIMARY);
        northPanel.add(title);

        add(northPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // --- ACTION LISTENERS ---
        searchBtn.addActionListener(e -> new SearchByFeature().setVisible(true));
        reviewBtn.addActionListener(e -> parent.showPanel("review"));

        myPlacesBtn.addActionListener(e -> new UserPlacesForm(parent.getCurrentUser().getId()).setVisible(true));
        feedbackBtn.addActionListener(e -> new FeedbackForm().setVisible(true));

        reportBtn.addActionListener(e -> reportPlaceFromDashboard()); // Calls the new method
    }

    private void reportPlaceFromDashboard() {
        String input = JOptionPane.showInputDialog(this,
                "Enter the ID of the place you want to report:",
                "Report Place", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                long placeId = Long.parseLong(input.trim());

                // Retrieve the Place object using the service layer
                Place placeToReport = placeService.getPlaceById(placeId); // FIX: PlaceService now resolved

                if (placeToReport != null) {
                    // Calls the ReportForm(long userId, Place place) constructor
                    new ReportForm(parent.getCurrentUser().getId(), placeToReport).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Place ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private JButton createStyledButton(String text, Color bg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setMaximumSize(new Dimension(350, 55));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}