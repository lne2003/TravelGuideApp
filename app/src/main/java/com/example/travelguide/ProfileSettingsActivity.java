package com.example.travelguide;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileSettingsActivity extends BaseActivity {

    private TextView nameTextView, emailTextView, historyTextView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        historyTextView = findViewById(R.id.historyTextView);

        fetchProfileData();
    }

    private void fetchProfileData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Fetch user details from Firestore
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("firstName");
                            String email = user.getEmail();

                            nameTextView.setText(name != null ? name : "Name not set");
                            emailTextView.setText(email != null ? email : "Email not set");

                            // Fetch user history (Example)
                            historyTextView.setText("1. Viewed: Paris\n2. Saved: Eiffel Tower");
                        } else {
                            Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to fetch profile data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
