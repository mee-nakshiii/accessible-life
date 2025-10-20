package com.accessiblelife.service;

import com.accessiblelife.model.Review;
import com.accessiblelife.repository.ReviewRepository;

public class ReviewService {
    private final ReviewRepository reviewRepository = new ReviewRepository();

    public boolean submitReview(Review review) {
        // Here you could add business logic like rating validation
        if (review.getRating() < 1 || review.getRating() > 5) {
            System.err.println("Invalid rating value.");
            return false;
        }
        return reviewRepository.addReview(review);
    }
}