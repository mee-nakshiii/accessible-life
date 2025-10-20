package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    private MainWindow parent;

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240); // Honeydew
    private static final Color TEXT_COLOR = new Color(47, 79, 79); // Dark Slate Gray
    private static final Color ACCENT_COLOR = new Color(144, 238, 144); // Light Green

    public SearchPanel(MainWindow parent) {
        this.parent = parent;
        setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Find Accessible Places", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(ACCENT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Results area
        JTextArea results = new JTextArea("Use the search filters to find results...");
        results.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        results.setEditable(false);
        results.setForeground(TEXT_COLOR);
        JScrollPane scrollPane = new JScrollPane(results);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(TEXT_COLOR), "Search Results",
                0, 0, new Font("Segoe UI", Font.BOLD, 16), TEXT_COLOR));
        scrollPane.getVerticalScrollBar().setBackground(BG_COLOR);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(BG_COLOR);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);


        // Back button
        JButton backBtn = createStyledButton("← Back to Home", new Color(180, 180, 180), TEXT_COLOR);
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> parent.showPanel("home"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
