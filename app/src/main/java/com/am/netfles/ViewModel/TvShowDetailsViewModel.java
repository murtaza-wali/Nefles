package com.am.netfles.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.am.netfles.repositories.TvShowDetailsRepository;
import com.am.netfles.responses.TvShowDetailResponse;

public class TvShowDetailsViewModel extends ViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;

    public TvShowDetailsViewModel() {

        tvShowDetailsRepository = new TvShowDetailsRepository();
    }

    public LiveData<TvShowDetailResponse> getShowDetails(String tvShowId) {

        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }
}
