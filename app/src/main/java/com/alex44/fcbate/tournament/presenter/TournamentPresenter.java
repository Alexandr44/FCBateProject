package com.alex44.fcbate.tournament.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.tournament.view.TournamentView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TournamentPresenter extends MvpPresenter<TournamentView> {

    @Inject
    protected Router router;

    private Scheduler mainThreadScheduler;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public TournamentPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

}
