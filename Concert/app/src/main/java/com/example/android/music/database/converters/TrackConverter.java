package com.example.android.music.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.android.music.database.models.Track;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TrackConverter {

    @TypeConverter
    public String fromTrackList(List<Track> track) {
        if (track == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Track>>() {
        }.getType();
        return gson.toJson(track, type);
    }

    @TypeConverter
    public List<Track> toTrackList(String trackListString) {
        if (trackListString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Track>>() {
        }.getType();
        return gson.fromJson(trackListString, type);
    }
}
