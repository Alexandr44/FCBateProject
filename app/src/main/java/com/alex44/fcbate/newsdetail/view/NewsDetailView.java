package com.alex44.fcbate.newsdetail.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface NewsDetailView extends MvpView {

    void setPhoto(String photoUrl);

    void setText(String text);

    void setBrief(String text);

    void setDate(String text, boolean isInLastHour);

    void showMessage(String message);

}
