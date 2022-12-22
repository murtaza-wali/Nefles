package com.am.netfles.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.am.netfles.Network.ApiClient;
import com.am.netfles.Network.ApiService;
import com.am.netfles.responses.TvShowDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {

    private ApiService apiService;

    public TvShowDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        Log.e("Response: ", apiService.toString());
        Log.e("Response URL", String.valueOf(apiService.getTVShowDetails("arrow")));
    }


    public LiveData<TvShowDetailResponse> getTvShowDetails(String tvShowId) {
        MutableLiveData<TvShowDetailResponse> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TvShowDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetailResponse> call, @NonNull Response<TvShowDetailResponse> response) {
                data.setValue(response.body());
                Log.e("Response", response.body().toString());
                Log.e("Response", response.raw().body().toString());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetailResponse> call, Throwable t) {
                data.setValue(null);
                Log.e("Error", "Error found!");
                Log.e("Error", t.getMessage());
            }
        });

        return data;
    }

}
