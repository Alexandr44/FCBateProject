package com.alex44.fcbate.news.presenter;

import com.alex44.fcbate.App;
import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.news.view.NewsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {

    @Inject
    protected Router router;

    public NewsPresenter() {
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
        App.getInstance().getComponentManager().clearNewsSubComponent();
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

}
