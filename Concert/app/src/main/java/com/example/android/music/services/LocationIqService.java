package com.example.android.music.services;

import com.example.android.music.database.models.LocationIqResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationIqService {

    @GET("/v1/reverse.php?&format=json")
    Call<LocationIqResponse> getZipCode(@Query("lat") String latitude,
                                        @Query("lon") String longitude,
                                        @Query("key") String apiKey);
}
