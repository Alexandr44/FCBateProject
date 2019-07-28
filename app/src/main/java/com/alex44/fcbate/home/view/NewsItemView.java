package com.alex44.fcbate.home.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import org.jetbrains.annotations.NotNull;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface NewsItemView extends MvpView {

    int getPos();

    void setPhoto(@NotNull String photoUrl);

    void setTitle(@NotNull String title);

    void setDateTime(@NotNull String dateTime, boolean isInLastHour);

}
