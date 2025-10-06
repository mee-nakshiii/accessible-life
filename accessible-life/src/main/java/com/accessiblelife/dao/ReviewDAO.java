package com.accessiblelife.dao;

import com.accessiblelife.model.Place;
import com.accessiblelife.model.RatingReview;
import com.accessiblelife.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDAO {

    @Autowired
    private ReviewRepository reviewRepository;

    public RatingReview addReview(RatingReview review) {
        return reviewRepository.save(review);
    }

    public List<RatingReview> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<RatingReview> getReviewsByPlaceId(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }
}
