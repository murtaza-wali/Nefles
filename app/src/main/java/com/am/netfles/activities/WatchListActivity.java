package com.am.netfles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.am.netfles.R;
import com.am.netfles.ViewModel.WatchlistViewModel;
import com.am.netfles.databinding.ActivityWatchListBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchlistViewModel watchlistViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {

        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(view -> onBackPressed());
    }

    private void loadWatchlist() {
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    Toast.makeText(this, "WatchList " + tvShows.size(), Toast.LENGTH_SHORT).show();
                }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }
}