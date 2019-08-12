package com.alex44.fcbate.tournament.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepo;
import com.alex44.fcbate.tournament.view.TournamentView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class TournamentPresenter extends MvpPresenter<TournamentView> {

    @Inject
    protected Router router;

    @Inject
    protected ITournamentRepo repo;

    private Scheduler mainThreadScheduler;
    private Disposable disposable;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        disposable = repo.getTournamentTable()
                .observeOn(mainThreadScheduler)
                .subscribe(tournamentInfoDTOS -> {
                    Timber.d("Result");
                }, throwable -> {
                    getViewState().showMessage("Tournaments table load failed");
                    Timber.e(throwable);
                });
    }

    public TournamentPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

}
