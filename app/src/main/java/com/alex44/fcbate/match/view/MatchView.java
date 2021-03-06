package com.alex44.fcbate.match.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import org.jetbrains.annotations.NotNull;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MatchView extends MvpView {

    void initPager();

    void showMessage(String message);

    void setLeftLogo(@NotNull String logoUrl);

    void setRightLogo(@NotNull String logoUrl);

    void setLeftTeamTitle(@NotNull String title);

    void setRightTeamTitle(@NotNull String title);

    void setChampTitle(@NotNull String title);

    void setScore(@NotNull String score);

    void setDate(@NotNull String date);

    void setTime(@NotNull String time);

    void setTournamentLogo(@NotNull String tournamentShortName);
}
