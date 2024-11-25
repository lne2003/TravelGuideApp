package com.example.travelguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginSignupPage extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Button loginButton, signupButton;

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

        // Set up the layout
        setContentView(R.layout.login_signup);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color));

        // Initialize the buttons
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        // Set click listener for the Login button
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginSignupPage.this, LoginPage.class);
            startActivity(intent);
        });

        // Set click listener for the Sign Up button
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginSignupPage.this, SignUpPage.class);
            startActivity(intent);
        });

        // Request location permissions
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        // Check if location permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Show the permission dialog
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Check if the user granted the location permission
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied. Some features may not work.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
