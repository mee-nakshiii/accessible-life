package com.accessiblelife.model;

public class Review {
    private long reviewId;
    private long userId;
    private long placeId;
    private int rating;
    private String reviewText;

    public Review(long userId, long placeId, int rating, String reviewText) {
        this.userId = userId;
        this.placeId = placeId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public long getUserId() {
        return userId;
    }

    public long getPlaceId() {
        return placeId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getComment() {
        return reviewText;
    }
}