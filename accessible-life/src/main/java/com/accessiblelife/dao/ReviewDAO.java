package com.accessiblelife.dao;

import com.accessiblelife.db.DBConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // --- ADD REVIEW (Insert a new rating/review) ---
    public boolean addReview(int placeId, int userId, RatingReview review) {
        String sql = "INSERT INTO reviews (place_id, user_id, rating, review_text) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, placeId);
            ps.setInt(2, userId);
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReview());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ReviewDAO - Add Review failed: " + e.getMessage());
            return false;
        }
    }

    // --- FETCH REVIEWS (Retrieve all reviews for a specific place) ---
    public List<RatingReview> fetchReviews(int placeId) {
        List<RatingReview> reviews = new ArrayList<>();
        // Join with users table to get the user's name (for the RatingReview constructor)
        String sql = "SELECT r.rating, r.review_text, u.name FROM reviews r JOIN users u ON r.user_id = u.user_id WHERE r.place_id = ?";

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, placeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String userName = rs.getString("name");
                    int rating = rs.getInt("rating");
                    String reviewText = rs.getString("review_text");

                    RatingReview review = new RatingReview(userName, rating, reviewText);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            System.err.println("ReviewDAO - Fetch Reviews failed: " + e.getMessage());
        }
        return reviews;
    }

    // --- AVERAGE RATING (Calculate the mean rating for a place) ---
    public double getAverageRating(int placeId) {
        double average = 0.0;
        String sql = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE place_id = ?";

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, placeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    average = rs.getDouble("avg_rating");
                }
            }
        } catch (SQLException e) {
            System.err.println("ReviewDAO - Get Average Rating failed: " + e.getMessage());
        }
        return average;
    }
}
