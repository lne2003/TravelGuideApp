package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NightlifeDetailsActivity extends AppCompatActivity {
    private YelpApiService apiService;
    private static final String API_KEY = "Bearer dNdcWrgDP7S8gm5eixxhG05jFmYQkEUaTZk_yBU5teF7dUxHAPQpm99AZbUBZZnKGoqnrP8e_hW7tkAlhDUKvAYw5WTeKTo9S-YWkYNfonVsUCtd4TGVutJpUp4_Z3Yx";

    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView ratingTextView;
    private ImageView imageView; // Add this for the image


    public void onResponse(Call<BusinessDetailsResponse> call, Response<BusinessDetailsResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            BusinessDetailsResponse details = response.body();

            // Log the full response for debugging
            Log.d("NightlifeDetails", "Raw Response: " + response.body().toString());

            // Existing code for setting text fields
            nameTextView.setText(details.getName());
            addressTextView.setText(details.getLocation().getAddress1());
            phoneTextView.setText(details.getPhone());
            ratingTextView.setText(String.valueOf(details.getRating()));

            // Log and load the image
            String imageUrl = details.getImageUrl();
            Log.d("NightlifeDetails", "Image URL: " + imageUrl);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(NightlifeDetailsActivity.this)
                        .load(imageUrl)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.placeholder1);
            }
        } else {
            Toast.makeText(NightlifeDetailsActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
        }
    }


    private void fetchBusinessDetails(String businessId) {
        if (businessId == null) {
            Toast.makeText(this, "Business ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getBusinessDetails(API_KEY, businessId).enqueue(new Callback<BusinessDetailsResponse>() {
            @Override
            public void onResponse(Call<BusinessDetailsResponse> call, Response<BusinessDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BusinessDetailsResponse details = response.body();

                    // Set the text fields
                    nameTextView.setText(details.getName());
                    addressTextView.setText(details.getLocation().getAddress1());
                    phoneTextView.setText(details.getPhone());
                    ratingTextView.setText(String.valueOf(details.getRating()));

                    // Load the image using Glide
                    String imageUrl = details.getImageUrl();
                    Log.d("NightlifeDetails", "Image URL: " + imageUrl);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(NightlifeDetailsActivity.this)
                                .load(imageUrl)
                                .into(imageView);
                    } else {
                        imageView.setImageResource(R.drawable.placeholder1); // Fallback image
                    }
                } else {
                    Toast.makeText(NightlifeDetailsActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessDetailsResponse> call, Throwable t) {
                Toast.makeText(NightlifeDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
