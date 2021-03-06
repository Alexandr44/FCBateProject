package com.alex44.fcbate.calendar.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface CalendarView extends MvpView {

    void showMessage(String message);

    void init();

    void updateData();

}
