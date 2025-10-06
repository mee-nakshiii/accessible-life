package com.accessiblelife.repository;

import com.accessiblelife.model.RatingReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<RatingReview, Long> {
    List<RatingReview> findByPlaceId(Long placeId);
}
