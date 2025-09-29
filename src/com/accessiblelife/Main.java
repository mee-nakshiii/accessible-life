package com.accessiblelife;

public class Main
{
    public static void main(String[] args)
    {
        RatingReviewManager manager = new RatingReviewManager();

        // Adding some sample reviews
        manager.addReview(new RatingReview("Alice", 5, "Amazing product!"));
        manager.addReview(new RatingReview("Bob", 4, "Very good, but could be improved."));
        manager.addReview(new RatingReview("Charlie", 3, "It’s okay, average experience."));

        // Show all reviews
        manager.showReviews();

        // Show average rating
        System.out.println("Average Rating: " + manager.getAverageRating());
    }
}
