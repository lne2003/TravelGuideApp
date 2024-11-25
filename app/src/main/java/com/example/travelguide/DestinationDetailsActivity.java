package com.example.travelguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

public class DestinationDetailsActivity extends BaseActivity {

    private static final String TAG = "DestinationDetails";
    private static final String PREFS_NAME = "DestinationCache";
    private static final String RESTAURANTS_CACHE = "RestaurantsCache";
    private static final String NIGHTLIFE_CACHE = "NightlifeCache";

    private ImageView destinationImageView;
    private Button restaurantsButton;
    private Button weatherButton;
    private Button peoplesFavoriteButton;
    private Button nightlifeButton;

    private String destinationName;
    private String destinationImageUrl;
    private String destinationDocumentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_details);
        // Initialize Firebase Messaging and retrieve the device FCM token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("DestinationDetails", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get the FCM token
                    String token = task.getResult();
                    Log.d("DestinationDetails", "FCM Token: " + token);


                });


        // Initialize views
        destinationImageView = findViewById(R.id.destinationImageView);
        restaurantsButton = findViewById(R.id.restaurantsButton);
        weatherButton = findViewById(R.id.weatherButton);
        peoplesFavoriteButton = findViewById(R.id.peoplesFavoriteButton);
        nightlifeButton = findViewById(R.id.nightlifeButton);

        // Retrieve intent data
        destinationImageUrl = getIntent().getStringExtra("imageUrl");
        destinationDocumentId = getIntent().getStringExtra("documentId");
        destinationName = getIntent().getStringExtra("name");

        // Log for debugging
        if (destinationName != null) {
            Log.d(TAG, "Destination name passed is: " + destinationName);
        } else {
            Log.d(TAG, "Destination name is null");
        }

        // Load the destination image
        if (destinationImageUrl != null && !destinationImageUrl.isEmpty()) {
            Picasso.get().load(destinationImageUrl).into(destinationImageView);
        }

        // Cache details if online, load from cache if offline
        if (NetworkUtils.isNetworkAvailable(this)) {
            cacheDestinationDetails();
            loadDestinationDetails();
        } else {
            loadCachedDestinationDetails();
        }

        // Set listeners for buttons
        setUpButtonListeners();
    }

    private void setUpButtonListeners() {
        restaurantsButton.setOnClickListener(v -> {
            if (NetworkUtils.isNetworkAvailable(this) || isDataCached(RESTAURANTS_CACHE, destinationDocumentId)) {
                Intent intent = new Intent(DestinationDetailsActivity.this, RestaurantsActivity.class);
                intent.putExtra("documentId", destinationDocumentId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No internet connection and no cached restaurant data available.", Toast.LENGTH_SHORT).show();
            }
        });

        weatherButton.setOnClickListener(v -> {
            if (NetworkUtils.isNetworkAvailable(this)) {
                Intent intent = new Intent(DestinationDetailsActivity.this, WeatherActivity.class);
                intent.putExtra("destinationName", destinationName);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No internet connection and no cached weather data available.", Toast.LENGTH_SHORT).show();
            }
        });

        nightlifeButton.setOnClickListener(v -> {
            if (NetworkUtils.isNetworkAvailable(this) || isDataCached(NIGHTLIFE_CACHE, destinationName)) {
                Intent intent = new Intent(DestinationDetailsActivity.this, NightlifeActivity.class);
                intent.putExtra("cityName", destinationName);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No internet connection and no cached nightlife data available.", Toast.LENGTH_SHORT).show();
            }
        });

        peoplesFavoriteButton.setOnClickListener(v -> {
            if (NetworkUtils.isNetworkAvailable(this)) {
                Intent intent = new Intent(DestinationDetailsActivity.this, RatingsPage.class);
                intent.putExtra("destinationName", destinationName); // Pass the destination name
                intent.putExtra("documentId", destinationDocumentId); // Pass the document ID
                startActivity(intent);
            } else {
                Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cacheDestinationDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("destinationName", destinationName);
        editor.putString("destinationImageUrl", destinationImageUrl);
        editor.apply();
        Log.d(TAG, "Destination details cached.");
    }

    private void loadCachedDestinationDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        destinationName = sharedPreferences.getString("destinationName", null);
        destinationImageUrl = sharedPreferences.getString("destinationImageUrl", null);

        if (destinationName != null && destinationImageUrl != null) {
            Picasso.get().load(destinationImageUrl).into(destinationImageView);
            Log.d(TAG, "Loaded cached destination details.");
        } else {
            Toast.makeText(this, "No cached destination details available.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDataCached(String cacheKey, String identifier) {
        SharedPreferences prefs = getSharedPreferences(cacheKey, Context.MODE_PRIVATE);
        String cachedData = prefs.getString(identifier, null);
        return cachedData != null;
    }

    private void loadDestinationDetails() {
        if (destinationName != null) {
            Log.d(TAG, "Destination name: " + destinationName);
        } else {
            Log.d(TAG, "Destination name is null");
        }

        if (destinationImageUrl != null && !destinationImageUrl.isEmpty()) {
            Picasso.get()
                    .load(destinationImageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(destinationImageView);
        }
    }
    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
