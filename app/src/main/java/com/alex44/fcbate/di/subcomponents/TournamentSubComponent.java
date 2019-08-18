package com.alex44.fcbate.di.subcomponents;

import com.alex44.fcbate.di.modules.TournamentModule;
import com.alex44.fcbate.di.scopes.TournamentScope;
import com.alex44.fcbate.tournament.presenter.TournamentPresenter;

import dagger.Subcomponent;

@TournamentScope
@Subcomponent(modules = {TournamentModule.class})
public interface TournamentSubComponent {

    void inject(TournamentPresenter tournamentPresenter);

}
