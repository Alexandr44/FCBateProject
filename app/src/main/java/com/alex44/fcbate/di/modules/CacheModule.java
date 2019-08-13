package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.home.model.repo.RoomHomeRepoCache;
import com.alex44.fcbate.news.model.repo.INewsRepoCache;
import com.alex44.fcbate.news.model.repo.RoomNewsRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepoCache;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepoCache;
import com.alex44.fcbate.tournament.model.repo.RoomTournamentRepoCahe;

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

    @Named("Room")
    @Provides
    public INewsRepoCache roomNewsRepoCache() {
        return new RoomNewsRepoCache();
    }

    @Named("Room")
    @Provides
    public ITournamentRepoCache tournamentRepoCache() {
        return new RoomTournamentRepoCahe();
    }
}
