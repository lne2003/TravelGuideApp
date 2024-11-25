package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NightlifeActivity extends AppCompatActivity {
    private RecyclerView nightlifeRecyclerView;
    private NightlifeAdapter nightlifeAdapter;
    private ProgressBar progressBar;
    private static final String TAG = "NightlifeActivity";
    private static final String API_KEY = "Bearer dNdcWrgDP7S8gm5eixxhG05jFmYQkEUaTZk_yBU5teF7dUxHAPQpm99AZbUBZZnKGoqnrP8e_hW7tkAlhDUKvAYw5WTeKTo9S-YWkYNfonVsUCtd4TGVutJpUp4_Z3Yx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightlife);

        progressBar = findViewById(R.id.progressBar);
        nightlifeRecyclerView = findViewById(R.id.nightlifeRecyclerView);

        nightlifeAdapter = new NightlifeAdapter(new ArrayList<>());
        nightlifeRecyclerView.setAdapter(nightlifeAdapter);
        nightlifeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String cityName = getIntent().getStringExtra("cityName");
        Log.d(TAG, "Received cityName: " + cityName);

        if (cityName != null) {
            if (NetworkUtils.isNetworkAvailable(this)) {
                // If online, fetch nightlife data directly
                fetchNightlife(cityName);
            } else {
                // If offline, show loading page for 3 seconds
                showLoadingPage(cityName);
            }
        } else {
            Log.e(TAG, "City name is null.");
        }
    }

    /**
     * Show the loading screen for 3 seconds if offline, and attempt to fetch nightlife data afterward.
     */
    private void showLoadingPage(final String cityName) {
        progressBar.setVisibility(View.VISIBLE); // Show progress bar to simulate loading

        // Simulate a 3-second loading time
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE); // Hide progress bar after 3 seconds
                Toast.makeText(NightlifeActivity.this, "No internet connection. Showing cached data if available.", Toast.LENGTH_SHORT).show();
                // Show a Toast or load cached data (if available) here
            }
        }, 3000); // Delay of 3 seconds
    }

    private void fetchNightlife(String cityName) {
        Log.d(TAG, "Fetching nightlife for city: " + cityName);
        progressBar.setVisibility(View.VISIBLE);

        YelpApiService apiService = ApiClient.getClient().create(YelpApiService.class);
        Call<YelpResponse> call = apiService.searchNightlife(API_KEY, cityName, "bars,clubs", 10);

        call.enqueue(new Callback<YelpResponse>() {
            @Override
            public void onResponse(Call<YelpResponse> call, Response<YelpResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Business> nightlifeList = response.body().getBusinesses();
                    Log.d(TAG, "Nightlife data fetched: " + nightlifeList.size() + " items.");
                    nightlifeAdapter.updateData(nightlifeList);
                } else {
                    Log.e(TAG, "API response error: " + response.code() + " - " + response.message());
                    Toast.makeText(NightlifeActivity.this, "No nightlife data found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YelpResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error fetching nightlife: " + t.getMessage());
                Toast.makeText(NightlifeActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}