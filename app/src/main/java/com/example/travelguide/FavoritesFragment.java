package com.example.travelguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private DatabaseHelper dbHelper;
    private FavoritesAdapter favoritesAdapter;
    private View noFavoritesTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Initialize views
        noFavoritesTextView = view.findViewById(R.id.noFavoritesTextView);
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);

        // Initialize database helper
        dbHelper = new DatabaseHelper(getContext());

        // Load favorites
        loadFavorites();

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
        } else {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);
        }
    }
}
