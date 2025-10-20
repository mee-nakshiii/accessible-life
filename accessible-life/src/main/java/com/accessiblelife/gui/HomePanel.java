package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private MainWindow parent;

    public HomePanel(MainWindow parent, String userName) {
        this.parent = parent;

        setBackground(new Color(240, 248, 255)); // AliceBlue
        setLayout(new BorderLayout());

        // Welcome label
        JLabel welcome = new JLabel("Welcome, " + userName, SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcome.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        add(welcome, BorderLayout.NORTH);

        // Button panel with vertical layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 300, 50, 300));

        // Buttons
        JButton searchBtn = createStyledButton("🔍 Search Places");
        JButton reviewBtn = createStyledButton("✍️ Add Review");
        JButton logoutBtn = createStyledButton("🚪 Logout");

        // Add spacing and buttons
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(searchBtn);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(reviewBtn);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(logoutBtn);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setBackground(new Color(100, 149, 237)); // CornflowerBlue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 50));
        return button;
    }
}