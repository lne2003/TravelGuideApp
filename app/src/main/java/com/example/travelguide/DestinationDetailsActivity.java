package com.example.travelguide;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DestinationDetailsActivity extends AppCompatActivity {

    private ImageView destinationImageView;
    private Button restaurantsButton;
    private Button newsButton;
    private Button weatherButton;
    private Button peoplesFavoriteButton;
    private Button nightlifeButton;
    private static final String NEWS_API_KEY = "Qn8rqwLQLlgL7iLzVjuf4UC8Q2UvzhIK";
    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_details);

        // Initialize views
        destinationImageView = findViewById(R.id.destinationImageView);
        restaurantsButton = findViewById(R.id.restaurantsButton);

        weatherButton = findViewById(R.id.weatherButton);
        peoplesFavoriteButton = findViewById(R.id.peoplesFavoriteButton);
        nightlifeButton = findViewById(R.id.nightlifeButton);

        // Get data from the Intent
        String imageUrl = getIntent().getStringExtra("imageUrl");

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
// Set click listener for attractions button

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
        nightlifeButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, NightlifeActivity.class);
            intent.putExtra("cityName", destinationName); // Pass destination name
            startActivity(intent);
        });


        peoplesFavoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, RatingsPage.class);
            startActivity(intent);
        });
    }
}
