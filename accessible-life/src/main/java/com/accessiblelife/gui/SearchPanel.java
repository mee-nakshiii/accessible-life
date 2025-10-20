package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    private MainWindow parent;

    public SearchPanel(MainWindow parent) {
        this.parent = parent;
        setBackground(new Color(240, 248, 255)); // AliceBlue
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Search Results", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Results area
        JTextArea results = new JTextArea("Search results will appear here...");
        results.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        results.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(results);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backBtn = new JButton("← Back to Home");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.setFocusPainted(false);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> parent.showPanel("home"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}