package com.am.netfles.responses;

import com.am.netfles.models.TvShows;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int total_pages;

    @SerializedName("tv_shows")
    private List<TvShows> tvShowsList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<TvShows> getTvShowsList() {
        return tvShowsList;
    }

    public void setTvShowsList(List<TvShows> tvShowsList) {
        this.tvShowsList = tvShowsList;
    }
}
