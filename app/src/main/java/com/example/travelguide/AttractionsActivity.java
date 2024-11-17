package com.example.travelguide;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AttractionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        TextView attractionsTextView = findViewById(R.id.attractionsTextView);
        attractionsTextView.setText("Attractions information coming soon!");
    }
}
