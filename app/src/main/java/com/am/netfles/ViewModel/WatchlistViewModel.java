package com.am.netfles.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.am.netfles.database.TvShowsDatabase;
import com.am.netfles.models.TvShows;

import java.util.List;

import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private TvShowsDatabase tvShowsDatabase;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        tvShowsDatabase = TvShowsDatabase.getTvShowsDatabase(application);
    }

    public Flowable<List<TvShows>> loadWatchList() {
        return tvShowsDatabase.tvShowsDao().getWatchList();
    }
}
