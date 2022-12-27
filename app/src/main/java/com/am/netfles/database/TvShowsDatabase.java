package com.am.netfles.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.am.netfles.dao.tvShowsDao;
import com.am.netfles.models.TvShows;

@Database(entities = TvShows.class, version = 1, exportSchema = false)
public abstract class TvShowsDatabase extends RoomDatabase {
    private static TvShowsDatabase tvShowsDatabase;

    public static synchronized TvShowsDatabase getTvShowsDatabase(Context context) {
        if (tvShowsDatabase == null) {
            tvShowsDatabase = Room.databaseBuilder(
                    context, TvShowsDatabase.class,
                    "tv_shows_db"
            ).build();
        }
        return tvShowsDatabase;
    }

    public abstract tvShowsDao tvShowsDao();
}
