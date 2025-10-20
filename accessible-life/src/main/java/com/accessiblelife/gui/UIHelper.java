package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class UIHelper {
    // Shared Theme Colors
    private static final Color TEXT_COLOR = new Color(47, 79, 79);
    private static final Color ACCENT_BORDER_COLOR = new Color(60, 179, 113);

    // Utility method to create a standardized, styled button
    public static JButton createStyledButton(String text, Color bg, Color fg, int fontSize, int maxWidth, int maxHeight) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Elegant styling for consistency
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(1, 1, 1, 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT_BORDER_COLOR, 2),
                        BorderFactory.createEmptyBorder(15, 30, 15, 30)
                )
        ));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(maxWidth, maxHeight));
        return button;
    }
}