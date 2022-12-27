package com.am.netfles.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.am.netfles.database.TvShowsDatabase;
import com.am.netfles.models.TvShows;
import com.am.netfles.repositories.TvShowDetailsRepository;
import com.am.netfles.responses.TvShowDetailResponse;

import io.reactivex.Completable;

public class TvShowDetailsViewModel extends AndroidViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowsDatabase tvShowsDatabase;

    public TvShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TvShowDetailsRepository();
        tvShowsDatabase = TvShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TvShowDetailResponse> getShowDetails(String tvShowId) {

        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }

    public Completable addToWatchList(TvShows tvShows) {
        return tvShowsDatabase.tvShowsDao().addWatchList(tvShows);
    }
}
