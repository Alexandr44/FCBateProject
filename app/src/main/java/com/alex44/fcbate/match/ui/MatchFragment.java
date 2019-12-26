package com.alex44.fcbate.match.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.common.ui.ViewPagerExt;
import com.alex44.fcbate.match.presenter.MatchPresenter;
import com.alex44.fcbate.match.view.MatchView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MatchFragment extends MvpAppCompatFragment implements MatchView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    MatchPresenter presenter;

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    @BindView(R.id.match_pager)
    protected ViewPagerExt pager;

    @BindView(R.id.match_tab_layout)
    protected TabLayout tabLayout;

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

    public MatchFragment() {
        App.getInstance().getAppComponent().inject(this);
    }

    public static MatchFragment newInstance(MatchDTO matchDTO) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("match", matchDTO);
        final MatchFragment fragment = new MatchFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @ProvidePresenter
    protected MatchPresenter createPresenter() {
        MatchDTO matchDTO = null;
        if (getArguments() != null) {
            matchDTO = (MatchDTO)getArguments().getSerializable("match");
        }

        final MatchPresenter matchPresenter = new MatchPresenter(matchDTO);
        App.getInstance().getAppComponent().inject(matchPresenter);
        return matchPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Boolean backClick() {
        presenter.backClick();
        return true;
    }

    @Override
    public void initPager() {
        final MatchPagerAdapter pagerAdapter = new MatchPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(createMatchOnlineFragment(), "Онлайн");
        pagerAdapter.addFragment(createMatchPhotoFragment(), "Фото");
//        pagerAdapter.addFragment(createTeamDetailBiographyFragment(), "Биография");
//        pagerAdapter.addFragment(createTeamDetailPhotoFragment(type, id), "Фото");
//        if (type == TeamItemType.PLAYERS) {
//            pagerAdapter.addFragment(createTeamDetailStatisticFragment(id), "Статистика");
//        }

//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                final Fragment fragment = pagerAdapter.getItem(position);
//                final View view = fragment.getView();
//                pager.setViewForMeasure(view);  //установит размер PagerView для нового фрагмента
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

        pager.setAdapter(pagerAdapter);
        final Fragment fragment = pagerAdapter.getItem(pager.getCurrentItem());
        final View view = fragment.getView();
        pager.setViewForMeasure(view);  //установит размер PagerView для текущего фрагмента
        tabLayout.setupWithViewPager(pager);
    }

    private MatchOnlineFragment createMatchOnlineFragment() {
        final Bundle arguments = new Bundle();
        arguments.putLong("matchId", presenter.getMatchDTO().getId());
        final MatchOnlineFragment fragment = new MatchOnlineFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private MatchPhotoFragment createMatchPhotoFragment() {
        final Bundle arguments = new Bundle();
        arguments.putLong("matchId", presenter.getMatchDTO().getId());
        final MatchPhotoFragment fragment = new MatchPhotoFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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
