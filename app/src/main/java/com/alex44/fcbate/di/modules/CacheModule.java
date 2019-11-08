package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.about.model.repo.IAboutRepoCache;
import com.alex44.fcbate.about.model.repo.RoomAboutRepoCache;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepoCache;
import com.alex44.fcbate.calendar.model.repo.RoomCalendarRepoCache;
import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.home.model.repo.RoomHomeRepoCache;
import com.alex44.fcbate.news.model.repo.INewsRepoCache;
import com.alex44.fcbate.news.model.repo.RoomNewsRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepoCache;
import com.alex44.fcbate.team.model.repo.ITeamRepoCache;
import com.alex44.fcbate.team.model.repo.RoomTeamRepoCache;
import com.alex44.fcbate.teamdetail.model.repo.ITeamDetailRepoCache;
import com.alex44.fcbate.teamdetail.model.repo.RoomTeamDetailRepoCache;
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

    @Named("Room")
    @Provides
    public ICalendarRepoCache calendarRepoCache() {
        return new RoomCalendarRepoCache();
    }

    @Named("Room")
    @Provides
    public IAboutRepoCache aboutRepoCache() {
        return new RoomAboutRepoCache();
    }

    @Named("Room")
    @Provides
    public ITeamRepoCache teamRepoCache() {
        return new RoomTeamRepoCache();
    }

    @Named("Room")
    @Provides
    public ITeamDetailRepoCache teamDetailRepoCache() {
        return new RoomTeamDetailRepoCache();
    }
}
