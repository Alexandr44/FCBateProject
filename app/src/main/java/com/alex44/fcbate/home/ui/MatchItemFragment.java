package com.alex44.fcbate.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.home.presenter.MatchItemPresenter;
import com.alex44.fcbate.home.view.MatchItemView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MatchItemFragment extends MvpAppCompatFragment implements MatchItemView {

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    MatchItemPresenter matchItemPresenter;

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
        App.getInstance().getComponentManager().getHomeSubComponent().inject(this);
    }

    @ProvidePresenter
    protected MatchItemPresenter createPresenter() {
        MatchDTO matchDTO = null;
        if (getArguments() != null && getArguments().getSerializable("data") instanceof MatchDTO) {
            matchDTO = (MatchDTO) getArguments().getSerializable("data");
        }
        else {
            Timber.e("No data for MatchItemPresenter");
        }
        return new MatchItemPresenter(matchDTO);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_match_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        if ("ЛЕ".equals(name)) return R.drawable.trnm_eur_league_logo;
        if ("ЛЮ".equals(name)) return R.drawable.trnm_eur_youth_league_logo;
        return 0;
    }
}
