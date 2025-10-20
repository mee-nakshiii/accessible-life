package com.accessiblelife.gui;

import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard(User user) {
        // Apply modern look (optional: FlatLaf or custom UIManager tweaks)
        setTitle("Admin Dashboard");
        setUndecorated(true); // Removes window borders

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, Admin " + user.getName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(40, 40, 40));

        // Placeholder panel for future dashboard features
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setOpaque(false);
        dashboardPanel.add(Box.createVerticalStrut(50)); // spacing
        dashboardPanel.add(welcomeLabel);
        dashboardPanel.add(Box.createVerticalGlue());

        // Wrap everything
        setLayout(new BorderLayout());
        add(dashboardPanel, BorderLayout.CENTER);

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }
}