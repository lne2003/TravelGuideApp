package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this matches your layout file name

        // Button to navigate to Ratings Page
        Button goToRatingsButton = findViewById(R.id.goToRatingsButton); // Ensure the ID matches the layout
        goToRatingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ratingsIntent = new Intent(MainActivity.this, RatingsPage.class);
                startActivity(ratingsIntent);
            }
        });
    }
}
