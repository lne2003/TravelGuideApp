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
    private ImageView nightlifeImageView;
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
        String nightlifeImageUrl = getIntent().getStringExtra("nightlifeImageUrl"); // Add the URL for nightlife image
        String destinationDocumentId = getIntent().getStringExtra("documentId");

        // Load the destination image using Picasso
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(destinationImageView);
        }

        // Load the nightlife image using Picasso
        if (nightlifeImageUrl != null && !nightlifeImageUrl.isEmpty()) {
            Picasso.get().load(nightlifeImageUrl).into(nightlifeImageView);
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
        weatherButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, WeatherActivity.class);
            intent.putExtra("documentId", destinationDocumentId);
            startActivity(intent);
        });

        peoplesFavoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, RatingsPage.class);
            startActivity(intent);
        });
    }
}
