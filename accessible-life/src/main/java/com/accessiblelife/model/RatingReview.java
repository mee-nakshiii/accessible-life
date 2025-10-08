package com.accessiblelife.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class RatingReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rating;

    private String reviewText;

    public RatingReview() {
        // Default constructor for JPA
    }

    // Custom constructor for GUI usage
    public RatingReview(String reviewer, int rating, String comment) {
        this.user = new User();
        this.user.setUsername(reviewer);
        this.rating = rating;
        this.reviewText = comment;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}