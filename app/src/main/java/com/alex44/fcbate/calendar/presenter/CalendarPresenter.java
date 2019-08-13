package com.alex44.fcbate.calendar.presenter;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepo;
import com.alex44.fcbate.calendar.view.CalendarView;
import com.alex44.fcbate.common.navigation.Screens;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> {
    private final static int PREVIOUS_GAME_COUNT = 10;
    private final static int NEXT_GAME_COUNT = 15;
    private final static int DIRECTION_PREVIOUS = 0;
    private final static int DIRECTION_NEXT = 1;

    @Inject
    protected Router router;

    @Inject
    protected ICalendarRepo repo;

    private Scheduler mainThreadScheduler;
    private Disposable disposable;

    public CalendarPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        disposable = repo.getGames(PREVIOUS_GAME_COUNT, DIRECTION_PREVIOUS)
                .zipWith(repo.getGames(NEXT_GAME_COUNT, DIRECTION_NEXT), (prevMatchDTOs, nextMatchDTOs) -> {
                    final List<MatchDTO> matchDTOs = new ArrayList<>();
                    Collections.reverse(prevMatchDTOs);
                    matchDTOs.addAll(prevMatchDTOs);
                    matchDTOs.addAll(nextMatchDTOs);
                    return matchDTOs;
                })
                .observeOn(mainThreadScheduler)
                .subscribe(gamesDTOs -> {
                    Timber.d("Result");
                }, throwable -> {
                    getViewState().showMessage("Games load failed");
                    Timber.e(throwable);
                });
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
