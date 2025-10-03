package com.accessiblelife.service;

import com.accessiblelife.dao.ReviewDAO;
import com.accessiblelife.RatingReview;

import java.util.List;

public class ReviewService {

    private ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }

    /**
     * Add a new review for a place
     * @param placeId ID of the place
     * @param userId ID of the user posting review
     * @param review RatingReview object
     * @return true if added successfully
     */
    public boolean addReview(int placeId, int userId, RatingReview review) {
        if (review == null || review.getRating() < 1 || review.getRating() > 5 || review.getReview() == null) {
            return false; // validation
        }
        return reviewDAO.addReview(placeId, userId, review);
    }

    /**
     * Fetch all reviews for a place
     * @param placeId ID of the place
     * @return list of RatingReview objects
     */
    public List<RatingReview> getReviews(int placeId) {
        return reviewDAO.fetchReviews(placeId);
    }

    /**
     * Calculate average rating of a place
     * @param placeId ID of the place
     * @return average rating (0.0 if no reviews)
     */
    public double getAverageRating(int placeId) {
        return reviewDAO.getAverageRating(placeId);
    }
}

