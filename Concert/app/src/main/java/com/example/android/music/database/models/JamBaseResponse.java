package com.example.android.music.database.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JamBaseResponse {

    @SerializedName("Events")
    @Expose
    private List<Event> events = null;

    public JamBaseResponse(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}