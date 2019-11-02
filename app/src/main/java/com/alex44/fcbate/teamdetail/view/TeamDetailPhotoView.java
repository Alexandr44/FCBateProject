package com.alex44.fcbate.teamdetail.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface TeamDetailPhotoView extends MvpView {

    void showMessage(String message);

    void updateData();

    void initRV();
}
