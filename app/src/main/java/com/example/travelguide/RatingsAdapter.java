package com.example.travelguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                .inflate(R.layout.item_rating, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        RatingItem item = ratingList.get(position);
        holder.personName.setText(item.getName());
        holder.personLocation.setText(item.getLocation());
        holder.reviewText.setText(item.getReview());
        holder.likeCount.setText(item.getLikes() + "K");
        holder.avatarImage.setImageResource(item.getPhotoResId()); // Set the avatar photo
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    static class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView personName, personLocation, reviewText, likeCount;
        ImageView avatarImage;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);
            personLocation = itemView.findViewById(R.id.personLocation);
            reviewText = itemView.findViewById(R.id.reviewText);
            likeCount = itemView.findViewById(R.id.likeCount);
            avatarImage = itemView.findViewById(R.id.avatarImage);
        }
    }
}
