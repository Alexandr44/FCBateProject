package com.alex44.fcbate.tournament.presenter;

import com.alex44.fcbate.App;
import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.tournament.model.repo.ITournamentRepo;
import com.alex44.fcbate.tournament.view.TournamentRVItemView;
import com.alex44.fcbate.tournament.view.TournamentView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
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

    @Getter
    private List<TournamentInfoDTO> data = new ArrayList<>();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
    }

    private void loadData() {
        disposable = repo.getTournamentTable()
                .observeOn(mainThreadScheduler)
                .subscribe(tournamentInfoDTOS -> {
                    data = tournamentInfoDTOS;
                    getViewState().update();
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
        App.getInstance().getComponentManager().clearTournamentSubComponent();
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

    public void bind(TournamentRVItemView viewHolder) {
        final TournamentInfoDTO infoDTO = data.get(viewHolder.getPos());
        viewHolder.setPosition(infoDTO.getPosition().toString());
        viewHolder.setTeam(infoDTO.getTeamName());
        viewHolder.setGames(infoDTO.getGames().toString());
        viewHolder.setDiffs(infoDTO.getDiffs());
        viewHolder.setPoints(infoDTO.getPoints().toString());
        if (infoDTO.getPosition() % 2 != 0) {
            viewHolder.highlightBackground();
        }
    }
}
