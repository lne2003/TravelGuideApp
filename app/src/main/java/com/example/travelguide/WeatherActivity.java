package com.example.travelguide;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        String weather = getIntent().getStringExtra("weather");
        TextView weatherTextView = findViewById(R.id.weatherTextView);

        if (weather != null) {
            weatherTextView.setText("Weather: " + weather);
        } else {
            weatherTextView.setText("Weather information not available.");
        }
    }
}
