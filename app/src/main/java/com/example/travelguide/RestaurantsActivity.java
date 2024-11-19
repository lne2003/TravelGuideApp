package com.example.travelguide;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    private TextView restaurantsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        restaurantsTextView = findViewById(R.id.restaurantsTextView);

        // Get the list of restaurants from the Intent
        List<String> restaurants = getIntent().getStringArrayListExtra("restaurants");

        // Display the list of restaurants
        if (restaurants != null && !restaurants.isEmpty()) {
            StringBuilder restaurantsList = new StringBuilder("Restaurants:\n");
            for (String restaurant : restaurants) {
                restaurantsList.append("- ").append(restaurant).append("\n");
            }
            restaurantsTextView.setText(restaurantsList.toString());
        } else {
            restaurantsTextView.setText("No restaurant information available.");
        }
    }
}
