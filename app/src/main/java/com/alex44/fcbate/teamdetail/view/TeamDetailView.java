package com.alex44.fcbate.teamdetail.view;

import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface TeamDetailView extends MvpView {

    void initPager(TeamItemType type, Long id);

    void showMessage(String message);

    void setPhoto(String url);

    void setFIO(String text);

    void setNum(String text);

    void setSpec(String text);

    void setParams(int age, String content);

    void setAgeOnly(int age);

    void setCountry(String text);

    void setCountryFromContent(String content);

    void updateFragments(TeamDetailDTO teamDetailDTO);
}
