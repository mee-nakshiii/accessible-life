package com.accessiblelife.gui;

import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JFrame {
    public ProfilePage(User user) {
        setTitle("Profile");
        setUndecorated(true); // Remove window borders

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue
        setLayout(new GridBagLayout());

        // Profile panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(240, 248, 255));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel title = new JLabel("User Profile", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel nameLabel = new JLabel("Name: " + user.getName());
        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        JLabel adminLabel = new JLabel("Admin: " + (user.isAdmin() ? "Yes" : "No"));

        for (JLabel label : new JLabel[]{nameLabel, emailLabel, adminLabel}) {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        }

        profilePanel.add(title);
        profilePanel.add(nameLabel);
        profilePanel.add(emailLabel);
        profilePanel.add(adminLabel);

        add(profilePanel, new GridBagConstraints());

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }
}