package com.example.android.music.services;

import java.util.Random;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JamBaseApiCall {

    private static final String JAMBASE_BASE_URL = "http://api.jambase.com/";
    private static final String[] JAMBASE_API_KEY =
            {"b4jstzufu6pjr26xf8uczwkv",
            "dzg3d3ygyva7vb287c6vh4p9",
            "jrsnytkdq9uty8269chdm4m4",
            "yttnppau8xjx7h2mrfmhhwsx",
            "tjdxbtt654urjvvnexmq6eyc",
            "ugzvgfc8tvabu52kca2e7qy9",
            "zkkfn7q77grqpxdgv7ptfpvv",
            "nsrbbbdesny4u8fc6kpr8p4g"};

    private static Retrofit getRetrofitInstance() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(JAMBASE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
        return builder.build();
    }

    public static JamBaseService getJamBaseService() {
        return getRetrofitInstance().create(JamBaseService.class);
    }

    public static String getApiKey() {
        return JAMBASE_API_KEY[new Random().nextInt(JAMBASE_API_KEY.length - 1)];
    }

}
