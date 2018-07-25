package com.example.android.music.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationIqApiCall {

    private static final String LOCATIONIQ_BASE_URL = "https://us1.locationiq.org/";
    private static final String LOCATIONIQ_API_KEY = "5eaba6961fc852";

    private static Retrofit getRetrofitInstance() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LOCATIONIQ_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    public static LocationIqService getLastFmService() {
        return getRetrofitInstance().create(LocationIqService.class);
    }

    public static String getApiKey() {
        return LOCATIONIQ_API_KEY;
    }

}
