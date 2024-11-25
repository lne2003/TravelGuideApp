package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends BaseActivity {

    private RecyclerView favoritesRecyclerView;
    private TextView noFavoritesTextView;
    private DatabaseHelper dbHelper;
    private FavoritesAdapter favoritesAdapter;
    private Button seeAllOnMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize views
        noFavoritesTextView = findViewById(R.id.noFavoritesTextView);
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        seeAllOnMapButton = findViewById(R.id.seeAllOnMapButton);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Load favorites from the database
        loadFavorites();

        // Set up the "See All on Map" button
        seeAllOnMapButton.setOnClickListener(v -> showFavoritesOnMap());
    }

    private void loadFavorites() {
        // Get all favorite restaurants from the database
        List<Restaurant> favoritesList = dbHelper.getAllFavorites();

        if (favoritesList != null && !favoritesList.isEmpty()) {
            // Set up the RecyclerView
            favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            favoritesAdapter = new FavoritesAdapter(favoritesList, this);
            favoritesRecyclerView.setAdapter(favoritesAdapter);

            // Show the RecyclerView and hide the "No Favorites" text
            noFavoritesTextView.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);

            // Enable the "See All on Map" button
            seeAllOnMapButton.setEnabled(true);
        } else {
            // Show the "No Favorites" text and hide the RecyclerView
            noFavoritesTextView.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);

            // Disable the "See All on Map" button
            seeAllOnMapButton.setEnabled(false);
        }
    }

    private void showFavoritesOnMap() {
        // Fetch the favorites list
        List<Restaurant> favoritesList = dbHelper.getAllFavorites();
        if (favoritesList != null && !favoritesList.isEmpty()) {
            Intent intent = new Intent(FavoritesActivity.this, MapsActivity.class);

            ArrayList<Double> latitudes = new ArrayList<>();
            ArrayList<Double> longitudes = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();

            for (Restaurant restaurant : favoritesList) {
                latitudes.add(restaurant.getLatitude());
                longitudes.add(restaurant.getLongitude());
                names.add(restaurant.getName());
            }

            intent.putExtra("latitudes", latitudes);
            intent.putExtra("longitudes", longitudes);
            intent.putExtra("names", names);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No favorite spots to display on map.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload favorites whenever the activity is resumed
        loadFavorites();
    }

    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
