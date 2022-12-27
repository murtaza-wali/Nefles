package com.am.netfles.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.am.netfles.R;
import com.am.netfles.ViewModel.MostPopularTvShowsViewModel;
import com.am.netfles.adapter.TvShowAdapter;
import com.am.netfles.databinding.ActivityMainBinding;
import com.am.netfles.listeners.TvShowListener;
import com.am.netfles.models.TvShows;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {


    ActivityMainBinding mainBinding;

    private MostPopularTvShowsViewModel viewModel;

    private TvShowAdapter tvShowAdapter;
    List<TvShows> tvShowsList = new ArrayList<>();


    private int currentPage = 1;

    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();

    }

    private void doInitialization() {


        mainBinding.tvShowRv.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);

        mainBinding.imgWatchlist.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, WatchListActivity.class)));
        tvShowAdapter = new TvShowAdapter(tvShowsList, this);
        mainBinding.tvShowRv.setAdapter(tvShowAdapter);
        mainBinding.tvShowRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mainBinding.tvShowRv.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getMostPopularTvShows();
                    }
                }
            }
        });
        getMostPopularTvShows();

        pullRefresh();
        mainBinding.tvShowRv.setVisibility(View.VISIBLE);

    }

    private void getMostPopularTvShows() {
        toggleLoading();
        Log.e("Final Url", String.valueOf(viewModel.getMostPopularTvShows(currentPage)));

        viewModel.getMostPopularTvShows(currentPage).observe
                (this, mostPopularTvShowsResponse -> {
                    toggleLoading();
                    if (mostPopularTvShowsResponse != null) {
                        totalAvailablePages = mostPopularTvShowsResponse.getTotal_pages();
                        if (mostPopularTvShowsResponse.getTvShowsList() != null) {
                            int oldCount = tvShowsList.size();
                            tvShowsList.addAll(mostPopularTvShowsResponse.getTvShowsList());
                            tvShowAdapter.notifyItemRangeInserted(oldCount, tvShowsList.size());

                        }
                    }
                });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (mainBinding.getIsloading() != null && mainBinding.getIsloading()) {
                mainBinding.setIsloading(false);
                Toast.makeText(this, "Isloading", Toast.LENGTH_SHORT).show();
            } else {
                mainBinding.setIsloading(true);
            }
        } else {
            if (mainBinding.getIsloadingMore() != null && mainBinding.getIsloadingMore()) {
                mainBinding.setIsloadingMore(false);
            } else {
                mainBinding.setIsloadingMore(true);
            }
        }
    }

    private void pullRefresh() {

        mainBinding.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainBinding.tvShowRv.setVisibility(View.GONE);
                doInitialization();
                mainBinding.pullToRefresh.setRefreshing(false);

            }
        });

    }

    @Override
    public void onTvShowClicked(TvShows tvShows) {
        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);

        intent.putExtra("tvShow", tvShows);
        startActivity(intent);


    }
}