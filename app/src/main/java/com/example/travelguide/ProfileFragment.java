package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private TextView welcomeTextView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Check for offline mode
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.fragment_offline_profile, container, false);
        }

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize welcome text view
        welcomeTextView = rootView.findViewById(R.id.welcomeTextView);

        // Fetch and display user's first name
        fetchFirstName();

        // Initialize buttons and linear layouts
        LinearLayout profileSettingsButton = rootView.findViewById(R.id.profileSettingsLayout);
        LinearLayout privacySettingsButton = rootView.findViewById(R.id.privacySettingsLayout);
        LinearLayout notificationSettingsButton = rootView.findViewById(R.id.notificationSettingsLayout);
        Button logoutButton = rootView.findViewById(R.id.logoutButton);

        // Set click listeners for each button
        profileSettingsButton.setOnClickListener(v -> navigateToProfileSettings());
        privacySettingsButton.setOnClickListener(v -> navigateToPrivacySettings());
        notificationSettingsButton.setOnClickListener(v -> navigateToNotificationSettings());
        logoutButton.setOnClickListener(v -> performLogout());

        return rootView;
    }

    private void fetchFirstName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String firstName = documentSnapshot.getString("firstName");
                            if (firstName != null) {
                                welcomeTextView.setText(firstName);
                            } else {
                                welcomeTextView.setText("Welcome, User!");
                            }
                        } else {
                            welcomeTextView.setText("Welcome, User!");
                        }
                    })
                    .addOnFailureListener(e -> {
                        welcomeTextView.setText("Welcome, User!");
                        Toast.makeText(getContext(), "Failed to fetch user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            welcomeTextView.setText("Welcome, User!");
        }
    }

    private void navigateToProfileSettings() {
        Intent intent = new Intent(getActivity(), ProfileSettingsActivity.class);
        startActivity(intent);
    }

    private void navigateToPrivacySettings() {
        Intent intent = new Intent(getActivity(), PrivacySettingsActivity.class);
        startActivity(intent);
    }

    private void navigateToNotificationSettings() {
        Intent intent = new Intent(getActivity(), NotificationSettingsActivity.class);
        startActivity(intent);
    }

    private void performLogout() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
