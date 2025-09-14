package com.accessiblelife.ratingreview;

import com.accessiblelife.model.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingReviewManager
{
    private final Map<Place, List<RatingReview>> placeReviews = new HashMap<>();

    public void addReview(Place place, RatingReview review)
    {
        if (place == null || review == null)
        {
            System.out.println("Cannot add review: place or review is null.");
            return;
        }

        placeReviews.computeIfAbsent(place, k -> new ArrayList<>()).add(review);
        System.out.println("Review added for: " + place.getShortDescription());
    }

    public void showReviews(Place place)
    {
        if (place == null)
        {
            System.out.println("Invalid place.");
            return;
        }

        List<RatingReview> reviews = placeReviews.get(place);
        if (reviews == null || reviews.isEmpty())
        {
            System.out.println("No reviews yet for: " + place.getShortDescription());
        }
        else
        {
            System.out.println("Reviews for " + place.getShortDescription() + ":");
            for (RatingReview review : reviews)
            {
                System.out.println(" - " + review);
            }
        }
    }

    public double getAverageRating(Place place)
    {
        List<RatingReview> reviews = placeReviews.get(place);
        if (reviews == null || reviews.isEmpty()) return 0.0;

        double sum = 0;
        for (RatingReview review : reviews)
        {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }

    public void showAllReviews()
    {
        if (placeReviews.isEmpty())
        {
            System.out.println("No reviews available.");
            return;
        }

        for (Map.Entry<Place, List<RatingReview>> entry : placeReviews.entrySet())
        {
            Place place = entry.getKey();
            List<RatingReview> reviews = entry.getValue();

            System.out.println("Reviews for " + place.getShortDescription() + ":");
            for (RatingReview review : reviews)
            {
                System.out.println("   - " + review);
            }
            System.out.printf("Average Rating: %.2f%n", getAverageRating(place));
            System.out.println("-----------------------------");
        }
    }

    public List<RatingReview> getReviewsForPlace(Place place)
    {
        return placeReviews.getOrDefault(place, new ArrayList<>());
    }
}