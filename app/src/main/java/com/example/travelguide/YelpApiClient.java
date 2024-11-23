package com.example.travelguide;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpApiClient {
    private static final String BASE_URL = "https://api.yelp.com/";

    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}


