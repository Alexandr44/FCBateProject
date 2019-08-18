package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.di.scopes.NewsDetailScope;
import com.alex44.fcbate.newsdetail.model.api.INewsDetailSource;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepo;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepo;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepoCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {ApiModule.class, NetworkModule.class})
public class NewsDetailModule {

    @NewsDetailScope
    @Provides
    public INewsDetailRepo newsDetailRepo(INewsDetailSource newsDetailSource, @Named("Room") INewsDetailRepoCache newsDetailRepoCache, INetworkStatus networkStatus) {
        return new NewsDetailRepo(newsDetailSource, newsDetailRepoCache, networkStatus);
    }

    @NewsDetailScope
    @Provides
    public INewsDetailSource newsDetailSource(Retrofit retrofit) {
        return retrofit.create(INewsDetailSource.class);
    }

    @NewsDetailScope
    @Named("Room")
    @Provides
    public INewsDetailRepoCache roomNewsDetailRepoCache() {
        return new NewsDetailRepoCache();
    }

}
