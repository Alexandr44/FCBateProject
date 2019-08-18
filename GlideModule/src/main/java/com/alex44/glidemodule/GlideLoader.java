package com.alex44.glidemodule;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideLoader {

    public void loadInto(String url, ImageView container, int corners) {
        final MultiTransformation<Bitmap> multi = new MultiTransformation<>(
                new RoundedCorners(corners)
        );

        Glide.with(container.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(container);
    }

    public void loadInto(String url, ImageView container) {
        Glide.with(container.getContext())
                .load(url)
                .into(container);
    }

    public void loadIntoWithCrop(String url, ImageView container, int corners) {
        final MultiTransformation<Bitmap> multi = new MultiTransformation<>(
                new CenterCrop(), new RoundedCorners(corners)
        );

        Glide.with(container.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(container);
    }
}
