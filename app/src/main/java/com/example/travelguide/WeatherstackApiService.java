package com.example.travelguide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherstackApiService {
    @GET("current")
    Call<WeatherstackResponse> getCurrentWeather(
            @Query("access_key") String apiKey,
            @Query("query") String location
    );
}
