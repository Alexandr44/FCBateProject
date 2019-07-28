package com.alex44.fcbate.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.home.presenter.IMatchItemPresenter;
import com.alex44.fcbate.home.view.MatchItemView;
import com.alex44.fcbate.utils.model.IImageLoader;
import com.arellomobile.mvp.MvpAppCompatFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatchItemFragment extends MvpAppCompatFragment implements MatchItemView {

    @Inject
    protected IImageLoader<ImageView> imageLoader;

    private View view;
    private Unbinder unbinder;
    private int pos;
    private IMatchItemPresenter matchItemPresenter;

    @BindView(R.id.left_logo)
    protected ImageView leftLogo;
    @BindView(R.id.right_logo)
    protected ImageView rightLogo;
    @BindView(R.id.tournament_logo)
    protected ImageView tournamentLogo;
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
        App.getInstance().getAppComponent().inject(this);
        // Required empty public constructor
    }

    public MatchItemFragment(int pos, IMatchItemPresenter iMatchItemPresenter) {
        App.getInstance().getAppComponent().inject(this);
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
        imageLoader.loadInto(logoUrl, leftLogo, 50);
    }

    @Override
    public void setRightLogo(@NotNull String logoUrl) {
        imageLoader.loadInto(logoUrl, rightLogo, 50);
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

    @Override
    public void setTournamentLogo(@NotNull String tournamentShortName) {
        tournamentLogo.setImageResource(getImageResByTournamentName(tournamentShortName));
    }

    private int getImageResByTournamentName(String name) {
        if ("ЧБ".equals(name)) return R.drawable.trnm_bel_league_logo;
        if ("КБ".equals(name)) return R.drawable.trnm_bel_cup_logo;
        if ("СКБ".equals(name)) return R.drawable.trnm_bel_super_cup_logo;
        if ("ЛЧ".equals(name)) return R.drawable.trnm_eur_champion_league_logo;
        if ("ЕЛ".equals(name)) return R.drawable.trnm_eur_league_logo;
        if ("ЮЛ".equals(name)) return R.drawable.trnm_eur_youth_league_logo;
        return 0;
    }
}
