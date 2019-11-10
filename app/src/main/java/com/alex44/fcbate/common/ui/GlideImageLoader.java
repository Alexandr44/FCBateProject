package com.alex44.fcbate.common.ui;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.alex44.fcbate.common.model.IImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideImageLoader implements IImageLoader<ImageView> {

    @Override
    public void loadInto(String url, ImageView container, int corners) {
        final MultiTransformation<Bitmap> multi = new MultiTransformation<>(
                new RoundedCorners(corners)
        );

        Glide.with(container.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(container);
    }

    @Override
    public void loadInto(String url, ImageView container) {
        Glide.with(container.getContext())
                .load(url)
                .into(container);
    }

    @Override
    public void loadIntoWithPlaceholder(String url, ImageView container, int photoPlaceholder) {
        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(photoPlaceholder);

        Glide.with(container.getContext())
                .load(url)
                .apply(requestOptions)
                .into(container);
    }

    @Override
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
