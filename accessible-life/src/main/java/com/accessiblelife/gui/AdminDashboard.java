package com.accessiblelife.gui;

import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;

    public AdminDashboard(User user) {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel for Admin Dashboard.");
        }

        getContentPane().setBackground(BG_COLOR);
        setLayout(new GridBagLayout());

        // --- Main Admin Card (Centralized) ---
        JPanel adminCard = new JPanel();
        adminCard.setLayout(new BoxLayout(adminCard, BoxLayout.Y_AXIS));
        adminCard.setBackground(ThemeColors.CARD_BG);
        adminCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(60, 100, 60, 100)
        ));

        // Welcome Header
        JLabel welcomeLabel = new JLabel("Welcome, Administrator, " + user.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(ThemeColors.ACCENT_PRIMARY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        // Buttons (using ThemeButton for visual elegance)
        JButton manageUsersBtn = ThemeButton.createPrimary("👤 Manage Users", ThemeColors.ACCENT_PRIMARY);
        manageUsersBtn.setMaximumSize(new Dimension(300, 55));

        JButton managePlacesBtn = ThemeButton.createPrimary("📍 Manage Places", ThemeColors.ACCENT_PRIMARY);
        managePlacesBtn.setMaximumSize(new Dimension(300, 55));

        JButton logoutBtn = ThemeButton.createPrimary("🚪 Logout", ThemeColors.LOGOUT_RED);
        logoutBtn.setMaximumSize(new Dimension(300, 55));

        // Layout
        adminCard.add(welcomeLabel);
        adminCard.add(manageUsersBtn);
        adminCard.add(Box.createVerticalStrut(20));
        adminCard.add(managePlacesBtn);
        adminCard.add(Box.createVerticalStrut(40));
        adminCard.add(logoutBtn);

        add(adminCard, new GridBagConstraints());

        // --- ACTION LISTENERS ---

        manageUsersBtn.addActionListener(e -> new ManageUsersForm().setVisible(true));

        managePlacesBtn.addActionListener(e -> new AddPlaceForm().setVisible(true));

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Admin logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            // CORRECT FIX: Chaining .setVisible(true) properly
            new LoginPage().setVisible(true);
        });

        setVisible(true);
    }
}