package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.common.model.DateFormatUtil;
import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.home.view.NewsItemView;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsItemPresenter extends MvpPresenter<NewsItemView> {

    @Inject
    protected Router router;

    private NewsItemDTO newsDTO;

    public NewsItemPresenter(NewsItemDTO newsDTO) {
        this.newsDTO = newsDTO;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        update();
    }

    public void update() {
        getViewState().setPhoto(newsDTO.getPhotoUrl());
        getViewState().setTitle(newsDTO.getTitle());
        final String dateStr = DateFormatUtil.getFormattedDateStr(newsDTO.getCreated());
        getViewState().setDateTime(dateStr, dateStr.contains("назад"));
    }

    public void imageClicked() {
        Timber.d("Go to detail news with id:" + newsDTO.getId() + ": "+newsDTO.getTitle());
        router.navigateTo(new Screens.NewsDetailScreen(NewsItemType.NEWS, newsDTO.getId()));
    }

}
