package com.example.android.music.services;

import com.example.android.music.database.models.LastFmResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFmService {

    @GET("/2.0?method=artist.gettoptracks&format=json&autocorrect=1&limit=10")
    Observable<LastFmResponse> getTracks(@Query("artist") String artistName,
                                         @Query("api_key") String apiKey);

}
