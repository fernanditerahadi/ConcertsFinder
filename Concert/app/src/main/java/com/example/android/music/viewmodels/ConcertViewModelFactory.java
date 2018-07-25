package com.example.android.music.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.music.database.AppDatabase;

public class ConcertViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase mAppDatabase;
    private String mConcertId;

    public ConcertViewModelFactory(AppDatabase appDatabase, String concertId) {
        mAppDatabase = appDatabase;
        mConcertId = concertId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConcertViewModel(mAppDatabase, mConcertId);
    }
}
