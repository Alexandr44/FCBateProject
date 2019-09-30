package com.alex44.fcbate.team.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.team.view.TeamView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class TeamPresenter extends MvpPresenter<TeamView> {

    @Inject
    protected Router router;

    public TeamPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {
        getViewState().initPager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

}
