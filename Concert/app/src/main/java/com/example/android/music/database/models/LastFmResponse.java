package com.example.android.music.database.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastFmResponse {

    @SerializedName("toptracks")
    @Expose
    private Toptracks toptracks;

    public LastFmResponse(Toptracks toptracks) {
        this.toptracks = toptracks;
    }

    public Toptracks getToptracks() {
        return toptracks;
    }
}

