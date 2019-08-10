package com.alex44.fcbate.news.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.news.model.repo.INewsRepo;
import com.alex44.fcbate.news.view.NewsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {

    @Inject
    protected Router router;

    private Scheduler mainThreadScheduler;

    @Inject
    protected INewsRepo newsRepo;

    private Disposable newsDisposable;
    private Disposable pressDisposable;
    private Disposable declarationDisposable;

    public NewsPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        update();
    }

    private void update() {
        newsDisposable = newsRepo.getNews()
                .observeOn(mainThreadScheduler)
                .subscribe(newsDTOS -> {
                    Timber.d("Result");
                    Timber.d("TODO");
                }, throwable -> {
                    getViewState().showMessage("News load failed");
                    Timber.e(throwable);
                });

        pressDisposable = newsRepo.getPresses()
                .observeOn(mainThreadScheduler)
                .subscribe(pressDTOS -> {
                    Timber.d("Result");
                    Timber.d("TODO");
                }, throwable -> {
                    getViewState().showMessage("Press load failed");
                    Timber.e(throwable);
                });

        declarationDisposable = newsRepo.getDeclarations()
                .observeOn(mainThreadScheduler)
                .subscribe(declarationsDTOS -> {
                    Timber.d("Result");
                    Timber.d("TODO");
                }, throwable -> {
                    getViewState().showMessage("Declaration load failed");
                    Timber.e(throwable);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (newsDisposable != null && !newsDisposable.isDisposed()) {
            newsDisposable.dispose();
        }
        if (pressDisposable != null && !pressDisposable.isDisposed()) {
            pressDisposable.dispose();
        }
        if (declarationDisposable != null && !declarationDisposable.isDisposed()) {
            declarationDisposable.dispose();
        }
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

}
