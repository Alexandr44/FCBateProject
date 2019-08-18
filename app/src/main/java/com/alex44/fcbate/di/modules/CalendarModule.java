package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.calendar.model.api.ICalendarSource;
import com.alex44.fcbate.calendar.model.repo.CalendarRepo;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepo;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepoCache;
import com.alex44.fcbate.calendar.model.repo.RoomCalendarRepoCache;
import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.di.scopes.CalendarScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {ApiModule.class, NetworkModule.class})
public class CalendarModule {

    @CalendarScope
    @Provides
    public ICalendarRepo calendarRepo(ICalendarSource source, INetworkStatus networkStatus, @Named("Room") ICalendarRepoCache repoCache) {
        return new CalendarRepo(source, networkStatus, repoCache);
    }

    @CalendarScope
    @Provides
    public ICalendarSource calendarSource(Retrofit retrofit) {
        return retrofit.create(ICalendarSource.class);
    }

    @CalendarScope
    @Named("Room")
    @Provides
    public ICalendarRepoCache calendarRepoCache() {
        return new RoomCalendarRepoCache();
    }

}
