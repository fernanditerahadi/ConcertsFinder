package com.example.android.music.database.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationIqResponse {

    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("licence")
    @Expose
    public String licence;
    @SerializedName("osm_type")
    @Expose
    public String osmType;
    @SerializedName("osm_id")
    @Expose
    public String osmId;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("address")
    @Expose
    public Address address;
    @SerializedName("boundingbox")
    @Expose
    public List<String> boundingbox = null;

    public LocationIqResponse(String placeId,
                              String licence,
                              String osmType,
                              String osmId,
                              String lat,
                              String lon,
                              String displayName,
                              Address address,
                              List<String> boundingbox) {
        this.placeId = placeId;
        this.licence = licence;
        this.osmType = osmType;
        this.osmId = osmId;
        this.lat = lat;
        this.lon = lon;
        this.displayName = displayName;
        this.address = address;
        this.boundingbox = boundingbox;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getLicence() {
        return licence;
    }

    public String getOsmType() {
        return osmType;
    }

    public String getOsmId() {
        return osmId;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getBoundingbox() {
        return boundingbox;
    }
}

