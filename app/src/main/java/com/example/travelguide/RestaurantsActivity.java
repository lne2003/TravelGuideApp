package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantsActivity";
    private TextView restaurantsTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        restaurantsTextView = findViewById(R.id.restaurantsTextView);
        db = FirebaseFirestore.getInstance();

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

                                if (restaurantName != null && imageUrl != null && !imageUrl.isEmpty()) {
                                    // Create a CardView to hold restaurant name and image
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

                                    // Create a LinearLayout to hold name and image
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

                                    // Add TextView and ImageView to cardContent
                                    cardContent.addView(nameTextView);
                                    cardContent.addView(restaurantImageView);

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
                                } else {
                                    Log.w(TAG, "Missing name or imageUrl in restaurant document: " + document.getId());
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
