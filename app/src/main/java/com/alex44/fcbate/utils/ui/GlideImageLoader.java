package com.alex44.fcbate.utils.ui;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.alex44.fcbate.utils.model.IImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideImageLoader implements IImageLoader<ImageView> {

//    @Override
//    public void loadInto(String url, ImageView container) {
//        Glide.with(container.getContext())
//                .asBitmap()
//                .load(url)
//                .listener(new RequestListener<Bitmap>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(container);
//    }

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
}
