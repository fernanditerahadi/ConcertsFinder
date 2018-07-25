package com.example.android.music.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.music.database.AppDatabase;
import com.example.android.music.database.models.Concert;

public class ConcertViewModel extends ViewModel {

    private LiveData<Concert> concert;

    public ConcertViewModel(AppDatabase appDatabase, String concertId) {
        concert = appDatabase.appDao().loadConcertById(concertId);
    }

    public LiveData<Concert> getConcert() {
        return concert;
    }

}
