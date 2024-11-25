package com.example.travelguide;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!NetworkUtils.isNetworkAvailable(this)) {
            Intent offlineIntent = new Intent(this, OfflineActivity.class);
            startActivity(offlineIntent);
            finish();
            return;
        }


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

                        // TODO: do smth when opening the push notification
                        // for now we just toast the firebase cloud messaging registration code
                        String msg = "FCM Registration token: " + token;
                        Log.d(TAG, msg);
//                        Toast.makeText(HomePage.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        setContentView(R.layout.homepage);



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
