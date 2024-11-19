package com.example.travelguide;

import com.google.firebase.firestore.GeoPoint;
import java.util.List;

public class Destination {
    private String name;
    private String description;
    private String imageUrl;
    private GeoPoint position;
    private List<String> restaurants;
    private String weather;
    private String documentId;

    // Full constructor
    public Destination(String name, String description, String imageUrl, GeoPoint position, List<String> restaurants, String weather, String documentId) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.position = position;
        this.restaurants = restaurants;
        this.weather = weather;
        this.documentId = documentId;
    }

    // Constructor with fewer parameters
    public Destination(String name, String description, String imageUrl, String documentId) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.documentId = documentId;
    }

    // Getter methods
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public GeoPoint getPosition() { return position; }
    public List<String> getRestaurants() { return restaurants; }
    public String getWeather() { return weather; }
    public String getDocumentId() { return documentId; }
}
