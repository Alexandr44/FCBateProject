package com.alex44.fcbate.calendar.presenter;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.calendar.model.repo.ICalendarRepo;
import com.alex44.fcbate.calendar.view.CalendarItemView;
import com.alex44.fcbate.calendar.view.CalendarSubtitleView;
import com.alex44.fcbate.calendar.view.CalendarView;
import com.alex44.fcbate.common.navigation.Screens;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> {
    private final static int PREVIOUS_GAME_COUNT = 50;
    private final static int NEXT_GAME_COUNT = 30;
    private final static int DIRECTION_PREVIOUS = 0;
    private final static int DIRECTION_NEXT = 1;

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());
    private final SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd MMMM, E", Locale.getDefault());
    private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Inject
    protected Router router;

    @Inject
    protected ICalendarRepo repo;

    private Scheduler mainThreadScheduler;
    private Disposable disposable;

    @Getter
    private List<MatchDTO> data = new ArrayList<>();

    public CalendarPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
    }

    private void loadData() {
        disposable = Maybe.zip(repo.getGames(PREVIOUS_GAME_COUNT, DIRECTION_NEXT).defaultIfEmpty(new ArrayList<>()),
                                repo.getGames(NEXT_GAME_COUNT, DIRECTION_PREVIOUS).defaultIfEmpty(new ArrayList<>()),
                                        (nextMatchDTOs, prevMatchDTOs) -> {
                    final List<MatchDTO> matchDTOs = new ArrayList<>();
                    Collections.reverse(prevMatchDTOs);
                    matchDTOs.addAll(prevMatchDTOs);
                    matchDTOs.addAll(nextMatchDTOs);

                    String prevSubTitle = "";
                    for(int i=0;i<matchDTOs.size();i++) {
                        final MatchDTO matchDTO = matchDTOs.get(i);
                        final Date date = dateTimeFormat.parse(matchDTO.getDateStr());
                        final String subTitle = monthFormat.format(date);

                        if (prevSubTitle != null && !prevSubTitle.equals(subTitle)) {
                            prevSubTitle = subTitle;
                            matchDTOs.add(i++, new MatchDTO(subTitle));
                        }

                        Timber.d(subTitle);
                    }

                    return matchDTOs;
                })
                .observeOn(mainThreadScheduler)
                .subscribe(gamesDTOs -> {
                    data = gamesDTOs;
                    getViewState().updateData();
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

    public void bind(CalendarItemView holder) {
        final MatchDTO matchDTO = data.get(holder.getPos());
        holder.setLeftLogo(matchDTO.getLeftTeam().getLogoUrl());
        holder.setLeftTeamTitle(matchDTO.getLeftTeam().getTitle());
        holder.setRightLogo(matchDTO.getRightTeam().getLogoUrl());
        holder.setRightTeamTitle(matchDTO.getRightTeam().getTitle());
        holder.setChampTitle(matchDTO.getTournament().getTitle());
        holder.setTournamentLogo(matchDTO.getTournament().getTitleShort());
        if (matchDTO.getGoalsLeft() == 0 && matchDTO.getGoalsRight() == 0) {
            holder.setScore("V");
        }
        else {
            holder.setScore(matchDTO.getGoalsLeft()+" - "+matchDTO.getGoalsRight());
        }
        try {
            Date date = dateTimeFormat.parse(matchDTO.getDateStr());
            holder.setDate(dateOutFormat.format(date));
            holder.setTime(timeOutFormat.format(date));
        } catch (ParseException e) {
            Timber.e(e);
        }

    }

    public void bind(CalendarSubtitleView holder) {
        final MatchDTO matchDTO = data.get(holder.getPos());
        holder.setTitle(matchDTO.getDateStr());
    }

}
