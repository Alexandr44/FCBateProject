package com.alex44.fcbate.main.presenter;

import com.alex44.fcbate.main.view.MainView;
import com.alex44.fcbate.utils.api.ApiHolder;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter  extends MvpPresenter<MainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        getViewState().goToHomeScreen();
        ApiHolder.getInstance().init();
    }

    public void selectedHome() {
        getViewState().goToHomeScreen();
    }

    public void selectedNews() {
        getViewState().goToNewsScreen();
    }

    public void selectedCalendar() {
        getViewState().goToCalendarScreen();
    }

    public void selectedTable() {
        getViewState().goToTableScreen();
    }

    public void selectedTeam() {
        getViewState().goToTeamScreen();
    }

    public void selectedClub() {
        getViewState().goToClubScreen();
    }

    public void selectedApp() {
        getViewState().goToAppScreen();
    }
}
