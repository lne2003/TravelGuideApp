package com.example.travelguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NightlifeAdapter extends RecyclerView.Adapter<NightlifeAdapter.ViewHolder> {
    private List<Business> nightlifeList;

    public NightlifeAdapter(List<Business> nightlifeList) {
        this.nightlifeList = nightlifeList;
    }

    public void updateData(List<Business> newNightlifeList) {
        this.nightlifeList = newNightlifeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nightlife, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business nightlife = nightlifeList.get(position);

        // Set basic details
        holder.nameTextView.setText(nightlife.getName());
        holder.addressTextView.setText(nightlife.getLocation().getAddress1());
        holder.ratingTextView.setText(String.valueOf(nightlife.getRating()));

        // Get themed image URL
        String imageUrl = getUnsplashUrlForBar(nightlife.getName());

        // Load the image using Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder1) // Fallback image while loading
                .error(R.drawable.error_image) // Fallback image if URL fails
                .into(holder.imageView);

        // Set the "Navigate" button functionality
        holder.navigateButton.setOnClickListener(v -> {
            String address = nightlife.getLocation().getAddress1();
            if (address == null || address.isEmpty()) {
                Toast.makeText(holder.itemView.getContext(), "Address not available.", Toast.LENGTH_SHORT).show();
            } else {
                navigateToAddress(holder.itemView.getContext(), address);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nightlifeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView ratingTextView;
        ImageView imageView;
        Button navigateButton;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            imageView = itemView.findViewById(R.id.nightlifeImageView);
            navigateButton = itemView.findViewById(R.id.navigateButton); // Reference to the Navigate button
        }
    }

    // Helper method to navigate to the address using Google Maps
    private void navigateToAddress(Context context, String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        } else {
            Uri browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            context.startActivity(browserIntent);
        }
    }

    public String getUnsplashUrlForBar(String name) {
        name = name.toLowerCase(); // Normalize for comparison

        if (name.contains("night club") || name.contains("club")) {
            return "https://images.unsplash.com/photo-people-dancing-inside-room-with-green-lights"; // Image by Antoine J.
        } else if (name.contains("stage") || name.contains("live")) {
            return "https://images.unsplash.com/photo-people-sitting-on-chair-watching-the-stage"; // Image by Shawn
        } else {
            return "https://cyprus.wiz-guide.com/assets/modules/kat/places/202006/2344/images/det_04_helicon_roof_bar.jpg"; // Fallback random image
        }
    }
}
