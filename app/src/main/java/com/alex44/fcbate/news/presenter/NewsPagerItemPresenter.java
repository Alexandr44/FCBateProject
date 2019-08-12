package com.alex44.fcbate.news.presenter;

import com.alex44.fcbate.common.model.DateFormatUtil;
import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.alex44.fcbate.news.model.repo.INewsRepo;
import com.alex44.fcbate.news.view.NewsPagerItemView;
import com.alex44.fcbate.news.view.NewsRVItemView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsPagerItemPresenter extends MvpPresenter<NewsPagerItemView> {

    private Scheduler mainThreadScheduler;
    private NewsItemType type;

    @Inject
    protected INewsRepo newsRepo;

    @Inject
    protected Router router;

    private Disposable disposable;
    private Disposable clickDisposable;

    @Getter
    private List<NewsItemDTO> data = new ArrayList<>();
    @Getter
    private PublishSubject<NewsRVItemView> clickSubject = PublishSubject.create();

    public NewsPagerItemPresenter(Scheduler mainThreadScheduler, NewsItemType type) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.type = type;
    }

    public void bind(NewsRVItemView view) {
        final NewsItemDTO itemDTO = data.get(view.getPos());
        view.setPhoto(itemDTO.getPhotoUrl());
        view.setTitle(itemDTO.getTitle());
        final String dateStr = DateFormatUtil.getFormattedDateStr(itemDTO.getCreated());
        view.setDateTime(dateStr, dateStr.contains("назад"));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
        processClicks();
    }

    private void processClicks() {
        clickDisposable = clickSubject.subscribe(newsRVItemView -> {
            final NewsItemDTO itemDTO = data.get(newsRVItemView.getPos());
            router.navigateTo(new Screens.NewsDetailScreen(type, itemDTO.getId()));
        });
    }

    private void loadData() {
        Maybe<List<NewsItemDTO>> maybe = null;
        switch (type) {
            case NEWS:
                maybe = newsRepo.getNews();
                break;
            case PRESS:
                maybe = newsRepo.getPresses();
                break;
            case DECLARATION:
                maybe = newsRepo.getDeclarations();
                break;
        }
        if (maybe == null) return;

        disposable = maybe.observeOn(mainThreadScheduler)
                .subscribe(newsDTOS -> {
                    Timber.d("Result");
                    data = newsDTOS;
                    getViewState().updateData();
                }, throwable -> {
                    getViewState().showMessage(type.toString()+" load failed");
                    Timber.e(throwable);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (clickDisposable != null && !clickDisposable.isDisposed()) {
            clickDisposable.dispose();
        }
    }

}
