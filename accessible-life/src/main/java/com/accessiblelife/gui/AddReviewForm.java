package com.accessiblelife.gui;

import com.accessiblelife.api.ApiClient;
import com.accessiblelife.model.RatingReview;

import javax.swing.*;
import java.awt.*;

public class AddReviewForm extends JPanel {
    public AddReviewForm() {
        setLayout(new GridLayout(4, 2, 10, 10));

        JTextField placeIdField = new JTextField();
        JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JTextArea reviewArea = new JTextArea();
        JButton submitButton = new JButton("Submit Review");

        submitButton.addActionListener(e -> {
            int placeId = Integer.parseInt(placeIdField.getText());
            int rating = (int) ratingBox.getSelectedItem();
            String reviewText = reviewArea.getText();

            RatingReview review = new RatingReview("Admin", rating, reviewText);
            boolean success = ApiClient.addReview(placeId, review);

            if (success) {
                JOptionPane.showMessageDialog(this, "Review added");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add review");
            }
        });

        add(new JLabel("Place ID:")); add(placeIdField);
        add(new JLabel("Rating (1-5):")); add(ratingBox);
        add(new JLabel("Review:")); add(new JScrollPane(reviewArea));
        add(new JLabel()); add(submitButton);
    }
}