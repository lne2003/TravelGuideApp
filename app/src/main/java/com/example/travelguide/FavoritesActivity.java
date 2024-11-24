package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private TextView noFavoritesTextView;
    private DatabaseHelper dbHelper;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize views
        noFavoritesTextView = findViewById(R.id.noFavoritesTextView);
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Load favorites from the database
        loadFavorites();
    }

    private void loadFavorites() {
        // Get all favorite restaurants from the database
        List<Restaurant> favoritesList = dbHelper.getAllFavorites();

        if (favoritesList != null && !favoritesList.isEmpty()) {
            // Set up the RecyclerView
            favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            // Pass context along with the favorites list
            favoritesAdapter = new FavoritesAdapter(favoritesList, this);
            favoritesRecyclerView.setAdapter(favoritesAdapter);

            // Make RecyclerView visible and hide the noFavoritesTextView
            noFavoritesTextView.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);
        } else {
            // Show the noFavoritesTextView and hide the RecyclerView
            noFavoritesTextView.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);
        }
    }
}
