package com.am.netfles.listeners;

import com.am.netfles.models.TvShowDetails;
import com.am.netfles.models.TvShows;
import com.am.netfles.responses.TvShowDetailResponse;

public interface TvShowListener {
    void onTvShowClicked(TvShows tvShows);

}
