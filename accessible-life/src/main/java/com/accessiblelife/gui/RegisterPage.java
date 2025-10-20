package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JFrame {
    private final UserService userService = new UserService();

    public RegisterPage() {
        setTitle("Register");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton registerBtn = new JButton("Register");

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

        add(registerBtn);
    }
}