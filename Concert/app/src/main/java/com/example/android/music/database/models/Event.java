package com.example.android.music.database.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Venue")
    @Expose
    private Venue venue;
    @SerializedName("Artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("TicketUrl")
    @Expose
    private String ticketUrl;

    public Event(Integer id, String date, Venue venue, List<Artist> artists, String ticketUrl) {
        this.id = id;
        this.date = date;
        this.venue = venue;
        this.artists = artists;
        this.ticketUrl = ticketUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Venue getVenue() {
        return venue;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

}
