package com.alex44.fcbate.main.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.main.view.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter  extends MvpPresenter<MainView> {

    @Inject
    protected Router router;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        goToHomeScreen();
    }
    
    public void goToHomeScreen() {
        router.navigateTo(new Screens.HomeScreen());
    }

    public void goToNewsScreen() {
        router.navigateTo(new Screens.NewsScreen());
    }

    public void goToCalendarScreen() {
    }

    public void goToTableScreen() {
    }

    public void goToTeamScreen() {
    }

    public void goToClubScreen() {
    }

    public void goToAppScreen() {
    }
}
