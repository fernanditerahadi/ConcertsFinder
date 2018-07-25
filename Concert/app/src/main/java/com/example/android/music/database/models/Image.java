package com.example.android.music.database.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("size")
    @Expose
    private String size;

    public Image(String text, String size) {
        this.text = text;
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public String getSize() {
        return size;
    }
}
