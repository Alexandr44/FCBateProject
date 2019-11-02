package com.alex44.fcbate.teamdetail.presenter;

import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailPhotoDTO;
import com.alex44.fcbate.teamdetail.model.repo.ITeamDetailRepo;
import com.alex44.fcbate.teamdetail.view.TeamDetailPhotoView;
import com.alex44.fcbate.teamdetail.view.TeamDetailRVItemView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class TeamDetailPhotoPresenter extends MvpPresenter<TeamDetailPhotoView> {

    @Inject
    protected Router router;

    private TeamItemType type;
    private Long id;

    @Inject
    protected ITeamDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    @Getter
    private List<TeamDetailPhotoDTO> photos = new ArrayList<>();

    public TeamDetailPhotoPresenter(TeamItemType type, Long id, Scheduler mainThreadScheduler) {
        this.type = type;
        this.id = id;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
        getInfo();
    }

    private void getInfo() {
        if (id == null) {
            Timber.e("Team member id not found");
            getViewState().showMessage("Team member id not found");
            return;
        }
        if (type == null) {
            Timber.e("Type for team member not found");
            getViewState().showMessage("Type for team member not found");
            return;
        }

        Maybe<List<TeamDetailPhotoDTO>> maybe = null;
        switch (type) {
            case PLAYERS:
                maybe = repo.getPlayerPhotos(id);
                break;
            case TRAINERS:
                maybe = repo.getTrainerPhotos(id);
                break;
        }

        if (maybe == null) return;

        disposable = maybe.observeOn(mainThreadScheduler)
                .subscribe(list -> {
                    photos.addAll(list);
                    getViewState().updateData();
                }, throwable -> {
                    getViewState().showMessage("Team member photo load failed");
                    Timber.e(throwable);
                });
    }

    private void init() {
        getViewState().initRV();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void bind(TeamDetailRVItemView holder) {
        holder.setPhoto(photos.get(holder.getPos()).getBigPhotoUrl());
    }
}
