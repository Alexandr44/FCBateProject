package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.view.HomeView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    @Inject
    protected Router router;

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
        router.newRootScreen(new Screens.CalendarScreen());
    }

    public void goToNewsScreen() {
        router.newRootScreen(new Screens.NewsScreen());
    }

    public void goToTournamentScreen() {
        router.newRootScreen(new Screens.TournamentScreen());
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

    public void backClick() {
        router.exit();
    }

    public void facebookClicked() {
        getViewState().openFacebook();
    }

    public void instagramClicked() {
        getViewState().openInstagram();
    }

    public void twitterClicked() {
        getViewState().openTwitter();
    }

    public void vkClicked() {
        getViewState().openVk();
    }

    public void youtubeClicked() {
        getViewState().openYoutube();
    }

    public void viberClicked() {
        getViewState().openViber();
    }

}
