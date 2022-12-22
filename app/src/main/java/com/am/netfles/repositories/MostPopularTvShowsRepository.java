package com.am.netfles.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.am.netfles.Network.ApiClient;
import com.am.netfles.Network.ApiService;
import com.am.netfles.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowsRepository {

    private ApiService apiService;

    public MostPopularTvShowsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        Log.e("Response Popular: ", apiService.toString());

    }

    public LiveData<TvShowsResponse> getMostPopularTvShows(int page) {
        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTvShows(page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {

                data.setValue(response.body());

                Log.e("Tv show Details:", response.body().toString());
                Log.e("Response popular", response.raw().body().toString());

            }

            @Override
            public void onFailure(Call<TvShowsResponse> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;
    }
}
