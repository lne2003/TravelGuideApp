package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RatingsPage extends BaseActivity {

    private static final String TAG = "RatingsPage";
    private RecyclerView recyclerView;
    private RatingsAdapter adapter;
    private FirebaseFirestore db;
    private List<RatingItem> ratingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        // Initialize RecyclerView and Firebase Firestore
        recyclerView = findViewById(R.id.ratingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        ratingList = new ArrayList<>();

        // Attach an empty adapter initially
        adapter = new RatingsAdapter(ratingList);
        recyclerView.setAdapter(adapter);

        // Get the destination document ID from the intent
        String documentId = getIntent().getStringExtra("documentId");
        String destinationName = getIntent().getStringExtra("destinationName");
        if (documentId == null || documentId.isEmpty()) {
            Log.e(TAG, "Document ID is missing!");
            Toast.makeText(this, "Error: Destination data not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d(TAG, "Received Document ID: " + documentId);

        // Fetch reviews for the destination
        fetchReviewsForDestination(documentId);
        Button leaveReviewButton = findViewById(R.id.leaveReviewButton);
        leaveReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LeaveReviewActivity.class);
            intent.putExtra("documentId", documentId); // Pass the destination's document ID
            intent.putExtra("destinationName", destinationName);
            startActivity(intent);
        });

    }


    private void fetchReviewsForDestination(String documentId) {
        db.collection("destinations").document(documentId).collection("reviews")
                .get()
                .addOnSuccessListener(this::handleReviewsFetched)
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to fetch reviews for destination " + documentId, e);
                    Toast.makeText(this, "Failed to load reviews.", Toast.LENGTH_SHORT).show();
                });
    }

    private void handleReviewsFetched(QuerySnapshot querySnapshot) {
        if (querySnapshot == null || querySnapshot.isEmpty()) {
            Log.e(TAG, "No reviews found for this destination.");
            Toast.makeText(this, "No reviews available.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear the existing list and add new data
        ratingList.clear();
        querySnapshot.forEach(documentSnapshot -> {
            String name = documentSnapshot.getString("author");
            String location = documentSnapshot.getString("location");
            String review = documentSnapshot.getString("review");
            long likes = documentSnapshot.getLong("likes") != null ? documentSnapshot.getLong("likes") : 0;
            String photoUrl = documentSnapshot.getString("photo");
            long rating = documentSnapshot.getLong("rating") != null ? documentSnapshot.getLong("rating") : 0; // Fetch rating

            ratingList.add(new RatingItem(
                    name != null ? name : "Anonymous",
                    location != null ? location : "Unknown Location",
                    review != null ? review : "No review text provided.",
                    (int) likes,
                    photoUrl,
                    (int) rating // Pass the rating
            ));
        });

        // Notify the adapter about data changes
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
