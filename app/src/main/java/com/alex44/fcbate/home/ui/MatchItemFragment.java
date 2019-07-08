package com.alex44.fcbate.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.home.presenter.IMatchItemPresenter;
import com.alex44.fcbate.home.view.MatchItemView;
import com.alex44.fcbate.utils.model.IImageLoader;
import com.alex44.fcbate.utils.ui.GlideImageLoader;
import com.arellomobile.mvp.MvpAppCompatFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatchItemFragment extends MvpAppCompatFragment implements MatchItemView {

    private View view;
    private Unbinder unbinder;
    private int pos;
    private IImageLoader<ImageView> imageLoader = new GlideImageLoader();
    private IMatchItemPresenter matchItemPresenter;

    @BindView(R.id.left_logo)
    protected ImageView leftLogo;
    @BindView(R.id.right_logo)
    protected ImageView rightLogo;
    @BindView(R.id.middle_logo)
    protected ImageView middleLogo;
    @BindView(R.id.left_title)
    protected TextView leftTitle;
    @BindView(R.id.right_title)
    protected TextView rightTitle;
    @BindView(R.id.champ_name)
    protected TextView champName;
    @BindView(R.id.match_date)
    protected TextView matchDate;
    @BindView(R.id.match_time)
    protected TextView matchTime;
    @BindView(R.id.match_score)
    protected TextView matchScore;

    public MatchItemFragment() {
        // Required empty public constructor
    }

    public MatchItemFragment(int pos, IMatchItemPresenter iMatchItemPresenter) {
        this.pos = pos;
        this.matchItemPresenter = iMatchItemPresenter;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_match_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        matchItemPresenter.update(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public void setLeftLogo(@NotNull String logoUrl) {
        imageLoader.loadInto(logoUrl, leftLogo);
    }

    @Override
    public void setRightLogo(@NotNull String logoUrl) {
        imageLoader.loadInto(logoUrl, rightLogo);
    }

    @Override
    public void setLeftTeamTitle(@NotNull String title) {
        leftTitle.setText(title);
    }

    @Override
    public void setRightTeamTitle(@NotNull String title) {
        rightTitle.setText(title);
    }

    @Override
    public void setChampTitle(@NotNull String title) {
        champName.setText(title);
    }

    @Override
    public void setScore(@NotNull String score) {
        matchScore.setText(score);
    }

    @Override
    public void setDate(@NotNull String date) {
        matchDate.setText(date);
    }

    @Override
    public void setTime(@NotNull String time) {
        matchTime.setText(time);
    }
}
