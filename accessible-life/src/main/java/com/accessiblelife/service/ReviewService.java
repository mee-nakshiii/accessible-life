package com.accessiblelife.service;

import com.accessiblelife.db.DatabaseManager;
import com.accessiblelife.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewService {

    public boolean submitReview(Review review) {
        Connection conn = DatabaseManager.getConnection();

        if (conn == null) {
            System.err.println("❌ Connection is null. Cannot submit review.");
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO reviews (user_id, place_id, rating, review_text) VALUES (?, ?, ?, ?)")) {

            stmt.setLong(1, review.getUserId());
            stmt.setLong(2, review.getPlaceId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getReviewText());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL error while submitting review:");
            e.printStackTrace();
            return false;
        }
    }
}