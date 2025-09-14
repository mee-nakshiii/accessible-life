package com.accessiblelife;

import com.accessiblelife.model.Place;
import com.accessiblelife.ratingreview.RatingReview;
import com.accessiblelife.ratingreview.RatingReviewManager;

public class Main
{
    public static void main(String[] args)
    {
        // Create a review manager
        RatingReviewManager manager = new RatingReviewManager();

        // Create a sample place
        Place place = new Place("Kochi", "MG Road", "Wheelchair accessible cafe", "Ramp, Toilet");

        manager.addReview(place, new RatingReview("user1", 4, "nice place"));
        manager.addReview(place, new RatingReview("user2", 5, "very good"));
        manager.addReview(place, new RatingReview("user3", 3, "decent"));
        manager.addReview(place, new RatingReview("user4", 2, "not great"));
        manager.addReview(place, new RatingReview("user5", 1, "worst place"));

        // Show reviews for the place
        manager.showReviews(place);

        // Show average rating
        System.out.println("Average Rating: " + manager.getAverageRating(place));
    }
}