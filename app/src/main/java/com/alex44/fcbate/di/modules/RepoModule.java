package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.repo.HomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.common.model.ISystemInfo;
import com.alex44.fcbate.newsdetail.model.api.INewsDetailSource;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepo;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SystemModule.class, ApiModule.class, NetworkModule.class, CacheModule.class})
public class RepoModule {

    @Provides
    public IHomeRepo homeRepo(ISystemInfo systemInfo, IHomeSource homeSource, INetworkStatus networkStatus, @Named("Room") IHomeRepoCache homeRepoCache) {
        return new HomeRepo(systemInfo, homeSource, networkStatus, homeRepoCache);
    }

    @Provides
    public INewsDetailRepo newsDetailRepo(INewsDetailSource newsDetailSource, @Named("Room")  INewsDetailRepoCache newsDetailRepoCache, INetworkStatus networkStatus) {
        return new NewsDetailRepo(newsDetailSource, newsDetailRepoCache, networkStatus);
    }

}
