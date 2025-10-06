package com.accessiblelife.service;

import com.accessiblelife.model.Place;
import com.accessiblelife.model.RatingReview;
import com.accessiblelife.repository.PlaceRepository;
import com.accessiblelife.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceRepository placeRepository;

    public RatingReview addReview(RatingReview review, Long placeId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        if (place != null) {
            review.setPlace(place);
            return reviewRepository.save(review);
        }
        return null;
    }

    public List<RatingReview> getAllReviews() {
        return reviewRepository.findAll();
    }

    public double getAverageRating(Long placeId) {
        return reviewRepository.findByPlaceId(placeId).stream()
                .mapToInt(RatingReview::getRating)
                .average()
                .orElse(0);
    }

    public List<RatingReview> getReviewsForPlace(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }
}
