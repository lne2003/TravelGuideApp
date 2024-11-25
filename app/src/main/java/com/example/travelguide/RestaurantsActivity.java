package com.example.travelguide;

import android.content.Intent;
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
import com.squareup.picasso.Picasso;

public class RestaurantsActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantsActivity";
    private TextView restaurantsTextView;
    private FirebaseFirestore db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection and no cached data available.", Toast.LENGTH_SHORT).show();
            finish(); // Exit the activity if no data is available
            return;
        }
        setContentView(R.layout.activity_restaurants);

        restaurantsTextView = findViewById(R.id.restaurantsTextView);
        db = FirebaseFirestore.getInstance();
        dbHelper = new DatabaseHelper(this);
        String documentId = getIntent().getStringExtra("documentId");

        if (documentId != null) {
            fetchRestaurants(documentId);
        } else {
            Log.e(TAG, "No documentId passed to RestaurantsActivity");
        }
    }



    private void fetchRestaurants(String destinationDocumentId) {
        db.collection("destinations").document(destinationDocumentId).collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            LinearLayout parentLayout = findViewById(R.id.restaurantsLayout);
                            parentLayout.removeAllViews(); // Clear any previous entries

                            for (DocumentSnapshot document : querySnapshot) {
                                String restaurantName = document.getString("name");
                                String imageUrl = document.getString("imageUrl");
                                GeoPoint position = document.getGeoPoint("position");

                                if (restaurantName != null && imageUrl != null && position != null && !imageUrl.isEmpty()) {
                                    // Create a CardView to hold restaurant name, image, and button
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

                                    // Create a LinearLayout to hold name, image, and button
                                    LinearLayout cardContent = new LinearLayout(this);
                                    cardContent.setOrientation(LinearLayout.VERTICAL);
                                    cardContent.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));

                                    // Create a TextView for the restaurant name
                                    TextView nameTextView = new TextView(this);
                                    nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));
                                    nameTextView.setText(restaurantName);
                                    nameTextView.setTextSize(18);
                                    nameTextView.setPadding(0, 0, 0, 8);

                                    // Create an ImageView for the restaurant image
                                    ImageView restaurantImageView = new ImageView(this);
                                    restaurantImageView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            400 // Height of the image
                                    ));
                                    restaurantImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                                    // Create a "Add to Favorites" button
                                    Button addToFavoritesButton = new Button(this);
                                    addToFavoritesButton.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    ));
                                    addToFavoritesButton.setText("Add to Favorites");
                                    addToFavoritesButton.setPadding(0, 16, 0, 0);

                                    // Add TextView, ImageView, and Button to cardContent
                                    cardContent.addView(nameTextView);
                                    cardContent.addView(restaurantImageView);
                                    cardContent.addView(addToFavoritesButton);

                                    // Add cardContent to the cardView
                                    cardView.addView(cardContent);

                                    // Add the cardView to the parent layout
                                    parentLayout.addView(cardView);

                                    // Load the image using Picasso
                                    Picasso.get()
                                            .load(imageUrl)
                                            .resize(600, 400)
                                            .centerCrop()
                                            .into(restaurantImageView);

                                    // Set OnClickListener for navigating to MapsActivity
                                    cardView.setOnClickListener(v -> {
                                        Intent mapIntent = new Intent(RestaurantsActivity.this, MapsActivity.class);
                                        mapIntent.putExtra("latitude", position.getLatitude());
                                        mapIntent.putExtra("longitude", position.getLongitude());
                                        mapIntent.putExtra("restaurantName", restaurantName);
                                        startActivity(mapIntent);
                                    });

                                    // Set OnClickListener for "Add to Favorites" button
                                    addToFavoritesButton.setOnClickListener(v -> {
                                        DatabaseHelper dbHelper = new DatabaseHelper(RestaurantsActivity.this);
                                        Restaurant restaurant = new Restaurant(
                                                restaurantName,
                                                imageUrl,
                                                position.getLatitude(),
                                                position.getLongitude()
                                        );
                                        dbHelper.addFavorite(restaurant);
                                        Intent intent = new Intent(RestaurantsActivity.this, FavoritesActivity.class);
                                        startActivity(intent);
                                    });

                                } else {
                                    Log.w(TAG, "Missing name, imageUrl, or position in restaurant document: " + document.getId());
                                }
                            }
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

}