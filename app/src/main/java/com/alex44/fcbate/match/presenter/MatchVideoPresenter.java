package com.alex44.fcbate.match.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.match.model.dto.VideoDTO;
import com.alex44.fcbate.match.model.repo.IMatchRepo;
import com.alex44.fcbate.match.view.MatchVideoView;
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

import static com.arellomobile.mvp.MvpFacade.init;

@InjectViewState
public class MatchVideoPresenter extends MvpPresenter<MatchVideoView> {

    private Long matchId;

    @Inject
    protected IMatchRepo repo;

    @Inject
    protected Router router;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    @Getter
    private List<VideoDTO> videos = new ArrayList<>();

    public MatchVideoPresenter(Long matchId, Scheduler mainThreadScheduler) {
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
            Timber.e("Video for current match not found");
            getViewState().showMessage("Video for current match not found");
            return;
        }

        disposable = repo.getMatchVideos(matchId)
                .observeOn(mainThreadScheduler)
                .subscribe(list -> {
                    videos = list;
                    initVideoView();
                }, throwable -> {
                    getViewState().showMessage("Match videos load failed");
                    Timber.e(throwable);
                });
    }

    private void initVideoView() {
        if (videos != null && !videos.isEmpty()) {
            final VideoDTO videoDTO = videos.get(0);
            if (videoDTO.isYoutube() && videoDTO.getYoutubeLink() != null && !videoDTO.getYoutubeLink().isEmpty()) {
                getViewState().initPlayer(videoDTO.getYoutubeLink());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void goToFullScreen(String videoId) {
        router.navigateTo(new Screens.VideoScreen(videoId));
    }
}
