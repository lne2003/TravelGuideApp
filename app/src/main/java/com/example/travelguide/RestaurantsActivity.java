package com.example.travelguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantsActivity";
    private static final String RESTAURANTS_CACHE = "RestaurantsCache";

    private TextView restaurantsTextView;
    private FirebaseFirestore db;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private String documentId; // Unique identifier for the city
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the document ID for the city
        documentId = getIntent().getStringExtra("documentId");

        // Check if the user is offline and no cached data exists for the city
        if (!NetworkUtils.isNetworkAvailable(this) && !isDataCached()) {
            Toast.makeText(this, "No internet connection and no cached data available for this city.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_restaurants);

        restaurantsTextView = findViewById(R.id.restaurantsTextView);
        db = FirebaseFirestore.getInstance();
        dbHelper = new DatabaseHelper(this);

        // Load restaurants data
        if (NetworkUtils.isNetworkAvailable(this)) {
            fetchRestaurantsForCity(documentId);
        } else {
            loadCachedRestaurants();
        }
    }

    private void fetchRestaurantsForCity(String destinationDocumentId) {
        db.collection("destinations").document(destinationDocumentId).collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            LinearLayout parentLayout = findViewById(R.id.restaurantsLayout);
                            parentLayout.removeAllViews();

                            restaurantList.clear();

                            for (DocumentSnapshot document : querySnapshot) {
                                String restaurantName = document.getString("name");
                                String imageUrl = document.getString("imageUrl");
                                GeoPoint position = document.getGeoPoint("position");

                                if (restaurantName != null && imageUrl != null && position != null && !imageUrl.isEmpty()) {
                                    // Add restaurant to list
                                    Restaurant restaurant = new Restaurant(
                                            restaurantName,
                                            imageUrl,
                                            position.getLatitude(),
                                            position.getLongitude()
                                    );

                                    restaurantList.add(restaurant);

                                    // Create and display the card view with navigation functionality
                                    createRestaurantCard(parentLayout, restaurant);
                                } else {
                                    Log.w(TAG, "Missing name, imageUrl, or position in restaurant document: " + document.getId());
                                }
                            }

                            // Cache the restaurants for this city
                            cacheRestaurantsForCity();
                        } else {
                            Log.e(TAG, "No restaurant documents found in subcollection");
                            restaurantsTextView.setText("No restaurant information available.");
                        }
                    } else {
                        Log.e(TAG, "Failed to fetch restaurants data", task.getException());
                        restaurantsTextView.setText("Failed to load restaurant data.");
                    }
                });
    }

    private void createRestaurantCard(LinearLayout parentLayout, Restaurant restaurant) {
        // Create a CardView to hold restaurant details
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 16, 0, 16);
        cardView.setLayoutParams(cardParams);
        cardView.setRadius(15);
        cardView.setElevation(8);
        cardView.setPadding(16, 16, 16, 16);

        // Set up click listener for navigation to MapsActivity
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantsActivity.this, MapsActivity.class);
            intent.putExtra("latitude", restaurant.getLatitude());
            intent.putExtra("longitude", restaurant.getLongitude());
            intent.putExtra("restaurantName", restaurant.getName());
            startActivity(intent);
        });

        // Create and add content to the CardView
        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);
        cardContent.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add restaurant name
        TextView nameTextView = new TextView(this);
        nameTextView.setText(restaurant.getName());
        nameTextView.setTextSize(18);
        nameTextView.setPadding(0, 0, 0, 8);

        // Add restaurant image
        ImageView restaurantImageView = new ImageView(this);
        restaurantImageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                400
        ));
        restaurantImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get()
                .load(restaurant.getImageUrl())
                .resize(600, 400)
                .centerCrop()
                .into(restaurantImageView);

        // Create "Add to Favorites" button
        Button addToFavoritesButton = new Button(this);
        addToFavoritesButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        addToFavoritesButton.setText("Add to Favorites");

        // Set OnClickListener for "Add to Favorites" button
        addToFavoritesButton.setOnClickListener(v -> {
            dbHelper.addFavorite(restaurant);
            Intent intent = new Intent(RestaurantsActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        cardContent.addView(nameTextView);
        cardContent.addView(restaurantImageView);
        cardContent.addView(addToFavoritesButton);

        cardView.addView(cardContent);
        parentLayout.addView(cardView);
    }

    private void cacheRestaurantsForCity() {
        SharedPreferences prefs = getSharedPreferences(RESTAURANTS_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(restaurantList); // Serialize list to JSON
        editor.putString(documentId, json); // Associate cache with city ID
        editor.apply();
        Log.d(TAG, "Restaurants cached for city: " + documentId);
    }

    private void loadCachedRestaurants() {
        SharedPreferences prefs = getSharedPreferences(RESTAURANTS_CACHE, Context.MODE_PRIVATE);
        String json = prefs.getString(documentId, null); // Load cache for the specific city
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Restaurant>>() {}.getType();
            restaurantList = gson.fromJson(json, type); // Deserialize JSON to list

            LinearLayout parentLayout = findViewById(R.id.restaurantsLayout);
            parentLayout.removeAllViews();

            for (Restaurant restaurant : restaurantList) {
                createRestaurantCard(parentLayout, restaurant);
            }
            Toast.makeText(this, "Loaded cached restaurants for this city.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No cached restaurants available for this city.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDataCached() {
        SharedPreferences prefs = getSharedPreferences(RESTAURANTS_CACHE, Context.MODE_PRIVATE);
        return prefs.contains(documentId); // Check if cache exists for this city
    }
}
