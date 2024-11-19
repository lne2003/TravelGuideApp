package com.example.travelguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DestinationsAdapter extends RecyclerView.Adapter<DestinationsAdapter.DestinationViewHolder> {

    private List<Destination> destinationList;
    private Context context;

    public DestinationsAdapter(List<Destination> destinationList, Context context) {
        this.destinationList = destinationList;
        this.context = context;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_destination, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.nameTextView.setText(destination.getName());
        holder.descriptionTextView.setText(destination.getDescription());

        // Load image from URL using Picasso
        Picasso.get().load(destination.getImageUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DestinationDetailsActivity.class);
            intent.putExtra("name", destination.getName());
            intent.putExtra("description", destination.getDescription());
            intent.putExtra("imageUrl", destination.getImageUrl());
            intent.putStringArrayListExtra("restaurants", new ArrayList<>(destination.getRestaurants()));
            intent.putExtra("weather", destination.getWeather());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView imageView;

        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
