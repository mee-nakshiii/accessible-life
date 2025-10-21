package com.accessiblelife.gui;

import javax.swing.*;
import java.awt.*;

public class FeedbackForm extends JFrame {

    private JTextArea feedbackArea;

    public FeedbackForm() {
        setTitle("General User Feedback");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);

        getContentPane().setBackground(ThemeColors.BG_PRIMARY);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Share Your Feedback", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(ThemeColors.TEXT_PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ThemeColors.CARD_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel instruction = new JLabel("Tell us how we can improve the platform:", SwingConstants.LEFT);
        instruction.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        instruction.setAlignmentX(Component.LEFT_ALIGNMENT);

        feedbackArea = new JTextArea(8, 30);
        feedbackArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(400, 150)); // Adjusted max size

        JButton submitBtn = createStyledButton("Send Feedback", ThemeColors.ACCENT_PRIMARY);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(instruction);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(scrollPane);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(submitBtn);

        add(formPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitFeedback() {
        if (feedbackArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please type your feedback before submitting.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("--- USER FEEDBACK RECEIVED ---");
        System.out.println(feedbackArea.getText().trim());
        // In a real application, you'd save this to a database or send an email.

        JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Sent", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setMaximumSize(new Dimension(200, 45));
        return button;
    }
}