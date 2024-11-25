package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class WeatherActivity extends BaseActivity {

    private static final String TAG = "WeatherActivity";
    private TextView temperatureTextView;
    private TextView descriptionTextView;
    private ImageView weatherIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection. Please try again later.", Toast.LENGTH_SHORT).show();
            Intent offlineIntent = new Intent(this, OfflineActivity.class);
            startActivity(offlineIntent);
            finish();
            return;
        }
        setContentView(R.layout.activity_weather);

        // Initialize views
        temperatureTextView = findViewById(R.id.temperatureTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        weatherIconImageView = findViewById(R.id.weatherIconImageView);

        String destinationName = getIntent().getStringExtra("destinationName");

        if (destinationName != null) {
            fetchWeatherData(destinationName);
        } else {
            Log.e(TAG, "No destination name provided for fetching weather data");
        }
    }

    private void fetchWeatherData(String destinationName) {
        String weatherApiKey = "784c250cd160e7d728b26727e290520d";
        String url = "http://api.weatherstack.com/current?access_key=" + weatherApiKey + "&query=" + destinationName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to fetch weather data", e);
                runOnUiThread(() -> {
                    temperatureTextView.setText("Failed to load weather data.");
                    descriptionTextView.setText("");
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        JSONObject current = jsonObject.getJSONObject("current");

                        double temperature = current.getDouble("temperature");
                        String weatherDescription = current.getJSONArray("weather_descriptions").getString(0);
                        String weatherIconUrl = current.getJSONArray("weather_icons").getString(0);

                        runOnUiThread(() -> {
                            temperatureTextView.setText(String.format("%.1f°C", temperature));
                            descriptionTextView.setText(weatherDescription);

                            // Load the weather icon using Picasso with placeholder and error handling
                            Picasso.get()
                                    .load(weatherIconUrl)
                                    .placeholder(R.drawable.placeholder) // Placeholder while loading
                                    .error(R.drawable.placeholder) // Fallback in case of an error
                                    .into(weatherIconImageView);
                        });

                    } catch (Exception e) {
                        Log.e(TAG, "Failed to parse weather data", e);
                    }
                } else {
                    runOnUiThread(() -> {
                        temperatureTextView.setText("Failed to load weather data.");
                        descriptionTextView.setText("");
                    });
                }
            }
        });
    }
    @Override
    protected int getSelectedMenuItemId() {
        return R.id.nav_home; // Adjust based on the context
    }
}
