package com.accessiblelife;

import java.util.ArrayList;
import java.util.List;


public class RatingReviewManager
{
    private final List<RatingReview> reviews = new ArrayList<>();

    public void addReview(RatingReview review)
    {
        reviews.add(review);
    }

    public void showReviews()
    {
        if (reviews.isEmpty())
        {
            System.out.println("No reviews yet.");
        } else
        {
            for (RatingReview review : reviews)
            {
                System.out.println(review);
            }
        }
    }

    public double getAverageRating()
    {
        if (reviews.isEmpty()) return 0.0;

        double sum = 0;
        for (RatingReview review : reviews)
        {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }
}
