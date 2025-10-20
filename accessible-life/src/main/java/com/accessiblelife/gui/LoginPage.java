package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginPage {
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private final UserService userService = new UserService();

    public LoginPage() {
        frame = new JFrame("Accessible Life - Login");
        frame.setUndecorated(true); // Remove window borders

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(frame);
        } else {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        frame.getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue
        frame.setLayout(new GridBagLayout()); // Center everything

        // Main panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(240, 248, 255));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel titleLabel = new JLabel("Login to Accessible Life", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailField.setMaximumSize(new Dimension(400, 40));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordField.setMaximumSize(new Dimension(400, 40));

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginBtn.setBackground(new Color(100, 149, 237)); // CornflowerBlue
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(200, 50));

        loginBtn.addActionListener(e -> attemptLogin());

        // Add components to panel
        loginPanel.add(titleLabel);
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(loginBtn);

        // Center the panel
        frame.add(loginPanel, new GridBagConstraints());
        frame.setVisible(true);
    }

    private void attemptLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        User user = userService.login(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(frame, "Login successful!");
            new MainWindow(user.getName()).setVisible(true);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid credentials. Try again.");
        }
    }
}