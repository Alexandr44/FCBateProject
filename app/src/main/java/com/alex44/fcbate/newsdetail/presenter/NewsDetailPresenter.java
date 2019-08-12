package com.alex44.fcbate.newsdetail.presenter;

import com.alex44.fcbate.newsdetail.model.dto.NewsDetailDTO;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepo;
import com.alex44.fcbate.newsdetail.view.NewsDetailView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

import static com.alex44.fcbate.common.model.DateFormatUtil.getFormattedDateStr;

@InjectViewState
public class NewsDetailPresenter extends MvpPresenter<NewsDetailView> {

    @Inject
    protected Router router;

    private Long newsId;

    @Inject
    protected INewsDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    public NewsDetailPresenter(Long newsId, Scheduler mainThreadScheduler) {
        this.newsId = newsId;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {
        if (newsId == null) {
            Timber.e("News id not found");
            getViewState().showMessage("News id not found");
            return;
        }
        disposable = repo.getNewsDetail(newsId)
                .observeOn(mainThreadScheduler)
                .subscribe(newsDetailDTO -> {
                    Timber.d(newsDetailDTO.toString());
                    update(newsDetailDTO);
                }, throwable -> {
                    getViewState().showMessage("News detail load failed");
                    Timber.e(throwable);
                });

    }

    private void update(NewsDetailDTO newsDetailDTO) {
        getViewState().setPhoto(newsDetailDTO.getPhotoUrl());
        getViewState().setText(newsDetailDTO.getContent());
        getViewState().setBrief(newsDetailDTO.getBrief());
        final String dateStr = getFormattedDateStr(newsDetailDTO.getDateCreated());
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
