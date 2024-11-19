package com.example.travelguide;

public class RatingItem {
    private String name, location, review;
    private int likes;
    private int photoResId; // Resource ID for the avatar photo

    public RatingItem(String name, String location, String review, int likes, int photoResId) {
        this.name = name;
        this.location = location;
        this.review = review;
        this.likes = likes;
        this.photoResId = photoResId;
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

    public int getPhotoResId() {
        return photoResId;
    }
}
