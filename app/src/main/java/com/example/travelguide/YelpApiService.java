package com.example.travelguide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApiService {
    @GET("v3/businesses/search")
    Call<YelpResponse> searchNightlife(
            @Header("Authorization") String authHeader,
            @Query("location") String location,
            @Query("categories") String categories,
            @Query("limit") int limit
    );

    @GET("v3/businesses/{id}")
    Call<BusinessDetailsResponse> getBusinessDetails(
            @Header("Authorization") String authHeader,
            @Path("id") String businessId
    );
}