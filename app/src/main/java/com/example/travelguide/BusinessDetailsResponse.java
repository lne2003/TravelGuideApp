package com.example.travelguide;

import com.google.gson.annotations.SerializedName;

public class BusinessDetailsResponse {
    private String name;
    private String phone;
    private double rating;
    private Location location;

    @SerializedName("image_url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public double getRating() {
        return rating;
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
}