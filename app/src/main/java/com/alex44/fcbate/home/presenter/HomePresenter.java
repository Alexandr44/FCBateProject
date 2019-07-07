package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.view.HomeView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }
}
