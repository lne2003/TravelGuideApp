package com.example.travelguide;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business {
    private String name;
    private String imageUrl;
    private double rating;

    @SerializedName("location")
    private Location location;

    @SerializedName("categories")
    private List<Category> categories;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public Location getLocation() {
        return location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public static class Location {
        private String address1;

        public String getAddress1() {
            return address1;
        }
    }

    public static class Category {
        private String title;

        public String getTitle() {
            return title;
        }
    }
}
