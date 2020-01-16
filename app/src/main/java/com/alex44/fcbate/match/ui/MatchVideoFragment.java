package com.alex44.fcbate.match.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.match.presenter.MatchVideoPresenter;
import com.alex44.fcbate.match.view.MatchVideoView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MatchVideoFragment extends MvpAppCompatFragment implements MatchVideoView {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    MatchVideoPresenter presenter;

    @BindView(R.id.youtube_player)
    YouTubePlayerView youTubePlayerView;

    private YouTubePlayer youTubePlayer;

    public MatchVideoFragment() {
    }

    @ProvidePresenter
    protected MatchVideoPresenter createPresenter() {
        Long matchId = null;
        if (getArguments() != null) {
            matchId = getArguments().getLong("matchId");
        }
        final MatchVideoPresenter matchVideoPresenter = new MatchVideoPresenter(matchId, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(matchVideoPresenter);
        return matchVideoPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_videos, container, false);
        unbinder = ButterKnife.bind(this, view);
        getLifecycle().addObserver(youTubePlayerView);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        youTubePlayerView.release();
        unbinder.unbind();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void initPlayer(String youtubeLink) {
        final String videoId = youtubeLink.substring(youtubeLink.lastIndexOf("/")+1);
        youTubePlayerView.getYouTubePlayerWhenReady(player -> {
            youTubePlayer = player;
            player.cueVideo(videoId, 0);
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                presenter.goToFullScreen(videoId);

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {}
        });

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (!menuVisible && youTubePlayer != null)
            youTubePlayer.pause();
    }

}
