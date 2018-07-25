package com.example.android.music.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.music.database.models.Concert;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM concert ORDER BY concert_date ASC")
    LiveData<List<Concert>> loadAllConcerts();

    @Insert
    void insertConcert(Concert concert);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateConcert(Concert concert);

    @Delete
    void deleteConcert(Concert concert);

    @Query("DELETE FROM concert WHERE concert_id = :concertId")
    void deleteConcertById(String concertId);

    @Query("SELECT * FROM concert WHERE concert_id = :concertId")
    LiveData<Concert> loadConcertById(String concertId);


}
