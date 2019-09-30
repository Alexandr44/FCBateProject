package com.alex44.fcbate.team.presenter;

import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.team.model.repo.ITeamRepo;
import com.alex44.fcbate.team.view.TeamPagerItemView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class TeamPagerItemPresenter extends MvpPresenter<TeamPagerItemView> {
    private Scheduler mainThreadScheduler;
    private TeamItemType type;

    @Inject
    protected ITeamRepo teamRepo;

    @Inject
    protected Router router;

    private Disposable disposable;
    private Disposable clickDisposable;

    @Getter
    private List<PlayerDTO> playersData = new ArrayList<>();
    @Getter
    private List<TrainerDTO> trainersData = new ArrayList<>();
//    @Getter
//    private PublishSubject<NewsRVItemView> clickSubject = PublishSubject.create();

    public TeamPagerItemPresenter(Scheduler mainThreadScheduler, TeamItemType type) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.type = type;
    }

//    public void bind(NewsRVItemView view) {
//        final NewsItemDTO itemDTO = data.get(view.getPos());
//        view.setPhoto(itemDTO.getPhotoUrl());
//        view.setTitle(itemDTO.getTitle());
//        final String dateStr = DateFormatUtil.getFormattedDateStr(itemDTO.getCreated());
//        view.setDateTime(dateStr, dateStr.contains("назад"));
//    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
        processClicks();
    }

    private void processClicks() {
//        clickDisposable = clickSubject.subscribe(newsRVItemView -> {
//            final NewsItemDTO itemDTO = data.get(newsRVItemView.getPos());
//            router.navigateTo(new Screens.NewsDetailScreen(type, itemDTO.getId()));
//        });
    }

    private void loadData() {
        switch (type) {
            case PLAYERS:
                disposable = teamRepo.getPlayers().observeOn(mainThreadScheduler)
                        .subscribe(players -> {
                            Timber.d("Result");
                            playersData = players;
                            getViewState().updateData();
                        }, throwable -> {
                            getViewState().showMessage(type.toString()+" load failed");
                            Timber.e(throwable);
                        });
                break;
            case TRAINERS:
                disposable = teamRepo.getTrainers().observeOn(mainThreadScheduler)
                        .subscribe(trainers -> {
                            Timber.d("Result");
                            trainersData = trainers;
                            getViewState().updateData();
                        }, throwable -> {
                            getViewState().showMessage(type.toString()+" load failed");
                            Timber.e(throwable);
                        });
                break;
        }
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
