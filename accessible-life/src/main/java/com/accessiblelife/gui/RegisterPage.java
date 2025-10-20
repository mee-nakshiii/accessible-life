package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JFrame {
    private final UserService userService = new UserService();

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240); // Honeydew
    private static final Color ACCENT_COLOR = new Color(144, 238, 144); // Light Green
    private static final Color TEXT_COLOR = new Color(47, 79, 79); // Dark Slate Gray
    private static final Color FIELD_BORDER = new Color(180, 180, 180);

    public RegisterPage() {
        setTitle("Register");
        setUndecorated(true);

        // Full screen setup
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        getContentPane().setBackground(BG_COLOR);
        setLayout(new GridBagLayout());

        // Form panel (White card)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        formPanel.setPreferredSize(new Dimension(600, 650));

        JLabel title = new JLabel("Create Your Accessible Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(ACCENT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel subtitle = new JLabel("Sign up for full access to features.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(TEXT_COLOR);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));


        JTextField nameField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JPasswordField passwordField = createStyledPasswordField();


        JLabel nameLabel = createStyledLabel("Name:");
        JLabel emailLabel = createStyledLabel("Email:");
        JLabel passwordLabel = createStyledLabel("Password:");

        formPanel.add(title);
        formPanel.add(subtitle);

        // --- Name Field ---
        formPanel.add(nameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(15));

        // --- Email Field ---
        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(15));

        // --- Password Field ---
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(30));


        JButton registerBtn = createStyledButton("Register", ACCENT_COLOR, Color.WHITE);
        registerBtn.setMaximumSize(new Dimension(250, 50));
        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            User newUser = new User(name, email, password, false);
            boolean success = userService.registerUser(newUser);

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginPage(); // Return to Login Page
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Email might already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backToLogin = createStyledButton("← Back to Login", new Color(180, 180, 180), TEXT_COLOR);
        backToLogin.setMaximumSize(new Dimension(200, 40));
        backToLogin.addActionListener(e -> {
            dispose();
            new LoginPage();
        });


        formPanel.add(registerBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backToLogin);

        add(formPanel, new GridBagConstraints());

        // ESC key to exit full screen
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setVisible(true);
    }

    // --- Helper methods for styling ---
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(400, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(400, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));
        return field;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
