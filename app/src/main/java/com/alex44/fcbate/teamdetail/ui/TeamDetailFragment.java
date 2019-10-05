package com.alex44.fcbate.teamdetail.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TeamDetailFragment extends MvpAppCompatFragment implements TeamDetailView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    TeamDetailPresenter presenter;

    @BindView(R.id.team_detail_pager)
    protected ViewPager pager;

    @BindView(R.id.team_detail_tab_layout)
    protected TabLayout tabLayout;

    public TeamDetailFragment() {
        // Required empty public constructor
    }

    public static TeamDetailFragment newInstance(TeamItemType type, Long newsId) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("type", type);
        arguments.putLong("id", newsId);
        final TeamDetailFragment fragment = new TeamDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @ProvidePresenter
    protected TeamDetailPresenter createPresenter() {
        TeamItemType type = null;
        Long id = null;
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            type = (TeamItemType)getArguments().getSerializable("type");
        }

        final TeamDetailPresenter teamDetailPresenter = new TeamDetailPresenter(type, id, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(teamDetailPresenter);
        return teamDetailPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail, container, false);
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
//        final TeamPagerAdapter pagerAdapter = new TeamPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        pagerAdapter.addFragment(createTeamPagerItemFragment(TeamItemType.PLAYERS), getResources().getString(R.string.pager_title_players));
//        pagerAdapter.addFragment(createTeamPagerItemFragment(TeamItemType.TRAINERS), getResources().getString(R.string.pager_title_trainers));

//        pager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(pager);
//        pager.setPageTransformer(true, new TabletTransformer());
    }

//    private TeamPagerItemFragment createTeamPagerItemFragment(TeamItemType type) {
//        final Bundle arguments = new Bundle();
//        arguments.putSerializable("type", type);
//        final TeamPagerItemFragment fragment = new TeamPagerItemFragment();
//        fragment.setArguments(arguments);
//        return fragment;
//    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
