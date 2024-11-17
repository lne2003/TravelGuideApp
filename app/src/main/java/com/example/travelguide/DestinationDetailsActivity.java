package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class DestinationDetailsActivity extends AppCompatActivity {

    private ImageView destinationImageView;
    private Button restaurantsButton, nightlifeButton, peoplesFavoriteButton, attractionsButton, weatherButton;
    private String weather;
    private List<String> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_details);

        // Initialize views
        destinationImageView = findViewById(R.id.destinationImageView);
        restaurantsButton = findViewById(R.id.restaurantsButton);
        nightlifeButton = findViewById(R.id.nightlifeButton);
        peoplesFavoriteButton = findViewById(R.id.peoplesFavoriteButton);
        attractionsButton = findViewById(R.id.attractionsButton);
        weatherButton = findViewById(R.id.weatherButton);

        // Get data from the Intent
        String imageUrl = getIntent().getStringExtra("imageUrl");
        restaurants = getIntent().getStringArrayListExtra("restaurants");
        weather = getIntent().getStringExtra("weather");

        // Load the image using Picasso
        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(destinationImageView);
        }

        // Set click listeners for each button to navigate to the corresponding activity
        restaurantsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, RestaurantsActivity.class);
            intent.putStringArrayListExtra("restaurants", new ArrayList<>(restaurants));
            startActivity(intent);
        });

        nightlifeButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, NightlifeActivity.class);
            startActivity(intent);
        });

        peoplesFavoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, PeoplesFavoriteActivity.class);
            startActivity(intent);
        });

        attractionsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, AttractionsActivity.class);
            startActivity(intent);
        });

        weatherButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, WeatherActivity.class);
            intent.putExtra("weather", weather);
            startActivity(intent);
        });
    }
}
