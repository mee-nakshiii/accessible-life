package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private final MainWindow parent;

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color BORDER_GRAY = ThemeColors.BORDER_GRAY;

    public HomePanel(MainWindow parent, String userName) {
        this.parent = parent;

        setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // --- 1. Main Welcome/Title (from MainWindow Header) ---
        JLabel title = new JLabel("Application Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(TEXT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        // --- 2. Central Content Card (Centered) ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BG_COLOR);

        JPanel featureCard = new JPanel();
        featureCard.setLayout(new BoxLayout(featureCard, BoxLayout.Y_AXIS));
        featureCard.setBackground(ThemeColors.CARD_BG); // White Card

        featureCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40) // Reduced padding
        ));

        // --- Components for the Card ---
        JLabel instruction = new JLabel("Main Features", SwingConstants.CENTER);
        instruction.setFont(new Font("Segoe UI", Font.BOLD, 26));
        instruction.setForeground(TEXT_COLOR);
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        instruction.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Search field (for quick search visual)
        JTextField searchField = createStyledTextField("Find accessible places...");
        searchField.setMaximumSize(new Dimension(350, 40));

        // --- PRIMARY BUTTONS ---
        JButton searchBtn = createStyledButton("🔍 Search Accessible Places", ACCENT_COLOR);
        JButton reviewBtn = createStyledButton("✍️ Submit a Review", ACCENT_COLOR);

        // --- NEW BUTTONS FOR MISSING FUNCTIONALITY ---
        JButton myPlacesBtn = createStyledButton("📍 My Submitted Places", ACCENT_COLOR);
        JButton feedbackBtn = createStyledButton("💬 Give Feedback", ACCENT_COLOR);

        // --- Layout ---
        featureCard.add(instruction);
        featureCard.add(searchField);
        featureCard.add(Box.createVerticalStrut(20));
        featureCard.add(searchBtn);
        featureCard.add(Box.createVerticalStrut(10));
        featureCard.add(reviewBtn);
        featureCard.add(Box.createVerticalStrut(25)); // Separator for management tools
        featureCard.add(myPlacesBtn);
        featureCard.add(Box.createVerticalStrut(10));
        featureCard.add(feedbackBtn);

        contentPanel.add(featureCard);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setBackground(ThemeColors.BG_PRIMARY);
        northPanel.add(title);

        add(northPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // --- ACTION LISTENERS (NEW) ---
        searchBtn.addActionListener(e -> new SearchByFeature().setVisible(true));
        reviewBtn.addActionListener(e -> parent.showPanel("review"));

        // NEW LISTENERS
        myPlacesBtn.addActionListener(e -> new UserPlacesForm(parent.getCurrentUser().getId()).setVisible(true));
        feedbackBtn.addActionListener(e -> new FeedbackForm().setVisible(true));
        // NOTE: Reporting (inaccurate info) is best placed on the Search/Review screens, not the Home screen.
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setForeground(ThemeColors.TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setMaximumSize(new Dimension(350, 55));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}