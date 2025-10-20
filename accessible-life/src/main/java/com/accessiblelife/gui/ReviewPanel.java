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
        setLayout(new GridLayout(0, 1));

        JTextField ratingField = new JTextField();
        JTextArea commentArea = new JTextArea();

        add(new JLabel("Rating (1–5):"));
        add(ratingField);
        add(new JLabel("Comment:"));
        add(new JScrollPane(commentArea));

        JButton submitBtn = new JButton("Submit Review");
        JButton backBtn = new JButton("Back to Home");

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

        add(submitBtn);
        add(backBtn);
    }
}