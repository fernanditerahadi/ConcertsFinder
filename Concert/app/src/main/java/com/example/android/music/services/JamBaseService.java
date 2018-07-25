package com.example.android.music.services;

import com.example.android.music.database.models.JamBaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JamBaseService {

    @GET("/events?page=0&o=json")
    Observable<JamBaseResponse> getConcerts(@Query("zipCode") String zip,
                                            @Query("api_key") String apiKey);
}
