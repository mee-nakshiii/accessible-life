package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JPanel {
    public AdminDashboard(MainGUI mainGUI) {
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton searchButton = new JButton("Search for Places");
        JButton reviewButton = new JButton("Add Review");
        JButton logoutButton = new JButton("Logout");

        searchButton.addActionListener(e -> {
            JFrame frame = new JFrame("Search");
            frame.setContentPane(new SearchForPatientPanel());
            frame.setSize(600, 400);
            frame.setVisible(true);
        });

        reviewButton.addActionListener(e -> {
            JFrame frame = new JFrame("Add Review");
            frame.setContentPane(new AddReviewForm());
            frame.setSize(600, 400);
            frame.setVisible(true);
        });

        logoutButton.addActionListener(e -> mainGUI.showPanel("Login"));

        add(searchButton);
        add(reviewButton);
        add(logoutButton);
    }
}