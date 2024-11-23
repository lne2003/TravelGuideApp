package com.example.travelguide;

import com.google.gson.annotations.SerializedName;

public class Business {

    private String id;
    private String name;
    private String imageUrl;
    private String address;
    private Location location;
    private double rating;



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public static class Location {
        private String address1;

        public String getAddress1() {
            return address1;
        }
    }
    public double getRating() { // Ensure this getter exists
        return rating;
    }
}