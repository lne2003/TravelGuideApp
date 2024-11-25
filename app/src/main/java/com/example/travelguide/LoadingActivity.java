package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private TextView loadingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingMessage = findViewById(R.id.loadingMessage);

        // Simulate loading time of 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if the internet is available
                if (NetworkUtils.isNetworkAvailable(LoadingActivity.this)) {
                    // If internet is available, proceed to NightlifeActivity
                    String cityName = getIntent().getStringExtra("cityName");
                    Intent intent = new Intent(LoadingActivity.this, NightlifeActivity.class);
                    intent.putExtra("cityName", cityName);
                    startActivity(intent);
                    finish();
                } else {
                    // If no internet, show the error page in NightlifeActivity
                    Intent intent = new Intent(LoadingActivity.this, NightlifeActivity.class);
                    intent.putExtra("showError", true); // Flag to show the error message
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);  // Show the loading screen for 3 seconds
    }
}
