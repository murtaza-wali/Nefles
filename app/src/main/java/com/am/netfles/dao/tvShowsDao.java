package com.am.netfles.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.am.netfles.models.TvShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface tvShowsDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<TvShows>> getWatchList();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addWatchList(TvShows tvShows);

    @Delete
    void removeFromWatchList(TvShows tvShows);
}
