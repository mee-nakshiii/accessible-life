package com.accessiblelife.gui;

import com.accessiblelife.api.ApiClient;
import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JPanel {
    public RegisterPage(MainGUI mainGUI) {
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField locationField = new JTextField();

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(e -> {
            User user = new User();
            user.setName(nameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            user.setLocation(locationField.getText());

            boolean success = ApiClient.register(user);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful");
                mainGUI.showPanel("Login");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed");
            }
        });

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Password:")); add(passwordField);
        add(new JLabel("Location:")); add(locationField);
        add(new JLabel()); add(registerButton);
    }
}