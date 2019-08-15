package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.calendar.model.api.ICalendarSource;
import com.alex44.fcbate.calendar.model.repo.CalendarRepo;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepo;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepoCache;
import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.common.model.ISystemInfo;
import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.repo.HomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.news.model.api.INewsSource;
import com.alex44.fcbate.news.model.repo.INewsRepo;
import com.alex44.fcbate.news.model.repo.INewsRepoCache;
import com.alex44.fcbate.news.model.repo.NewsRepo;
import com.alex44.fcbate.newsdetail.model.api.INewsDetailSource;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepo;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepoCache;
import com.alex44.fcbate.newsdetail.model.repo.NewsDetailRepo;
import com.alex44.fcbate.tournament.model.api.ITournamentSource;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepo;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepoCache;
import com.alex44.fcbate.tournament.model.repo.TournamentRepo;

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
    public INewsDetailRepo newsDetailRepo(INewsDetailSource newsDetailSource, @Named("Room") INewsDetailRepoCache newsDetailRepoCache, INetworkStatus networkStatus) {
        return new NewsDetailRepo(newsDetailSource, newsDetailRepoCache, networkStatus);
    }

    @Provides
    public INewsRepo newsRepo(INewsSource newsSource, INetworkStatus networkStatus, @Named("Room") INewsRepoCache repoCache) {
        return new NewsRepo(newsSource, networkStatus, repoCache);
    }

    @Provides
    public ITournamentRepo tournamentRepo(ITournamentSource source, INetworkStatus networkStatus, @Named("Room") ITournamentRepoCache tournamentRepoCache) {
        return new TournamentRepo(source, networkStatus, tournamentRepoCache);
    }

    @Provides
    public ICalendarRepo calendarRepo(ICalendarSource source, INetworkStatus networkStatus, @Named("Room")ICalendarRepoCache repoCache) {
        return new CalendarRepo(source, networkStatus, repoCache);
    }

}
