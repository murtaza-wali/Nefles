package com.am.netfles.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.am.netfles.R;
import com.am.netfles.databinding.ItemContainerEpisodeBinding;
import com.am.netfles.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewModel> {

    private List<Episode> episodeList;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodeBinding itemContainerEpisodeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episode, parent, false);
        return new EpisodeViewModel(itemContainerEpisodeBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull EpisodeViewModel holder, int position) {


        holder.bindEpisode(episodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class EpisodeViewModel extends RecyclerView.ViewHolder {

        ItemContainerEpisodeBinding itemContainerEpisodeBinding;

        public EpisodeViewModel(ItemContainerEpisodeBinding itemContainerEpisodeBinding) {
            super(itemContainerEpisodeBinding.getRoot());
            this.itemContainerEpisodeBinding = itemContainerEpisodeBinding;
        }

        public void bindEpisode(Episode episode) {
            String title = "S";
            String season = episode.getSeason();
            if (season.length() == 1) {
                season = "0".concat(season);
            }

            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            itemContainerEpisodeBinding.setTitle(title);
            itemContainerEpisodeBinding.setName(episode.getName());
            itemContainerEpisodeBinding.setAirDate(episode.getAir_date());
        }
    }
}
