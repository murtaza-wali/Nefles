package com.am.netfles.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowDetails {

    @SerializedName("id")
    private String id;

    @SerializedName("permalink")
    private String permalink;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("description_source")
    private String description_source;

    @SerializedName("start_date")
    private String start_date;


    @SerializedName("country")
    private String country;

    @SerializedName("status")
    private String status;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("network")
    private String network;


    @SerializedName("image_path")
    private String image_path;

    @SerializedName("image_thumbnail_path")
    private String image_thumbnail_path;

    @SerializedName("rating")
    private String rating;

    @SerializedName("rating_count")
    private String rating_count;


    @SerializedName("genres")
    private String[] genres;

    @SerializedName("pictures")
    private String[] pictures;

    @SerializedName("episodes")
    private List<Episode> episodeList;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getDescription_source() {
        return description_source;
    }

    public String getStart_date() {
        return start_date;
    }


    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getNetwork() {
        return network;
    }


    public String getImage_path() {
        return image_path;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public String getRating() {
        return rating;
    }

    public String getRating_count() {
        return rating_count;
    }


    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }
}
