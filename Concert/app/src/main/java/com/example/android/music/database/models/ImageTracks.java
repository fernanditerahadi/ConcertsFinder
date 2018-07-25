package com.example.android.music.database.models;

import java.util.List;

public class ImageTracks {

    private String imageUrl;
    private List<Track> track;

    public ImageTracks(String imageUrl, List<Track> track) {
        this.imageUrl = imageUrl;
        this.track = track;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Track> getTrack() {
        return track;
    }
}
