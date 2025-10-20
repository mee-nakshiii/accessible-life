package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;
import com.accessiblelife.model.Review;

import java.sql.*;

public class ReviewRepository {

    public boolean addReview(Review review) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO reviews (user_id, place_id, rating, review_text) VALUES (?, ?, ?, ?)")) {

            stmt.setLong(1, review.getUserId());
            stmt.setLong(2, review.getPlaceId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}