package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Initialize the Get Started button
        Button getStartedButton = findViewById(R.id.getStartedButton);

        // Set click listener for the Get Started button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Destinations activity
                Intent intent = new Intent(HomePage.this, DestinationsActivity.class);
                startActivity(intent);
            }
        });
    }
}
