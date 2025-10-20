package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private MainWindow parent;

    public HomePanel(MainWindow parent, String userName) {
        this.parent = parent;
        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel welcome = new JLabel("Welcome, " + userName, SwingConstants.CENTER);
        add(welcome);

        JButton searchBtn = new JButton("Search Places");
        JButton reviewBtn = new JButton("Add Review");
        JButton logoutBtn = new JButton("Logout");

        searchBtn.addActionListener(e -> parent.showPanel("search"));
        reviewBtn.addActionListener(e -> parent.showPanel("review"));
        logoutBtn.addActionListener(e -> System.exit(0));

        add(searchBtn);
        add(reviewBtn);
        add(logoutBtn);
    }
}