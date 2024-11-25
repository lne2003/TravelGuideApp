package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logoImage);
        TextView loadingText = findViewById(R.id.loadingText);

        // Scale animation for the logo
        Animation scaleAnimation = new ScaleAnimation(
                0.7f, 1f, // Start and end scale for X-axis
                0.7f, 1f, // Start and end scale for Y-axis
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X-axis (center)
                Animation.RELATIVE_TO_SELF, 0.5f  // Pivot Y-axis (center)
        );
        scaleAnimation.setDuration(1000); // Animation duration in milliseconds
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        logoImage.startAnimation(scaleAnimation);

        // Alpha animation for the loading text
        Animation alphaAnimation = new AlphaAnimation(0.3f, 1f); // Fading in and out
        alphaAnimation.setDuration(1000); // Animation duration in milliseconds
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        loadingText.startAnimation(alphaAnimation);

        // Delay for 3 seconds before navigating to the next activity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginSignupPage.class);
            startActivity(intent);
            finish(); // Close SplashActivity so it can't be returned to
        }, 3000); // 3000ms = 3 seconds
    }
}