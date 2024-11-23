package com.example.travelguide;

import com.google.gson.annotations.SerializedName;

public class NightlifeDetailsResponse {

    private String id;
    private String name;
    private String imageUrl;
    private String address;

    @SerializedName("location")
    private Location location;

    private String phone;
    private String price;
    private double rating;

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public static class Location {
        private String address1;

        public String getAddress1() {
            return address1;
        }
    }
}


