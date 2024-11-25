package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LeaveReviewActivity extends AppCompatActivity {

    private static final String TAG = "LeaveReviewActivity";

    private EditText reviewTextInput;
    private RatingBar ratingBar;
    private Button submitButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String documentId; // Destination document ID
    private String destinationName; // Current city name
    private String userFirstName; // User's first name from Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get intent data
        documentId = getIntent().getStringExtra("documentId");
        destinationName = getIntent().getStringExtra("destinationName");

        if (documentId == null || destinationName == null) {
            Toast.makeText(this, "Error: Missing destination data.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d(TAG, "Destination: " + destinationName);

        // Initialize UI components
        reviewTextInput = findViewById(R.id.reviewTextInput);
        ratingBar = findViewById(R.id.ratingBar);
        submitButton = findViewById(R.id.submitReviewButton);

        // Set up the Submit button listener
        submitButton.setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        String reviewText = reviewTextInput.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (reviewText.isEmpty() || rating == 0) {
            Toast.makeText(this, "Please provide a review and a rating.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the user's first name
        db.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    userFirstName = documentSnapshot.getString("firstName");
                    if (userFirstName == null) userFirstName = "Anonymous";

                    // Save the review to Firestore
                    saveReviewToFirestore(userFirstName, reviewText, rating);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to fetch user details.", e);
                    Toast.makeText(this, "Failed to fetch user details. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveReviewToFirestore(String firstName, String reviewText, float rating) {
        Map<String, Object> review = new HashMap<>();
        review.put("author", firstName);
        review.put("review", reviewText);
        review.put("rating", (int) rating); // Cast rating to int
        review.put("likes", 0); // Default likes to 0
        review.put("location", destinationName); // Use the current destination name

        // Save review under the destination's "reviews" subcollection
        db.collection("destinations").document(documentId).collection("reviews")
                .add(review)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after submission
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to submit review.", e);
                    Toast.makeText(this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }
}
