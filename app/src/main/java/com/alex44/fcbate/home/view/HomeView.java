package com.alex44.fcbate.home.view;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;
import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface HomeView extends MvpView {

    void initMatchPager(List<MatchDTO> matchDTOS);

    void initNewsPager(List<NewsItemDTO> newsDTOS);

    void fillTable(List<TournamentInfoDTO> infoList);

    void showMessage(String message);

    void openFacebook();

    void openInstagram();

    void openTwitter();

    void openVk();

    void openYoutube();

    void openViber();
}
