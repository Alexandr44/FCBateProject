package com.alex44.fcbate.about.presenter;

import com.alex44.fcbate.about.model.dto.AboutDTO;
import com.alex44.fcbate.about.model.repo.IAboutRepo;
import com.alex44.fcbate.about.view.AboutView;
import com.alex44.fcbate.common.navigation.Screens;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class AboutPresenter extends MvpPresenter<AboutView> {

    @Inject
    protected Router router;

    @Inject
    protected IAboutRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    public AboutPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {
        disposable = repo.getAbout()
                .observeOn(mainThreadScheduler)
                .subscribe(aboutDTO -> {
                    Timber.d(aboutDTO.toString());
                    update(aboutDTO);
                }, throwable -> {
                    getViewState().showMessage("About load failed");
                    Timber.e(throwable);
                });
    }

    private void update(AboutDTO aboutDTO) {
        getViewState().setText(aboutDTO.getContent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void backClick() {
        router.newRootScreen(new Screens.HomeScreen());
    }

}
