package com.am.netfles.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.am.netfles.R;
import com.am.netfles.ViewModel.TvShowDetailsViewModel;
import com.am.netfles.adapter.EpisodesAdapter;
import com.am.netfles.adapter.ImageSliderAdapter;
import com.am.netfles.databinding.ActivityTvShowDetailsBinding;
import com.am.netfles.databinding.EpisodeLayoutBinding;
import com.am.netfles.models.TvShows;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowDetailsActivity extends AppCompatActivity {

    ActivityTvShowDetailsBinding detailsBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;
    private BottomSheetDialog bottomSheetDialog;
    private EpisodeLayoutBinding episodeLayoutBinding;

    private TvShows tvShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);
        detailsBinding.backImageBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        doInitialization();
    }

    private void doInitialization() {

        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        tvShows = (TvShows) getIntent().getSerializableExtra("tvShow");
        getTvShowDetails();
    }

    private void getTvShowDetails() {
        detailsBinding.setIsloading(true);
        String tvShowid = String.valueOf(tvShows.getId());
        Toast.makeText(this, tvShowid, Toast.LENGTH_SHORT).show();
        tvShowDetailsViewModel.getShowDetails(tvShowid).observe(
                this, tvShowDetailResponse -> {

                    if (tvShowDetailResponse.getTvShowDetails() != null) {
                        if (tvShowDetailResponse.getTvShowDetails().getPictures() != null) {

                            loadImageSlider(tvShowDetailResponse.getTvShowDetails().getPictures());
                            detailsBinding.setIsloading(false);
                        }
                        detailsBinding.setTvShowImageURL(
                                tvShowDetailResponse.getTvShowDetails().getImage_path()
                        );
                        detailsBinding.ImageTvShow.setVisibility(View.VISIBLE);


                        detailsBinding.setDescription(
                                String.valueOf(
                                        HtmlCompat.fromHtml(
                                                tvShowDetailResponse.getTvShowDetails().getDescription(),
                                                HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                )
                        );
                        detailsBinding.textReadMore.setVisibility(View.VISIBLE);
                        detailsBinding.textDescription.setVisibility(View.VISIBLE);
                        detailsBinding.textReadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (detailsBinding.textReadMore.getText().toString().equals("Read More..")) {
                                    detailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                    detailsBinding.textDescription.setEllipsize(null);
                                    detailsBinding.textReadMore.setText(R.string.read_less);
                                } else {
                                    detailsBinding.textDescription.setMaxLines(4);
                                    detailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                    detailsBinding.textReadMore.setText(R.string.read_more);
                                }
                            }
                        });
                        detailsBinding.setRating(
                                String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        Double.parseDouble(tvShowDetailResponse.getTvShowDetails().getRating())
                                )
                        );
                        if (tvShowDetailResponse.getTvShowDetails().getGenres() != null) {
                            detailsBinding.setGenres(tvShowDetailResponse.getTvShowDetails().getGenres()[0]);
                        } else {
                            detailsBinding.setGenres("N/A");
                        }
                        detailsBinding.setRuntime(String.valueOf(tvShowDetailResponse.getTvShowDetails().getRuntime()) + " Min");
                        detailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                        detailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                        detailsBinding.layoutMisc.setVisibility(View.VISIBLE);

                        detailsBinding.btnWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(tvShowDetailResponse.getTvShowDetails().getUrl()));
                                startActivity(intent);
                            }
                        });

                        detailsBinding.btnWebsite.setVisibility(View.VISIBLE);
                        detailsBinding.btnEpisode.setVisibility(View.VISIBLE);


                        detailsBinding.btnEpisode.setOnClickListener(view -> {
                            Toast.makeText(this, "Show", Toast.LENGTH_SHORT).show();
                            String showName = tvShows.getName();
                            if (bottomSheetDialog == null) {
                                bottomSheetDialog = new BottomSheetDialog(TvShowDetailsActivity.this);
                                episodeLayoutBinding = DataBindingUtil.inflate(
                                        LayoutInflater.from(TvShowDetailsActivity.this)
                                        , R.layout.episode_layout,
                                        findViewById(R.id.episodeContainer),
                                        false);

                                bottomSheetDialog.setContentView(episodeLayoutBinding.getRoot());
                                episodeLayoutBinding.episodeRv.setAdapter(
                                        new EpisodesAdapter(tvShowDetailResponse.getTvShowDetails().getEpisodeList())
                                );
                                episodeLayoutBinding.episodeTitleText.setText(
                                        String.format(
                                                "Episodes | %s", showName
                                        ));

                                episodeLayoutBinding.imgClose.setOnClickListener(view1 -> {
                                    bottomSheetDialog.cancel();
                                });


                            }

                            //Optional section start -------//
                            FrameLayout frameLayout = bottomSheetDialog.findViewById(
                                    com.google.android.material.R.id.design_bottom_sheet
                            );
                            if (frameLayout != null) {
                                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                            Toast.makeText(this, "Show!", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.show();
                        });

                        //add to watchlist implementation
                        detailsBinding.imageWatchList.setOnClickListener(view -> new CompositeDisposable().add(tvShowDetailsViewModel.addToWatchList(tvShows)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    detailsBinding.imageWatchList.setImageResource(R.drawable.ic_check);
                                    Toast.makeText(TvShowDetailsActivity.this, "Added to Watchlist", Toast.LENGTH_SHORT).show();
                                })
                        ));
                        detailsBinding.imageWatchList.setVisibility(View.VISIBLE);
                        basicTvShowDetails();
                    }
                }

        );
    }

    private void loadImageSlider(String[] sliderImage) {
        detailsBinding.tvShowDetailsPager.setOffscreenPageLimit(1);
        detailsBinding.tvShowDetailsPager.setAdapter(new ImageSliderAdapter(sliderImage));
        detailsBinding.tvShowDetailsPager.setVisibility(View.VISIBLE);
        detailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(sliderImage.length);
        detailsBinding.tvShowDetailsPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });


    }

    private void setupSliderIndicator(int count) {
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 0, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(
                    ContextCompat.getDrawable
                            (getApplicationContext(), R.drawable.background_slider_indicator_inactive));

            indicator[i].setLayoutParams(layoutParams);
            detailsBinding.layoutIndicator.addView(indicator[i]);
        }
        detailsBinding.layoutIndicator.setVisibility(View.VISIBLE);
        setCurrentIndicator(0);

    }

    private void setCurrentIndicator(int position) {
        int childCount = detailsBinding.layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) detailsBinding.layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));

            }
        }
    }

    private void basicTvShowDetails() {
        String tvShowName = String.valueOf(tvShows.getName());
        String tvShowCountry = String.valueOf(tvShows.getCountry());
        String tvShowNetwork = String.valueOf(tvShows.getNetwork());
        String tvShowStatus = String.valueOf(tvShows.getStatus());
        String tvShowStartedDate = String.valueOf(tvShows.getStart_date());

        detailsBinding.setTvShowName(tvShowName);
        detailsBinding.setNetworkCountry(tvShowCountry + " " + tvShowNetwork);
        detailsBinding.setTvShowStatus(tvShowStatus);
        detailsBinding.setStartedDate(tvShowStartedDate);
        detailsBinding.textName.setVisibility(View.VISIBLE);
        detailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        detailsBinding.textStarted.setVisibility(View.VISIBLE);
        detailsBinding.textStatus.setVisibility(View.VISIBLE);
    }
}