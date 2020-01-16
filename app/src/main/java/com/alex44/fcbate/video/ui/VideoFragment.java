package com.alex44.fcbate.video.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.R;
import com.alex44.fcbate.main.ui.MainActivity;
import com.alex44.fcbate.video.presenter.VideoPresenter;
import com.alex44.fcbate.video.view.VideoView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoFragment extends MvpAppCompatFragment implements VideoView {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    VideoPresenter presenter;

    @BindView(R.id.youtube_player_fullscreen)
    YouTubePlayerView youTubePlayerView;

    private YouTubePlayerTracker tracker = null;

    public VideoFragment() {
    }

    public static Fragment newInstance(String videoId) {
        final Bundle arguments = new Bundle();
        arguments.putString("videoId", videoId);
        final VideoFragment fragment = new VideoFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @ProvidePresenter
    protected VideoPresenter createPresenter() {
        String videoId = null;
        if (getArguments() != null) {
            videoId = getArguments().getString("videoId");
        }
        return new VideoPresenter(videoId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.hideActionBar();
        }
        view = inflater.inflate(R.layout.fragment_fullscreen_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPlayer(savedInstanceState);
        presenter.viewCreated();
        return view;
    }

    private void initPlayer(Bundle savedInstanceState) {
        getLifecycle().addObserver(youTubePlayerView);
        if (savedInstanceState != null) {
            final float currentSecond = savedInstanceState.getFloat("currentSecond");
            presenter.setStartSecond(currentSecond);
        }
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen(){}

            @Override
            public void onYouTubePlayerExitFullScreen() {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
        youTubePlayerView.enterFullScreen();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.showActionBar();
        }
        if (tracker != null) {
            presenter.setStartSecond(tracker.getCurrentSecond());
        }
        youTubePlayerView.release();
        unbinder.unbind();
    }

    @Override
    public void launchVideo(String videoId, float startSecond) {
        youTubePlayerView.getYouTubePlayerWhenReady(player -> {
            player.loadVideo(videoId, startSecond);
            tracker = new YouTubePlayerTracker();
            player.addListener(tracker);
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("currentSecond", tracker.getCurrentSecond());
    }

}
