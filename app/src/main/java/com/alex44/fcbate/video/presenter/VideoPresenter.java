package com.alex44.fcbate.video.presenter;

import com.alex44.fcbate.video.view.VideoView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class VideoPresenter extends MvpPresenter<VideoView> {

    private String videoId;
    private float startSecond;

    public VideoPresenter(String videoId) {
        this.videoId = videoId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    private void launchVideo(String videoId, float startSecond) {
        getViewState().launchVideo(videoId, startSecond);
    }

    public void setStartSecond(float currentSecond) {
        startSecond = currentSecond;
    }

    public void viewCreated() {
        launchVideo(videoId, startSecond);
    }
}
