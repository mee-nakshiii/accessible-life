package com.accessiblelife;

public class RatingReview
{
    private String user;
    private int rating;
    private String review;

    public RatingReview(String user, int rating, String review)
    {
        this.user = user;
        this.rating = rating;
        this.review = review;
    }

    public String getUser()
    {
        return user;
    }

    public int getRating()
    {
        return rating;
    }

    public String getReview()
    {
        return review;
    }

    @Override
    public String toString()
    {
        return user + " rated " + rating + "/5 - " + review;
    }
}
