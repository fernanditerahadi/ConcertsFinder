package com.example.android.music.database.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("cafe")
    @Expose
    public String cafe;
    @SerializedName("road")
    @Expose
    public String road;
    @SerializedName("suburb")
    @Expose
    public String suburb;
    @SerializedName("county")
    @Expose
    public String county;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("postcode")
    @Expose
    public String postcode;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("country_code")
    @Expose
    public String countryCode;

    public Address(String cafe,
                   String road,
                   String suburb,
                   String county,
                   String region,
                   String state,
                   String postcode,
                   String country,
                   String countryCode) {
        this.cafe = cafe;
        this.road = road;
        this.suburb = suburb;
        this.county = county;
        this.region = region;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getCafe() {
        return cafe;
    }

    public String getRoad() {
        return road;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCounty() {
        return county;
    }

    public String getRegion() {
        return region;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }


}
