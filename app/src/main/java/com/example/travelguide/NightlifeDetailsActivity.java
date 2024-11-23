package com.example.travelguide;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguide.ApiClient;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightlife_details);

        nameTextView = findViewById(R.id.nameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        ratingTextView = findViewById(R.id.ratingTextView);

        String businessId = getIntent().getStringExtra("businessId");
        apiService = ApiClient.getClient().create(YelpApiService.class);

        fetchBusinessDetails(businessId);
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
                    nameTextView.setText(details.getName());
                    addressTextView.setText(details.getLocation().getAddress1());
                    phoneTextView.setText(details.getPhone());
                    ratingTextView.setText(String.valueOf(details.getRating()));
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