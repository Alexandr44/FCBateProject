package com.alex44.fcbate.team.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.team.presenter.TeamPresenter;
import com.alex44.fcbate.team.view.TeamView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class TeamFragment extends MvpAppCompatFragment implements TeamView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    TeamPresenter presenter;

    @BindView(R.id.team_pager)
    protected ViewPager pager;

    @BindView(R.id.team_tab_layout)
    protected TabLayout tabLayout;

    public TeamFragment() {
        // Required empty public constructor
    }

    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    @ProvidePresenter
    protected TeamPresenter createPresenter() {
        final TeamPresenter teamPresenter = new TeamPresenter();
        App.getInstance().getAppComponent().inject(teamPresenter);
        return teamPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team, container, false);
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
        final TeamPagerAdapter pagerAdapter = new TeamPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(createTeamPagerItemFragment(TeamItemType.PLAYERS), getResources().getString(R.string.pager_title_players));
        pagerAdapter.addFragment(createTeamPagerItemFragment(TeamItemType.TRAINERS), getResources().getString(R.string.pager_title_trainers));

        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        pager.setPageTransformer(true, new TabletTransformer());
    }

    private TeamPagerItemFragment createTeamPagerItemFragment(TeamItemType type) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("type", type);
        final TeamPagerItemFragment fragment = new TeamPagerItemFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
