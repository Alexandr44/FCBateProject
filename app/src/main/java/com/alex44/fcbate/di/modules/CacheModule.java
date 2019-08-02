package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.home.model.repo.RoomHomeRepoCache;

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

}
