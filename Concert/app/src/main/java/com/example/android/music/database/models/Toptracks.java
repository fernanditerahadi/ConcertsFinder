package com.example.android.music.database.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Toptracks {

    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    public Toptracks(List<Track> track) {
        this.track = track;
    }

    public List<Track> getTrack() {
        return track;
    }

}
