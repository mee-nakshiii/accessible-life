package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginPage {
    private final JFrame frame;
    private final UserService userService = new UserService();

    // --- THEME COLORS ---
    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color FIELD_BORDER = ThemeColors.BORDER_GRAY;

    public LoginPage() {
        frame = new JFrame("Accessible Life - Login");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel.");
        }

        // --- CRITICAL FIX ---
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fixed size frame, centered (desktop style)
        frame.setSize(550, 650);
        // ... (rest of the constructor remains the same)

        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(new GridBagLayout());

        // --- 1. Main Login Card Panel ---
        JPanel loginPanel = createLoginCard();

        // Center the panel
        frame.add(loginPanel, new GridBagConstraints());

        frame.setLocationRelativeTo(null); // Center the frame
        // NOTE: setVisible(true) is called externally via the new method
    }

    // ... (rest of the LoginPage class methods) ...

    private JPanel createLoginCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(ThemeColors.CARD_BG);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1),
                BorderFactory.createEmptyBorder(60, 50, 60, 50)
        ));

        // Logo and Title
        JLabel logoLabel = new JLabel("Accessible Life");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logoLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel subtitleLabel = new JLabel("Login to Your Account", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));


        JTextField emailField = createStyledTextField("Email");
        JPasswordField passwordField = createStyledPasswordField("Password");

        JButton loginBtn = ThemeButton.createPrimary("Login", ACCENT_COLOR);
        loginBtn.setMaximumSize(new Dimension(300, 55));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> attemptLogin(emailField, passwordField));

        JLabel registerLink = new JLabel("Don't have an account? Register");
        registerLink.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerLink.setForeground(ThemeColors.TEXT_PRIMARY);
        registerLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLink.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        registerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose();
                new RegisterPage().setVisible(true);
            }
        });


        // Layout
        card.add(logoLabel);
        card.add(subtitleLabel);
        card.add(emailField);
        card.add(Box.createVerticalStrut(15));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(40));
        card.add(loginBtn);
        card.add(registerLink);

        return card;
    }

    // --- CRITICAL FIX METHOD (Needs to remain as is) ---
    public void setVisible(boolean b) {
        if (frame != null) {
            frame.setVisible(b);
        }
    }

    // Method to create a styled JTextField
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setText(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setForeground(ThemeColors.TEXT_PRIMARY);
        field.setBackground(ThemeColors.CARD_BG);
        field.setMaximumSize(new Dimension(350, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        addPlaceholderBehavior(field, placeholder, false); // Add focus listener
        return field;
    }

    // Method to create a styled JPasswordField
    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setText(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setForeground(ThemeColors.TEXT_PRIMARY);
        field.setBackground(ThemeColors.CARD_BG);
        field.setMaximumSize(new Dimension(350, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setEchoChar((char) 0); // Show placeholder as plain text initially
        addPlaceholderBehavior(field, placeholder, true); // Add focus listener
        return field;
    }

    // Helper method to add focus listener for placeholder behavior
    private void addPlaceholderBehavior(JTextField field, String placeholder, boolean isPasswordField) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPasswordField) {
                    JPasswordField passField = (JPasswordField) field;
                    if (new String(passField.getPassword()).equals(placeholder)) {
                        passField.setText("");
                        passField.setEchoChar('\u2022'); // Change to bullet for password
                    }
                } else {
                    if (field.getText().equals(placeholder)) {
                        field.setText("");
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    if (isPasswordField) {
                        JPasswordField passField = (JPasswordField) field;
                        passField.setText(placeholder);
                        passField.setEchoChar((char) 0); // Show placeholder as plain text
                    } else {
                        field.setText(placeholder);
                    }
                }
            }
        });
    }

    private void attemptLogin(JTextField emailField, JPasswordField passwordField) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Prevent login with placeholder text
        if (email.equals("Email") || password.equals("Password") || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your email and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userService.login(email, password);

        if (user != null) {
            frame.dispose();
            if (user.isAdmin()) {
                new AdminDashboard(user).setVisible(true);
            } else {
                new MainWindow(user).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}