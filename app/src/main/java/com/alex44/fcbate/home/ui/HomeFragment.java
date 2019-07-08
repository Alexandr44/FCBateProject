package com.alex44.fcbate.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.alex44.fcbate.R;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.view.HomeView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    HomePresenter presenter;

    @BindView(R.id.home_pager)
    protected ViewPager pager;

    @ProvidePresenter
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initMatchPager() {
        Timber.d("Init Pager");
        final MatchPagerAdapter pagerAdapter = new MatchPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new MatchItemFragment(), "Match 1");
        pagerAdapter.addFragment(new MatchItemFragment(), "Match 2");
        pagerAdapter.addFragment(new MatchItemFragment(), "Match 3");
        pagerAdapter.addFragment(new MatchItemFragment(), "Match 4");
        pagerAdapter.addFragment(new MatchItemFragment(), "Match 5");

        pager.setAdapter(pagerAdapter);
//        tabLayout.setupWithViewPager(pager);
        pager.setPageTransformer(true, new TabletTransformer());
    }
}
