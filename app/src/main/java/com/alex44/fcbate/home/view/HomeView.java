package com.alex44.fcbate.home.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface HomeView extends MvpView {

    void initMatchPager();

    void initNewsPager();

    void showMessage(String message);
}
