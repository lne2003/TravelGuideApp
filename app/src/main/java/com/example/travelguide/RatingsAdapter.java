package com.example.travelguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.RatingViewHolder> {

    private List<RatingItem> ratingList;

    public RatingsAdapter(List<RatingItem> ratingList) {
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rating, parent, false); // Ensure layout file name matches
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        RatingItem item = ratingList.get(position);

        // Set text fields
        holder.personName.setText(item.getName() != null ? item.getName() : "Anonymous");
        holder.personLocation.setText(item.getLocation() != null ? item.getLocation() : "Unknown Location");
        holder.reviewText.setText(item.getReview() != null ? item.getReview() : "No review provided.");
        holder.likeCount.setText(item.getLikes() + "K");

        // Load avatar image
        if (item.getPhotoUrl() != null && !item.getPhotoUrl().isEmpty()) {
            Picasso.get().load(item.getPhotoUrl()).placeholder(R.drawable.placeholder).into(holder.avatarImage);
         // Fallback to resource-based image
        } else {
            holder.avatarImage.setImageResource(R.drawable.peletie); // Default placeholder image
        }

        // Set rating
        holder.reviewRatingBar.setRating(item.getRating()); // Set rating from RatingItem
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    static class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView personName, personLocation, reviewText, likeCount;
        ImageView avatarImage;
        RatingBar reviewRatingBar; // Add RatingBar

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);
            personLocation = itemView.findViewById(R.id.personLocation);
            reviewText = itemView.findViewById(R.id.reviewText);
            likeCount = itemView.findViewById(R.id.likeCount);
            avatarImage = itemView.findViewById(R.id.avatarImage);
            reviewRatingBar = itemView.findViewById(R.id.reviewRatingBar); // Initialize RatingBar
        }
    }
}
