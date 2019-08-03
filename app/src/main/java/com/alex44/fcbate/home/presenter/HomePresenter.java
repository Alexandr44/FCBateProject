package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.view.HomeView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    @Inject
    protected IHomeRepo homeRepo;

    private Scheduler mainThreadScheduler;

    private Disposable matchesDisposable;
    private Disposable newsDisposable;
    private Disposable tournamentsDisposable;

    public HomePresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initMatchPager();
        initNewsPager();
        initTournamentsTable();
    }

    public void initMatchPager() {
        matchesDisposable = homeRepo.getMatches()
                .observeOn(mainThreadScheduler)
                .subscribe(matchDTOS -> {
                    if (matchDTOS.size() > 5) {
                        matchDTOS.subList(5, matchDTOS.size()).clear();
                    }
                    getViewState().initMatchPager(matchDTOS);
                }, throwable -> {
                    getViewState().showMessage("Matches load failed");
                    Timber.e(throwable);
                });
    }

    public void initNewsPager() {
        newsDisposable = homeRepo.getNews()
                .observeOn(mainThreadScheduler)
                .subscribe(newsDTOS -> {
                    if (newsDTOS.size() > 5) {
                        newsDTOS.subList(5, newsDTOS.size()).clear();
                    }
                    getViewState().initNewsPager(newsDTOS);
                }, throwable -> {
                    getViewState().showMessage("News load failed");
                    Timber.e(throwable);
                });
    }

    public void initTournamentsTable() {
        tournamentsDisposable = homeRepo.getTournamentsInfo()
                .observeOn(mainThreadScheduler)
                .subscribe(infoList -> {
                    if (infoList.size() >= 3) {
                        getViewState().fillTable(infoList);
                    }
                }, throwable -> {
                    getViewState().showMessage("Tournaments info load failed");
                    Timber.e(throwable);
                });
    }

    public void goToCalendarScreen() {
        Timber.d("ToDo: go to calendar");
    }

    public void goToNewsScreen() {
        Timber.d("ToDo: go to news");
    }

    public void goToTournamentScreen() {
        Timber.d("ToDo: go to tournaments");
    }

    private void goToNewsDetailScreen(Long id) {
        Timber.d("ToDo: go to news detail with id: "+id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (matchesDisposable != null && !matchesDisposable.isDisposed())
            matchesDisposable.dispose();
        if (newsDisposable != null && !newsDisposable.isDisposed())
            newsDisposable.dispose();
        if (tournamentsDisposable != null && !tournamentsDisposable.isDisposed())
            tournamentsDisposable.dispose();
    }

}
