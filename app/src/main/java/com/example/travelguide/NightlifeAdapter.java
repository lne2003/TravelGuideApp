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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        // URLs for different categories
        List<String> clubUrls = Arrays.asList(
                "https://www.cherishtrip.com/wp-content/uploads/2018/09/nightlife-2.jpg",
                "https://www.discoverhongkong.com/content/dam/dhk/intl/explore/nightlife/events/temple-street-960x720.jpg",
                "https://www.nightlifeinternational.org/images/raxo_thumbs/amk/tb-w400-h300-crop-int-c17db24579aef3632c778c4dbee90c79.jpg"
        );

        List<String> stageUrls = Arrays.asList(
                "https://www.eventbrite.com/blog/wp-content/uploads/2024/01/image4-min-2-768x432.png",
                "https://c8.alamy.com/comp/BC8H4C/row-of-pubs-along-the-busy-temple-bar-nightlife-area-dublin-republic-BC8H4C.jpg",
                "https://www.cherishtrip.com/wp-content/uploads/2018/09/nightlife-2.jpg"
        );

        List<String> barUrls = Arrays.asList(
                "https://s3-media0.fl.yelpcdn.com/bphoto/OGxQa5IX4OFbFBjGtC0Evw/348s.jpg",
                "https://www.discoverhongkong.com/content/dam/dhk/intl/explore/nightlife/rooftop-bars-in-hong-kong/rooftop-bars-in-hong-kong-960x720.jpg",
                "https://www.spccs1.co.uk/ImageAssets/a1d78abe8e7347368c784a867af06354.JPG"
        );

        // Match category and return a random URL from the corresponding list
        if (name.contains("nightlife") || name.contains("club")) {
            return getRandomUrl(clubUrls);
        } else if (name.contains("nightlife") || name.contains("bar")) {
            return getRandomUrl(stageUrls);
        } else {
            return getRandomUrl(barUrls);
        }
    }

    // Helper method to get a random URL from a list
    private String getRandomUrl(List<String> urls) {
        Random random = new Random();
        return urls.get(random.nextInt(urls.size()));
    }

}
