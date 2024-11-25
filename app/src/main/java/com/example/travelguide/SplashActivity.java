package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // Delay for 3 seconds before navigating to the next activity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginSignupPage.class);
            startActivity(intent);
            finish(); // Close SplashActivity so it can't be returned to
        }, 3000); // 3000ms = 3 seconds
    }
}
