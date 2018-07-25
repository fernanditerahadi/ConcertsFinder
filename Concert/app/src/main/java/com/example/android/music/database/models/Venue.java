package com.example.android.music.database.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("StateCode")
    @Expose
    private String stateCode;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    public Venue(Integer id,
                 String name,
                 String address,
                 String city,
                 String state,
                 String stateCode,
                 String country,
                 String countryCode,
                 String zipCode,
                 String url,
                 Double latitude,
                 Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.stateCode = stateCode;
        this.country = country;
        this.countryCode = countryCode;
        this.zipCode = zipCode;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getUrl() {
        return url;
    }
}