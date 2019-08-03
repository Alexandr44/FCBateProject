package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.view.MatchItemView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

@InjectViewState
public class MatchItemPresenter extends MvpPresenter<MatchItemView> {

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    private final SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd MMMM, E", Locale.getDefault());
    private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    
    private MatchDTO matchDTO;

    public MatchItemPresenter(MatchDTO matchDTO) {
        this.matchDTO = matchDTO;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        update();
    }

    public void update() {
        if (matchDTO == null) return;
        getViewState().setLeftLogo(matchDTO.getLeftTeam().getLogoUrl());
        getViewState().setLeftTeamTitle(matchDTO.getLeftTeam().getTitle());
        getViewState().setRightLogo(matchDTO.getRightTeam().getLogoUrl());
        getViewState().setRightTeamTitle(matchDTO.getRightTeam().getTitle());
        getViewState().setChampTitle(matchDTO.getTournament().getTitle());
        getViewState().setTournamentLogo(matchDTO.getTournament().getTitleShort());
        if (matchDTO.getGoalsLeft() == 0 && matchDTO.getGoalsRight() == 0) {
            getViewState().setScore("V");
        }
        else {
            getViewState().setScore(matchDTO.getGoalsLeft()+" - "+matchDTO.getGoalsRight());
        }
        try {
            Date date = dateTimeFormat.parse(matchDTO.getDateStr());
            getViewState().setDate(dateOutFormat.format(date));
            getViewState().setTime(timeOutFormat.format(date));
        } catch (ParseException e) {
            Timber.e(e);
        }
    }

}
