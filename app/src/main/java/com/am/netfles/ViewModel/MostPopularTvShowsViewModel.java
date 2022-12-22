package com.am.netfles.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.am.netfles.repositories.MostPopularTvShowsRepository;
import com.am.netfles.responses.TvShowsResponse;

public class MostPopularTvShowsViewModel extends ViewModel {
    private MostPopularTvShowsRepository mostPopularTvShowsRepository;

    public MostPopularTvShowsViewModel() {
        mostPopularTvShowsRepository = new MostPopularTvShowsRepository();
    }

    public LiveData<TvShowsResponse> getMostPopularTvShows(int page) {

        return mostPopularTvShowsRepository.getMostPopularTvShows(page);
    }
}
