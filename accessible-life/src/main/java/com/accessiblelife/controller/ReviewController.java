package com.accessiblelife.controller;

import com.accessiblelife.model.RatingReview;
import com.accessiblelife.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService = new ReviewService();

    @PostMapping("/add")
    public boolean addReview(@RequestParam int placeId, @RequestParam int userId,
                             @RequestBody RatingReview review) {
        return reviewService.addReview(placeId, userId, review);
    }

    @GetMapping("/list")
    public List<RatingReview> listReviews(@RequestParam int placeId) {
        return reviewService.getReviews(placeId);
    }

    @GetMapping("/average")
    public double averageRating(@RequestParam int placeId) {
        return reviewService.getAverageRating(placeId);
    }
}

