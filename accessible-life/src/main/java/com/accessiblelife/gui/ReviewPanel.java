package com.accessiblelife.gui;

import com.accessiblelife.model.Review;
import com.accessiblelife.service.ReviewService;

import javax.swing.*;
import java.awt.*;

public class ReviewPanel extends JPanel {
    private MainWindow parent;
    private final ReviewService reviewService = new ReviewService();
    private long currentUserId; // NEW FIELD to store logged-in user ID

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240);
    private static final Color ACCENT_COLOR = new Color(144, 238, 144);
    private static final Color TEXT_COLOR = new Color(47, 79, 79);
    private static final Color FIELD_BORDER = new Color(180, 180, 180);

    // FIX: Constructor now accepts the current user's ID
    public ReviewPanel(MainWindow parent, long userId) {
        this.parent = parent;
        this.currentUserId = userId; // Store the ID

        setBackground(BG_COLOR);
        setLayout(new GridBagLayout());

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        formPanel.setPreferredSize(new Dimension(600, 600));

        JLabel title = new JLabel("Submit Your Review", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel placeLabel = new JLabel("Place ID (Placeholder): 1"); // Ideally, this would be a real search/selection
        placeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JLabel ratingLabel = new JLabel("Rating (1–5):");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JTextField ratingField = createStyledTextField();
        ratingField.setMaximumSize(new Dimension(400, 40));

        JLabel commentLabel = new JLabel("Comment:");
        commentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JTextArea commentArea = createStyledTextArea();
        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentScroll.setMaximumSize(new Dimension(400, 150));
        commentScroll.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));


        JButton submitBtn = createStyledButton("Submit Review", ACCENT_COLOR, Color.WHITE);
        submitBtn.setMaximumSize(new Dimension(250, 50));

        JButton backBtn = createStyledButton("← Back to Home", new Color(180, 180, 180), TEXT_COLOR);
        backBtn.setMaximumSize(new Dimension(250, 40));

        // Action listeners
        submitBtn.addActionListener(e -> {
            try {
                int rating = Integer.parseInt(ratingField.getText().trim());
                String comment = commentArea.getText().trim();
                long placeId = 1; // HARDCODED PLACE ID for testing

                if (rating < 1 || rating > 5 || comment.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid rating (1-5) and comment.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // FIX: Use the stored user ID
                Review review = new Review(currentUserId, placeId, rating, comment);
                boolean success = reviewService.submitReview(review);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Review submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    parent.showPanel("home");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit review.", "DB Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rating must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> parent.showPanel("home"));

        // Add components to form panel
        formPanel.add(title);
        formPanel.add(placeLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(ratingLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(ratingField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(commentLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(commentScroll);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(submitBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backBtn);

        add(formPanel, new GridBagConstraints());
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBorder(BorderFactory.createLineBorder(FIELD_BORDER));
        return field;
    }

    private JTextArea createStyledTextArea() {
        JTextArea area = new JTextArea(5, 40);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
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