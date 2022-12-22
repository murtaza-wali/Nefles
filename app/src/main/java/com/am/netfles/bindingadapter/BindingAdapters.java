package com.am.netfles.bindingadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.am.netfles.R;
import com.am.netfles.databinding.ItemContainerTvShowBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {


    @BindingAdapter("android:imageURL")
    public static void setImageURL(Context context, ImageView imageView, String url) {


        try {


            imageView.setAlpha(0f);
            Picasso.get().load(url).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();


                }

                @Override
                public void onError(Exception e) {
                }
            });

        } catch (Exception ignored) {

        }
    }

}
