package com.alex44.fcbate.info.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface InfoView extends MvpView {

    void rate();

    void writeToDevelopers();

    void openSite();

    void openForum();

    void updateVersion();

    void showMessage(String message);

}
