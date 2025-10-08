package com.accessiblelife.gui;

import com.accessiblelife.api.ApiClient;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {
    public LoginPage(MainGUI mainGUI) {
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Go to Register");

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            boolean success = ApiClient.login(email, password);
            if (success) {
                mainGUI.showPanel("Admin");
            } else {
                JOptionPane.showMessageDialog(this, "Login failed");
            }
        });

        registerButton.addActionListener(e -> mainGUI.showPanel("Register"));

        add(emailLabel); add(emailField);
        add(passwordLabel); add(passwordField);
        add(loginButton); add(registerButton);
    }
}