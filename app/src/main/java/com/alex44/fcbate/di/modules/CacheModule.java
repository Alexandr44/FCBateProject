package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.home.model.repo.RoomHomeRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepoCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Named("Room")
    @Provides
    public IHomeRepoCache roomHomeRepoCache() {
        return new RoomHomeRepoCache();
    }

    @Named("Room")
    @Provides
    public INewsDetailRepoCache roomNewsDetailRepoCache() {
        return new NewsDetailRepoCache();
    }

}
