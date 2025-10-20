package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ThemeButton extends JButton {

    public ThemeButton(String text) {
        super(text);
        setContentAreaFilled(false); // CRITICAL: Forces custom painting
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the button area with the desired background color
        g2.setColor(getBackground());
        int arc = ThemeColors.CORNER_RADIUS;

        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));

        // Darken slightly on press
        if (getModel().isPressed()) {
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arc, arc));
        }

        g2.dispose();

        // Paint the text and icon over the colored background
        super.paintComponent(g);
    }

    // Utility for creating standard buttons with the theme
    public static JButton createPrimary(String text, Color bg) {
        ThemeButton button = new ThemeButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bg);
        button.setForeground(ThemeColors.PRIMARY_CONTRAST_TEXT);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        return button;
    }
}