package com.accessiblelife.gui;

import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final User currentUser;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainWindow(User user) {
        this.currentUser = user;

        setTitle("Accessible Life - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel.");
        }

        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeColors.BG_PRIMARY);

        // --- 1. Top Header Bar ---
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // --- 2. Main Content Panel (CardLayout) ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(ThemeColors.BG_PRIMARY);

        // Panels
        HomePanel home = new HomePanel(this, user.getName());
        SearchPanel search = new SearchPanel(this);
        ReviewPanel review = new ReviewPanel(this, user.getId());

        mainPanel.add(home, "home");
        mainPanel.add(search, "search");
        mainPanel.add(review, "review");

        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "home");

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeColors.ACCENT_SECONDARY); // Lightest Mint Header
        header.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Left Side: Logo/Title
        JLabel logo = new JLabel("Accessible Life", SwingConstants.LEFT);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(ThemeColors.TEXT_PRIMARY);
        header.add(logo, BorderLayout.WEST);

        // Right Side: User Info and Logout Button
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userPanel.setBackground(header.getBackground());

        JLabel welcome = new JLabel("Welcome, " + currentUser.getName() + "!", SwingConstants.RIGHT);
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcome.setForeground(ThemeColors.TEXT_PRIMARY);

        // Use ThemeButton for high contrast logout button
        JButton logoutBtn = ThemeButton.createPrimary("Logout", ThemeColors.LOGOUT_RED);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // Smaller padding for header

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new LoginPage();
        });

        userPanel.add(welcome);
        userPanel.add(logoutBtn);

        header.add(userPanel, BorderLayout.EAST);

        return header;
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public long getCurrentUserId() {
        return currentUser.getId();
    }

    public User getCurrentUser() {
        return currentUser;
    }
}