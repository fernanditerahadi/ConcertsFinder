package com.example.android.music.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.music.database.AppDatabase;
import com.example.android.music.database.models.Concert;

import java.util.List;

public class ConcertListViewModel extends AndroidViewModel {

    private LiveData<List<Concert>> concertList;

    public ConcertListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        concertList = database.appDao().loadAllConcerts();
    }

    public LiveData<List<Concert>> getConcertList() {
        return concertList;
    }

}
