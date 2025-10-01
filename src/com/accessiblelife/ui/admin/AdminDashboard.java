package com.accessiblelife.ui.admin;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with buttons
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton approvePlacesBtn = new JButton("Approve Places");
        JButton manageUsersBtn = new JButton("Manage Users");
        JButton logoutBtn = new JButton("Logout");

        // Add buttons
        panel.add(approvePlacesBtn);
        panel.add(manageUsersBtn);
        panel.add(logoutBtn);

        add(panel, BorderLayout.CENTER);

        // Action when clicking "Approve Places"
        approvePlacesBtn.addActionListener(e -> {
            // For now just open ApprovePlacesPanel
            new ApprovePlacesPanel();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
