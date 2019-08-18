package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.di.scopes.NewsScope;
import com.alex44.fcbate.news.model.api.INewsSource;
import com.alex44.fcbate.news.model.repo.INewsRepo;
import com.alex44.fcbate.news.model.repo.INewsRepoCache;
import com.alex44.fcbate.news.model.repo.NewsRepo;
import com.alex44.fcbate.news.model.repo.RoomNewsRepoCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {ApiModule.class, NetworkModule.class})
public class NewsModule {

    @NewsScope
    @Provides
    public INewsRepo newsRepo(INewsSource newsSource, INetworkStatus networkStatus, @Named("Room") INewsRepoCache repoCache) {
        return new NewsRepo(newsSource, networkStatus, repoCache);
    }

    @NewsScope
    @Provides
    public INewsSource newsSource(Retrofit retrofit) {
        return retrofit.create(INewsSource.class);
    }

    @NewsScope
    @Named("Room")
    @Provides
    public INewsRepoCache roomNewsRepoCache() {
        return new RoomNewsRepoCache();
    }

}
