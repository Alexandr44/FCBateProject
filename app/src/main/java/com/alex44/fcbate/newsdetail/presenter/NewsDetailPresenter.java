package com.alex44.fcbate.newsdetail.presenter;

import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepo;
import com.alex44.fcbate.newsdetail.view.NewsDetailView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

import static com.alex44.fcbate.common.model.DateFormatUtil.getFormattedDateStr;

@InjectViewState
public class NewsDetailPresenter extends MvpPresenter<NewsDetailView> {

    @Inject
    protected Router router;

    private NewsItemType type;
    private Long itemId;

    @Inject
    protected INewsDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    public NewsDetailPresenter(NewsItemType type, Long itemId, Scheduler mainThreadScheduler) {
        this.type = type;
        this.itemId = itemId;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {
        if (itemId == null) {
            Timber.e("News id not found");
            getViewState().showMessage("News id not found");
            return;
        }
        if (type == null) {
            Timber.e("Type not found");
            getViewState().showMessage("Type not found");
            return;
        }

        Maybe<NewsItemDetailDTO> maybe = null;
        switch (type) {
            case NEWS:
                maybe = repo.getNewsDetail(itemId);
                break;
            case PRESS:
                maybe = repo.getPressDetail(itemId);
                break;
            case DECLARATION:
                maybe = repo.getDeclarationDetail(itemId);
                break;
        }
        if (maybe == null) return;

        disposable = maybe.observeOn(mainThreadScheduler)
                .subscribe(itemDetailDTO -> {
                    Timber.d(itemDetailDTO.toString());
                    update(itemDetailDTO);
                }, throwable -> {
                    getViewState().showMessage("News detail load failed");
                    Timber.e(throwable);
                });
    }

    private void update(NewsItemDetailDTO itemDetailDTO) {
        getViewState().setPhoto(itemDetailDTO.getPhotoUrl());
        getViewState().setText(itemDetailDTO.getContent());
        getViewState().setBrief(itemDetailDTO.getBrief());
        final String dateStr = getFormattedDateStr(itemDetailDTO.getDateCreated());
        getViewState().setDate(dateStr, dateStr.contains("назад"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void backClick() {
        router.exit();
    }

}
