package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private DatabaseHelper dbHelper;
    private FavoritesAdapter favoritesAdapter;
    private View noFavoritesTextView;
    private Button seeAllOnMapButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Initialize views
        noFavoritesTextView = view.findViewById(R.id.noFavoritesTextView);
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        seeAllOnMapButton = view.findViewById(R.id.seeAllOnMapButton);

        // Initialize database helper
        dbHelper = new DatabaseHelper(getContext());

        // Load favorites and setup the map button
        loadFavorites();

        // Set up "See All on Map" button
        seeAllOnMapButton.setOnClickListener(v -> showFavoritesOnMap());

        return view;
    }

    private void loadFavorites() {
        List<Restaurant> favoritesList = dbHelper.getAllFavorites();

        if (favoritesList != null && !favoritesList.isEmpty()) {
            favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            favoritesAdapter = new FavoritesAdapter(favoritesList, getContext());
            favoritesRecyclerView.setAdapter(favoritesAdapter);

            noFavoritesTextView.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);

            // Enable the "See All on Map" button
            seeAllOnMapButton.setEnabled(true);
        } else {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);

            // Disable the "See All on Map" button
            seeAllOnMapButton.setEnabled(false);
        }
    }

    private void showFavoritesOnMap() {
        List<Restaurant> favoritesList = dbHelper.getAllFavorites();

        if (favoritesList != null && !favoritesList.isEmpty()) {
            Intent intent = new Intent(getContext(), MapsActivity.class);

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
            Toast.makeText(getContext(), "No favorite spots to display on map.", Toast.LENGTH_SHORT).show();
        }
    }
}
