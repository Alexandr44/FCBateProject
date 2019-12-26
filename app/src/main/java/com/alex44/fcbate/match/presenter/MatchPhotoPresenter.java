package com.alex44.fcbate.match.presenter;

import com.alex44.fcbate.match.model.repo.IMatchRepo;
import com.alex44.fcbate.match.view.MatchPhotoRVItemView;
import com.alex44.fcbate.match.view.MatchPhotoView;
import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;
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
public class MatchPhotoPresenter extends MvpPresenter<MatchPhotoView> {

    @Inject
    protected Router router;

    private Long matchId;

    @Inject
    protected IMatchRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    @Getter
    private List<PhotoDTO> photos = new ArrayList<>();

    public MatchPhotoPresenter(Long matchId, Scheduler mainThreadScheduler) {
        this.matchId = matchId;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
        getInfo();
    }

    private void getInfo() {
        if (matchId == null) {
            Timber.e("Photo for current match not found");
            getViewState().showMessage("Photo for current match not found");
            return;
        }

        disposable = repo.getMatchPhotos(matchId)
                .observeOn(mainThreadScheduler)
                .subscribe(list -> {
                    photos = list;
                    getViewState().updateData();
                }, throwable -> {
                    getViewState().showMessage("Match photos load failed");
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

    public void bind(MatchPhotoRVItemView holder) {
        holder.setPhoto(photos.get(holder.getPos()).getBigPhotoUrl());
    }
}
