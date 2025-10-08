package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private CardLayout layout;
    private JPanel container;

    public MainGUI() {
        setTitle("Accessible Life");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layout = new CardLayout();
        container = new JPanel(layout);

        container.add(new LoginPage(this), "Login");
        container.add(new RegisterPage(this), "Register");
        container.add(new AdminDashboard(this), "Admin");

        add(container);
        setVisible(true);
    }

    public void showPanel(String name) {
        layout.show(container, name);
    }
}