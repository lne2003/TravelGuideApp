package com.example.travelguide;

public class BusinessDetailsResponse {
    private String name;
    private String phone;
    private double rating;
    private Location location;

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