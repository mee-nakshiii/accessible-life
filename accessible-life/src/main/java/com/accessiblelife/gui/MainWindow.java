package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainWindow(String userName) {
        setTitle("Accessible Life");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panels
        HomePanel home = new HomePanel(this, userName);
        SearchPanel search = new SearchPanel(this);
        ReviewPanel review = new ReviewPanel(this);

        mainPanel.add(home, "home");
        mainPanel.add(search, "search");
        mainPanel.add(review, "review");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}