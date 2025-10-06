package com.accessiblelife.controller;

import com.accessiblelife.model.RatingReview;
import com.accessiblelife.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public RatingReview addReview(@RequestBody RatingReview review, @RequestParam Long placeId) {
        return reviewService.addReview(review, placeId);
    }

    @GetMapping("/list")
    public List<RatingReview> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/average")
    public double getAverageRating(@RequestParam Long placeId) {
        return reviewService.getAverageRating(placeId);
    }
    // Get reviews for a specific place
    @GetMapping("/place/{placeId}")
    public List<RatingReview> getReviewsForPlace(@PathVariable Long placeId) {
        return reviewService.getReviewsForPlace(placeId);
    }

}
