package com.accessiblelife;

import com.accessiblelife.model.RatingReview;
import java.util.ArrayList;
import java.util.List;

public class RatingReviewManager {

    private final List<RatingReview> reviews = new ArrayList<>();

    // Add a new review
    public void addReview(RatingReview review) {
        reviews.add(review);
    }

    // Show all reviews
    public void showReviews() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews yet.");
        } else {
            for (RatingReview review : reviews) {
                System.out.println(review);
            }
        }
    }

    // Get average rating
    public double getAverageRating() {
        if (reviews.isEmpty()) return 0.0;

        double sum = 0;
        for (RatingReview review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }

    // Getter to access all reviews
    public List<RatingReview> getReviews() {
        return reviews;
    }
}
