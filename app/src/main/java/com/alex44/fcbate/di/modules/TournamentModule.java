package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.di.scopes.TournamentScope;
import com.alex44.fcbate.tournament.model.api.ITournamentSource;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepo;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepoCache;
import com.alex44.fcbate.tournament.model.repo.RoomTournamentRepoCahe;
import com.alex44.fcbate.tournament.model.repo.TournamentRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {ApiModule.class, NetworkModule.class})
public class TournamentModule {

    @TournamentScope
    @Provides
    public ITournamentRepo tournamentRepo(ITournamentSource source, INetworkStatus networkStatus, @Named("Room") ITournamentRepoCache tournamentRepoCache) {
        return new TournamentRepo(source, networkStatus, tournamentRepoCache);
    }

    @TournamentScope
    @Provides
    public ITournamentSource tournamentSource(Retrofit retrofit) {
        return retrofit.create(ITournamentSource.class);
    }

    @TournamentScope
    @Named("Room")
    @Provides
    public ITournamentRepoCache tournamentRepoCache() {
        return new RoomTournamentRepoCahe();
    }

}
