package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainWindow(String userName) {
        setTitle("Accessible Life");
        setUndecorated(true); // Removes window borders

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        // Main content panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(240, 248, 255)); // AliceBlue

        // Panels
        HomePanel home = new HomePanel(this, userName);
        SearchPanel search = new SearchPanel(this);
        ReviewPanel review = new ReviewPanel(this);

        mainPanel.add(home, "home");
        mainPanel.add(search, "search");
        mainPanel.add(review, "review");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}