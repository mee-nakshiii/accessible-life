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
        frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(0, 1, 10, 10));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        frame.add(new JLabel("Email:"));
        frame.add(emailField);
        frame.add(new JLabel("Password:"));
        frame.add(passwordField);
        frame.add(loginBtn);

        loginBtn.addActionListener(e -> attemptLogin());

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