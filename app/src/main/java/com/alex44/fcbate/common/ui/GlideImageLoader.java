package com.alex44.fcbate.common.ui;

import android.widget.ImageView;

import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.glidemodule.GlideLoader;

public class GlideImageLoader implements IImageLoader<ImageView> {

    @Override
    public void loadInto(String url, ImageView container, int corners) {
        new GlideLoader().loadInto(url, container, corners);
    }

    @Override
    public void loadInto(String url, ImageView container) {
        new GlideLoader().loadInto(url, container);
    }

    @Override
    public void loadIntoWithCrop(String url, ImageView container, int corners) {
        new GlideLoader().loadIntoWithCrop(url, container, corners);
    }
}
