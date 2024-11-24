package com.example.travelguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<Restaurant> favoritesList;
    private Context context;
    private DatabaseHelper dbHelper;


    public FavoritesAdapter(List<Restaurant> favoritesList, Context context) {
        this.favoritesList = favoritesList;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
       // Get the user's email to use in removing favorites
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_favorite_restaurant, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Restaurant restaurant = favoritesList.get(position);

        holder.favoriteNameTextView.setText(restaurant.getName());
        Picasso.get().load(restaurant.getImageUrl()).into(holder.favoriteImageView);

        // Handle the delete button click event
        holder.deleteFavoriteButton.setOnClickListener(v -> {
            dbHelper.removeFavorite( restaurant.getName()); // Call removeFavorite with both userEmail and restaurant name
            favoritesList.remove(position);
            notifyItemRemoved(position);
        });

        // Navigate to MapsActivity when clicking on the card
        holder.itemView.setOnClickListener(v -> {
            Intent mapIntent = new Intent(context, MapsActivity.class);
            mapIntent.putExtra("latitude", restaurant.getLatitude());
            mapIntent.putExtra("longitude", restaurant.getLongitude());
            context.startActivity(mapIntent);
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView favoriteNameTextView;
        ImageView favoriteImageView;
        Button deleteFavoriteButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteNameTextView = itemView.findViewById(R.id.favoriteNameTextView);
            favoriteImageView = itemView.findViewById(R.id.favoriteImageView);
            deleteFavoriteButton = itemView.findViewById(R.id.deleteFavoriteButton);
        }
    }
}
