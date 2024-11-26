package com.example.travelguide;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomePage extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check for network availability
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Intent offlineIntent = new Intent(this, OfflineActivity.class);
            startActivity(offlineIntent);
            finish();
            return;
        }

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Log the homepage_open event
        Bundle bundle = new Bundle();
        bundle.putString("screen_name", "HomePage");
        firebaseAnalytics.logEvent("homepage_open", bundle);

        // Fetch Firebase Cloud Messaging token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get the FCM registration token
                        String token = task.getResult();
                        Log.d(TAG, "FCM Registration Token: " + token);
                    }
                });

        // Set the content view for HomePage
        setContentView(R.layout.homepage);

        // Enable Firebase In-App Messaging
        FirebaseInAppMessaging.getInstance().setAutomaticDataCollectionEnabled(true);

        // Set up the "Let's Explore" button
        Button getStartedButton = findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check network status before navigating
                if (NetworkUtils.isNetworkAvailable(HomePage.this)) {
                    Intent intent = new Intent(HomePage.this, DestinationsActivity.class);
                    startActivity(intent);
                } else {
                    // Show error if offline
                    Toast.makeText(HomePage.this, "No internet connection. Please check your network and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
