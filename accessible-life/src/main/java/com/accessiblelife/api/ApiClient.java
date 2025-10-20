package com.accessiblelife.api;

import com.accessiblelife.model.Review;
import com.accessiblelife.service.ReviewService;

public class ApiClient {

    private String email;

    private static final ReviewService reviewService = new ReviewService();

    public ApiClient(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public boolean submitReview(Review review) {
        return reviewService.submitReview(review);
    }
}