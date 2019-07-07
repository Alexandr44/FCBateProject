package com.alex44.fcbate.main.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void init();

    void goToHomeScreen();

    void goToNewsScreen();

    void goToCalendarScreen();

    void goToTableScreen();

    void goToTeamScreen();

    void goToClubScreen();

    void goToAppScreen();

}
