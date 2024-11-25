package com.example.travelguide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RatingsPage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        RecyclerView recyclerView = findViewById(R.id.ratingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add unique photos and data for each rating
        List<RatingItem> ratings = new ArrayList<>();
        ratings.add(new RatingItem(
                "Sotiria, 21 years old",
                "BALI",
                "If you're looking for a cozy spot with incredible food, Kabana Ubud offers a perfect blend of flavorful dishes and a warm, inviting atmosphere.",
                10,
                R.drawable.sotiria)); // Unique photo

        ratings.add(new RatingItem(
                "Tasos, 48 years old",
                "BALI",
                "Kau Kau Restaurant offers a perfect blend of traditional Chinese dishes and a welcoming atmosphere.",
                3,
                R.drawable.tasos)); // Unique photo

        ratings.add(new RatingItem(
                "Niki, 25 years old",
                "BALI",
                "This is my favorite thing I did in Bali: visiting Banyumala Waterfall. It's breathtaking!",
                24,
                R.drawable.niki)); // Unique photo

        RatingsAdapter adapter = new RatingsAdapter(ratings);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
