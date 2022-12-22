package com.am.netfles.Network;

import android.database.Observable;
import android.util.Log;

import com.am.netfles.responses.TvShowDetailResponse;
import com.am.netfles.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowsResponse>
    getMostPopularTvShows
            (@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailResponse> getTVShowDetails(@Query("q") String tvShowId);


}
