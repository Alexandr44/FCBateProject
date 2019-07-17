package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.repo.HomeRepo;
import com.alex44.fcbate.home.view.HomeView;
import com.alex44.fcbate.home.view.MatchItemView;
import com.alex44.fcbate.utils.model.ISystemInfo;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import timber.log.Timber;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private HomeRepo homeRepo;

    private ISystemInfo systemInfo;

    private Scheduler mainThreadScheduler;

    private Disposable matchesDisposable;

    @Getter
    private MatchItemPresenter matchItemPresenter;

    public HomePresenter(Scheduler mainThreadScheduler, ISystemInfo systemInfo) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.systemInfo = systemInfo;
        homeRepo = new HomeRepo(systemInfo);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        matchItemPresenter = new MatchItemPresenter();
        initMatchPager();
    }

    void initMatchPager() {
        matchesDisposable = homeRepo.getMatches()
                .observeOn(mainThreadScheduler)
                .subscribe(matchDTOS -> {
                    matchItemPresenter.getMatches().clear();
                    matchItemPresenter.getMatches().addAll(matchDTOS);
                    getViewState().initMatchPager();
                }, throwable -> {
                    Timber.e(throwable);
                });
    }

    public void goToCalendarScreen() {
        Timber.d("ToDo: go to calendar");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!matchesDisposable.isDisposed())
            matchesDisposable.dispose();
    }

    private class MatchItemPresenter implements IMatchItemPresenter {

        private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        private final SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd MMMM, E", Locale.getDefault());
        private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        @Getter
        private List<MatchDTO> matches = new ArrayList<>();

        @Override
        public void update(MatchItemView view) {
            final MatchDTO matchDTO = matches.get(view.getPos());
            view.setLeftLogo(matchDTO.getLeftTeam().getLogoUrl());
            view.setLeftTeamTitle(matchDTO.getLeftTeam().getTitle());
            view.setRightLogo(matchDTO.getRightTeam().getLogoUrl());
            view.setRightTeamTitle(matchDTO.getRightTeam().getTitle());
            view.setChampTitle(matchDTO.getTournament().getTitle());
            view.setTournamentLogo(matchDTO.getTournament().getTitleShort());
            if (matchDTO.getGoalsLeft() == 0 && matchDTO.getGoalsRight() == 0) {
                view.setScore("V");
            }
            else {
                view.setScore(matchDTO.getGoalsLeft()+" - "+matchDTO.getGoalsRight());
            }
            try {
                Date date = dateTimeFormat.parse(matchDTO.getDateStr());
                view.setDate(dateOutFormat.format(date));
                view.setTime(timeOutFormat.format(date));
            } catch (ParseException e) {
                Timber.e(e);
            }
        }
    }

}
