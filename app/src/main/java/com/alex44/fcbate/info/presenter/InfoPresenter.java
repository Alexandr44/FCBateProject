package com.alex44.fcbate.info.presenter;

import com.alex44.fcbate.about.model.repo.IAboutRepo;
import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.info.view.InfoView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {

    @Inject
    protected Router router;

    @Inject
    protected IAboutRepo repo;

    public InfoPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {
        getViewState().updateVersion();
        getViewState().countAppCacheSize();
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

    public void siteClicked() {
        getViewState().openSite();
    }

    public void forumClicked() {
        getViewState().openForum();
    }

    public void rateClicked() {
        getViewState().rate();
    }

    public void writeClicked() {
        getViewState().writeToDevelopers();
    }

    public void cleanCacheClicked() {
        getViewState().cleanAppCache();
    }

    public void cacheSizeCalculated(String cacheSize) {
        getViewState().updateCacheSize(cacheSize);
    }

    public void cleanFinished() {
        getViewState().countAppCacheSize();
    }
}
