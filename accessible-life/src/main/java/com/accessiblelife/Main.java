package com.accessiblelife;

import com.accessiblelife.gui.LoginPage;
import com.accessiblelife.db.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize the database connection
        // FIX: Change 'connect()' to 'getConnection()' which calls the connect logic.
        DatabaseManager.getConnection();

        // 2. Launch the GUI on the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}