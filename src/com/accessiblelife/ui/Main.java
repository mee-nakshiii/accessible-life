package com.accessiblelife.ui;

import com.accessiblelife.ui.admin.AdminDashboard;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }
}
