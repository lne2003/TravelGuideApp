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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_details);

        destinationImageView = findViewById(R.id.destinationImageView);
        restaurantsButton = findViewById(R.id.restaurantsButton);

        // Get data from the Intent
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String destinationDocumentId = getIntent().getStringExtra("documentId");

        // Load the image using Picasso
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(destinationImageView);
        }

        // Set click listener for restaurants button
        restaurantsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DestinationDetailsActivity.this, RestaurantsActivity.class);
            intent.putExtra("documentId", destinationDocumentId);
            startActivity(intent);
        });
    }
}
