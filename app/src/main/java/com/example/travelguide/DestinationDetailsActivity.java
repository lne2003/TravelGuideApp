package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class DestinationDetailsActivity extends AppCompatActivity {

    private ImageView destinationImageView;
    private Button restaurantsButton;
    private Button nightlifeButton;
    private Button weatherButton;
    private Button peoplesFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_details);

        // Initialize views
        destinationImageView = findViewById(R.id.destinationImageView);
        restaurantsButton = findViewById(R.id.restaurantsButton);
        nightlifeButton = findViewById(R.id.nightlifeButton);
        weatherButton = findViewById(R.id.weatherButton);
        peoplesFavoriteButton = findViewById(R.id.peoplesFavoriteButton);

        // Get data from the Intent
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String nightlifeImageUrl = getIntent().getStringExtra("nightlifeImageUrl");
        String destinationDocumentId = getIntent().getStringExtra("documentId");
        String destinationName = getIntent().getStringExtra("name"); // Get the destination name from the Intent

        // Log the destination name to debug the issue
        if (destinationName != null) {
            System.out.println("Destination name passed is: " + destinationName);
        } else {
            System.out.println("Destination name is null");
        }

        // Load the destination image using Picasso
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(destinationImageView);
        }

        // Set click listener for restaurants button
        restaurantsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, RestaurantsActivity.class);
            intent.putExtra("documentId", destinationDocumentId);
            startActivity(intent);
        });

        // Set click listener for nightlife button
        nightlifeButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, NightlifeActivity.class);
            intent.putExtra("documentId", destinationDocumentId);
            startActivity(intent);
        });

        // Set click listener for weather button to view the weather
        weatherButton.setOnClickListener(v -> {
            if (destinationName != null) {
                Intent intent = new Intent(DestinationDetailsActivity.this, WeatherActivity.class);
                intent.putExtra("destinationName", destinationName); // Pass destination name to get weather data
                startActivity(intent);
            } else {
                System.out.println("Destination name is null when trying to start WeatherActivity");
            }
        });


        peoplesFavoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, RatingsPage.class);
            startActivity(intent);
        });
    }
}
