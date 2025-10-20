package com.accessiblelife.gui;

import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JFrame {

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240); // Honeydew
    private static final Color TEXT_COLOR = new Color(47, 79, 79); // Dark Slate Gray
    private static final Color ACCENT_COLOR = new Color(144, 238, 144); // Light Green

    public ProfilePage(User user) {
        setTitle("Profile");
        setUndecorated(true);

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        getContentPane().setBackground(BG_COLOR);
        setLayout(new GridBagLayout());

        // Profile panel (White card)
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("Your Profile", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(ACCENT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel nameLabel = new JLabel("Name: " + user.getName());
        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        JLabel adminLabel = new JLabel("Role: " + (user.isAdmin() ? "Administrator" : "User"));

        for (JLabel label : new JLabel[]{nameLabel, emailLabel, adminLabel}) {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 22));
            label.setForeground(TEXT_COLOR);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        }

        JButton closeBtn = createStyledButton("Close / Back", new Color(180, 180, 180), TEXT_COLOR);
        closeBtn.setMaximumSize(new Dimension(200, 40));
        closeBtn.addActionListener(e -> dispose());


        profilePanel.add(title);
        profilePanel.add(nameLabel);
        profilePanel.add(emailLabel);
        profilePanel.add(adminLabel);
        profilePanel.add(Box.createVerticalStrut(40));
        profilePanel.add(closeBtn);

        add(profilePanel, new GridBagConstraints());

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
