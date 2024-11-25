package com.example.travelguide;

public class RatingItem {
    private String name;
    private String location;
    private String review;
    private int likes;
    private String photoUrl;
    private int rating; // New field for rating

    public RatingItem(String name, String location, String review, int likes, String photoUrl, int rating) {
        this.name = name;
        this.location = location;
        this.review = review;
        this.likes = likes;
        this.photoUrl = photoUrl;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getReview() {
        return review;
    }

    public int getLikes() {
        return likes;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getRating() {
        return rating; // Getter for rating
    }
}
