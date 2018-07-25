package com.example.android.music.services;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LastFmApiCall {

    private static final String LASTFM_BASE_URL = "http://ws.audioscrobbler.com/";
    private static final String LASTFM_API_KEY = "a226d910f264b4bb12ee6484dc726209";

    private static Retrofit getRetrofitInstance() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LASTFM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
        return builder.build();
    }

    public static LastFmService getLastFmService() {
        return getRetrofitInstance().create(LastFmService.class);
    }

    public static String getApiKey() {
        return LASTFM_API_KEY;
    }

}