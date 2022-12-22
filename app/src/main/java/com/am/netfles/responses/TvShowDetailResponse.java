package com.am.netfles.responses;

import com.am.netfles.models.TvShowDetails;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailResponse {


    @SerializedName("tvShow")
    private TvShowDetails tvShowDetails;

    public TvShowDetails getTvShowDetails() {
        return tvShowDetails;
    }

    public void setTvShowDetails(TvShowDetails tvShowDetails) {
        this.tvShowDetails = tvShowDetails;
    }
}
