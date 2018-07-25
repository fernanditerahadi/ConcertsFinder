package com.example.android.music.database.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    public Track(String name, String playcount, String listeners, String url, List<Image> image) {
        this.name = name;
        this.playcount = playcount;
        this.listeners = listeners;
        this.url = url;
        this.image = image;
    }

    protected Track(Parcel in) {
        name = in.readString();
        playcount = in.readString();
        listeners = in.readString();
        url = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPlaycount() {
        return playcount;
    }

    public String getListeners() {
        return listeners;
    }

    public String getUrl() {
        return url;
    }

    public List<Image> getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(playcount);
        dest.writeString(listeners);
        dest.writeString(url);
    }
}
