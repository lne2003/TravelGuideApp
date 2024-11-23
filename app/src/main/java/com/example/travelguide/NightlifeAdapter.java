package com.example.travelguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.travelguide.Business;

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
        holder.nameTextView.setText(nightlife.getName());
        holder.addressTextView.setText(nightlife.getLocation().getAddress1());
        holder.ratingTextView.setText(String.valueOf(nightlife.getRating()));
    }

    @Override
    public int getItemCount() {
        return nightlifeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView ratingTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }
    }
}


