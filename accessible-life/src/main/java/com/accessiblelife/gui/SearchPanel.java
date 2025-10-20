package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    private MainWindow parent;

    public SearchPanel(MainWindow parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JTextArea results = new JTextArea("Search results will appear here...");
        results.setEditable(false);
        add(new JScrollPane(results), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Home");
        backBtn.addActionListener(e -> parent.showPanel("home"));
        add(backBtn, BorderLayout.SOUTH);
    }
}