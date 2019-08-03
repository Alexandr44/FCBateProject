package com.alex44.fcbate.di.modules;

import android.widget.ImageView;

import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.common.ui.GlideImageLoader;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {

    @Named("Glide")
    @Provides
    IImageLoader<ImageView> imageLoader() {
        return new GlideImageLoader();
    }

}
