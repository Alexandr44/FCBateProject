package com.alex44.fcbate.di.modules;

import android.widget.ImageView;

import com.alex44.fcbate.utils.model.IImageLoader;
import com.alex44.fcbate.utils.ui.GlideImageLoader;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {

    @Provides
    IImageLoader<ImageView> imageLoader() {
        return new GlideImageLoader();
    }

}
