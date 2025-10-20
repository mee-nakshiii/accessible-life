package com.accessiblelife.gui;

import com.accessiblelife.model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JFrame {
    public ProfilePage(User user) {
        setTitle("Profile");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea profile = new JTextArea();
        profile.setEditable(false);
        profile.setText("Name: " + user.getName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Admin: " + (user.isAdmin() ? "Yes" : "No"));
        add(profile, BorderLayout.CENTER);
    }
}