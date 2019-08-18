package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.common.model.ISystemInfo;
import com.alex44.fcbate.di.scopes.HomeScope;
import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.repo.HomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.home.model.repo.RoomHomeRepoCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {ApiModule.class, NetworkModule.class})
public class HomeModule {

    @HomeScope
    @Provides
    public IHomeRepo homeRepo(ISystemInfo systemInfo, IHomeSource homeSource, INetworkStatus networkStatus, @Named("Room") IHomeRepoCache homeRepoCache) {
        return new HomeRepo(systemInfo, homeSource, networkStatus, homeRepoCache);
    }

    @HomeScope
    @Provides
    public IHomeSource homeSource(Retrofit retrofit) {
        return retrofit.create(IHomeSource.class);
    }

    @HomeScope
    @Named("Room")
    @Provides
    public IHomeRepoCache roomHomeRepoCache() {
        return new RoomHomeRepoCache();
    }

}
