package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegisterPage extends JFrame {

    private final UserService userService = new UserService();

    public RegisterPage() {
        setTitle("Register New Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 750);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel.");
        }

        getContentPane().setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new GridBagLayout());

        // --- Form Card Panel ---
        JPanel formPanel = createRegisterCard();

        add(formPanel, new GridBagConstraints());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createRegisterCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(ThemeColors.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(50, 60, 50, 60)
        ));

        // Header
        JLabel title = new JLabel("Create Your Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(ThemeColors.ACCENT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel subtitle = new JLabel("Join our accessible community.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(ThemeColors.TEXT_PRIMARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));


        // Fields
        JTextField nameField = createStyledTextField("Full Name");
        JTextField emailField = createStyledTextField("Email Address");
        JPasswordField passwordField = createStyledPasswordField("Password");

        // Buttons
        JButton registerBtn = ThemeButton.createPrimary("Register", ThemeColors.ACCENT_PRIMARY);
        registerBtn.setMaximumSize(new Dimension(350, 55));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backToLoginBtn = createStyledButton("← Back to Login", ThemeColors.BORDER_GRAY, ThemeColors.TEXT_PRIMARY);
        backToLoginBtn.setMaximumSize(new Dimension(350, 40));
        backToLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);


        registerBtn.addActionListener(e -> attemptRegister(nameField, emailField, passwordField));
        backToLoginBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });

        // Layout
        card.add(title);
        card.add(subtitle);
        card.add(createLabel("Name:"));
        card.add(nameField);
        card.add(Box.createVerticalStrut(15));
        card.add(createLabel("Email:"));
        card.add(emailField);
        card.add(Box.createVerticalStrut(15));
        card.add(createLabel("Password:"));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(40));
        card.add(registerBtn);
        card.add(Box.createVerticalStrut(10));
        card.add(backToLoginBtn);

        return card;
    }

    private void attemptRegister(JTextField nameField, JTextField emailField, JPasswordField passwordField) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                name.equals("Full Name") || email.equals("Email Address") || password.equals("Password")) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // NOTE: Password hashing should be implemented here in a production environment
        User newUser = new User(name, email, password, false);
        boolean success = userService.registerUser(newUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginPage().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email might already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Helper Methods (Create as private methods in your class) ---
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(ThemeColors.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setForeground(ThemeColors.TEXT_PRIMARY);
        field.setBackground(ThemeColors.CARD_BG);
        field.setMaximumSize(new Dimension(350, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.addFocusListener(new PlaceholderFocusListener(field, placeholder, false));
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setForeground(ThemeColors.TEXT_PRIMARY);
        field.setBackground(ThemeColors.CARD_BG);
        field.setMaximumSize(new Dimension(350, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.BORDER_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setEchoChar((char) 0);
        field.addFocusListener(new PlaceholderFocusListener(field, placeholder, true));
        return field;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setForeground(fg);
        return button;
    }

    // Helper class for placeholder logic (put this inside RegisterPage.java)
    private static class PlaceholderFocusListener extends FocusAdapter {
        private final JTextField field;
        private final String placeholder;
        private final boolean isPassword;

        public PlaceholderFocusListener(JTextField field, String placeholder, boolean isPassword) {
            this.field = field;
            this.placeholder = placeholder;
            this.isPassword = isPassword;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (isPassword) {
                JPasswordField passField = (JPasswordField) field;
                if (new String(passField.getPassword()).equals(placeholder)) {
                    passField.setText("");
                    passField.setEchoChar('\u2022');
                }
            } else if (field.getText().equals(placeholder)) {
                field.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (field.getText().isEmpty()) {
                if (isPassword) {
                    JPasswordField passField = (JPasswordField) field;
                    passField.setText(placeholder);
                    passField.setEchoChar((char) 0);
                } else {
                    field.setText(placeholder);
                }
            }
        }
    }
}