package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JFrame {
    private final UserService userService = new UserService();

    public RegisterPage() {
        setTitle("Register");
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

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel title = new JLabel("Create Your Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        for (JComponent field : new JComponent[]{nameField, emailField, passwordField}) {
            field.setMaximumSize(new Dimension(400, 40));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            formPanel.add(field);
            formPanel.add(Box.createVerticalStrut(15));
        }

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        for (JLabel label : new JLabel[]{nameLabel, emailLabel, passwordLabel}) {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            formPanel.add(label);
        }

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        registerBtn.setBackground(new Color(100, 149, 237)); // CornflowerBlue
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(200, 50));

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            User newUser = new User(name, email, password, false);
            boolean success = userService.registerUser(newUser);

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
                new MainWindow(newUser.getName()).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }
        });

        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(registerBtn);

        add(formPanel, new GridBagConstraints());

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }
}