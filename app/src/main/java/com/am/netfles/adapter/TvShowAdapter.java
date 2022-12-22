package com.am.netfles.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.am.netfles.databinding.ItemContainerTvShowBinding;
import com.am.netfles.listeners.TvShowListener;
import com.am.netfles.models.TvShows;
import com.am.netfles.R;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {


    private List<TvShows> tvShowsList;
    private LayoutInflater layoutInflater;

    private TvShowListener tvShowListener;

    public TvShowAdapter(List<TvShows> tvShowsList, TvShowListener tvShowListener) {
        this.tvShowsList = tvShowsList;
        this.tvShowListener = tvShowListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_tv_show, parent, false);
        return new TvShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {

        holder.bindTvShow(tvShowsList.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShowsList.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TvShowViewHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTvShow(TvShows tvShows) {
            itemContainerTvShowBinding.setTvShow(tvShows);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(view -> tvShowListener.onTvShowClicked(tvShows));

        }
    }
}
