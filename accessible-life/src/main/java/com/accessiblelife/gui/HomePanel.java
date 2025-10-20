package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private final MainWindow parent;

    public HomePanel(MainWindow parent, String userName) {
        this.parent = parent;

        setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new BorderLayout());

        // --- 1. Main Welcome/Title ---
        JLabel title = new JLabel("Application Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(ThemeColors.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        // --- 2. Central Content Card (Centered) ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(ThemeColors.BG_PRIMARY);

        JPanel featureCard = new JPanel();
        featureCard.setLayout(new BoxLayout(featureCard, BoxLayout.Y_AXIS));
        featureCard.setBackground(ThemeColors.CARD_BG); // White Card

        // Soft border for the card
        featureCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(60, 60, 60, 60)
        ));

        // --- Components for the Card ---
        JLabel instruction = new JLabel("Main Features", SwingConstants.CENTER);
        instruction.setFont(new Font("Segoe UI", Font.BOLD, 26));
        instruction.setForeground(ThemeColors.TEXT_PRIMARY);
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        instruction.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Search field (for quick search visual)
        JTextField searchField = new JTextField("Find accessible places...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setForeground(ThemeColors.TEXT_PRIMARY);
        searchField.setMaximumSize(new Dimension(350, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons using ThemeButton for high contrast
        JButton searchBtn = ThemeButton.createPrimary("🔍 Search Accessible Places", ThemeColors.ACCENT_PRIMARY);
        searchBtn.setMaximumSize(new Dimension(350, 55));

        JButton reviewBtn = ThemeButton.createPrimary("✍️ Submit a Review", ThemeColors.ACCENT_PRIMARY);
        reviewBtn.setMaximumSize(new Dimension(350, 55));


        // Add components to the card
        featureCard.add(instruction);
        featureCard.add(searchField);
        featureCard.add(Box.createVerticalStrut(30));
        featureCard.add(searchBtn);
        featureCard.add(Box.createVerticalStrut(15));
        featureCard.add(reviewBtn);

        contentPanel.add(featureCard);

        // Add components to the MainPanel
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setBackground(ThemeColors.BG_PRIMARY);
        northPanel.add(title);

        add(northPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // --- ACTION LISTENERS ---
        searchBtn.addActionListener(e -> new SearchByFeature().setVisible(true));
        reviewBtn.addActionListener(e -> parent.showPanel("review"));
    }
}
