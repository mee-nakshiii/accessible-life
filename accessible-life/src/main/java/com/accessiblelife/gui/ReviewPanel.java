package com.accessiblelife.gui;

import com.accessiblelife.model.Review;
import com.accessiblelife.service.ReviewService;

import javax.swing.*;
import java.awt.*;

public class ReviewPanel extends JPanel {
    private MainWindow parent;
    private final ReviewService reviewService = new ReviewService();

    public ReviewPanel(MainWindow parent) {
        this.parent = parent;
        setBackground(new Color(240, 248, 255)); // AliceBlue
        setLayout(new GridBagLayout());

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel title = new JLabel("Submit Your Review", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel ratingLabel = new JLabel("Rating (1–5):");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JTextField ratingField = new JTextField();
        ratingField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ratingField.setMaximumSize(new Dimension(400, 40));

        JLabel commentLabel = new JLabel("Comment:");
        commentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JTextArea commentArea = new JTextArea(5, 40);
        commentArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentScroll.setMaximumSize(new Dimension(400, 100));

        JButton submitBtn = new JButton("Submit Review");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        submitBtn.setBackground(new Color(100, 149, 237)); // CornflowerBlue
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setMaximumSize(new Dimension(200, 50));

        JButton backBtn = new JButton("← Back to Home");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setMaximumSize(new Dimension(200, 40));

        // Action listeners
        submitBtn.addActionListener(e -> {
            try {
                int rating = Integer.parseInt(ratingField.getText().trim());
                String comment = commentArea.getText().trim();

                if (rating < 1 || rating > 5 || comment.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid rating and comment.");
                    return;
                }

                Review review = new Review(1, 1, rating, comment); // Replace with actual user/place IDs
                boolean success = reviewService.submitReview(review);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Review submitted!");
                    parent.showPanel("home");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit review.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rating must be a number.");
            }
        });

        backBtn.addActionListener(e -> parent.showPanel("home"));

        // Add components to form panel
        formPanel.add(title);
        formPanel.add(ratingLabel);
        formPanel.add(ratingField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(commentLabel);
        formPanel.add(commentScroll);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(submitBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backBtn);

        add(formPanel, new GridBagConstraints());
    }
}