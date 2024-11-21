package com.example.travelguide;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFetcherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherFetcherActivity";
    private static final String BASE_URL = "http://api.weatherstack.com/";
    private static final String API_KEY = "784c250cd160e7d728b26727e290520d"; // Your Weatherstack API key

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Example location for Paris (You can dynamically set it based on your needs)
        String location = "Paris";

        fetchWeatherData(location, "destinationDocumentId"); // Replace "destinationDocumentId" with the actual document ID
    }

    private void fetchWeatherData(String location, String destinationDocumentId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherstackApiService weatherApiService = retrofit.create(WeatherstackApiService.class);

        Call<WeatherstackResponse> call = weatherApiService.getCurrentWeather(API_KEY, location);
        call.enqueue(new Callback<WeatherstackResponse>() {
            @Override
            public void onResponse(Call<WeatherstackResponse> call, Response<WeatherstackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherstackResponse weatherResponse = response.body();

                    // Extract weather data from the response
                    double temp = weatherResponse.getCurrent().getTemperature();
                    int humidity = weatherResponse.getCurrent().getHumidity();
                    double windSpeed = weatherResponse.getCurrent().getWindSpeed();
                    String description = weatherResponse.getCurrent().getWeatherDescription();

                    // Prepare data to save to Firestore
                    WeatherData weatherData = new WeatherData(temp, humidity, windSpeed, description);

                    // Save the weather data under the "weather" subcollection
                    db.collection("destinations")
                            .document(destinationDocumentId)
                            .collection("weather")
                            .document("current")
                            .set(weatherData)
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "Weather data successfully written!"))
                            .addOnFailureListener(e -> Log.w(TAG, "Error writing weather data", e));
                } else {
                    Log.e(TAG, "Failed to get response from API");
                }
            }

            @Override
            public void onFailure(Call<WeatherstackResponse> call, Throwable t) {
                Log.e(TAG, "Failed to fetch weather data: " + t.getMessage());
            }
        });
    }
}
