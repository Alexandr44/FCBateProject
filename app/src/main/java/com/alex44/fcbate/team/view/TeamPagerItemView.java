package com.alex44.fcbate.team.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface TeamPagerItemView extends MvpView {

    void init();

    void updateData();

    void showMessage(String message);

}
