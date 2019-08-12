package com.alex44.fcbate.news.ui;

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
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.alex44.fcbate.news.presenter.NewsPresenter;
import com.alex44.fcbate.news.view.NewsView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class NewsFragment extends MvpAppCompatFragment implements NewsView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    NewsPresenter presenter;

    @BindView(R.id.news_pager)
    protected ViewPager pager;

    @BindView(R.id.news_tab_layout)
    protected TabLayout tabLayout;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @ProvidePresenter
    protected NewsPresenter createPresenter() {
        final NewsPresenter newsPresenter = new NewsPresenter();
        App.getInstance().getAppComponent().inject(newsPresenter);
        return newsPresenter;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
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
        final NewsPagerAdapter pagerAdapter = new NewsPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(createNewsPagerItemFragment(NewsItemType.NEWS), "News");
        pagerAdapter.addFragment(createNewsPagerItemFragment(NewsItemType.PRESS), "Press");
        pagerAdapter.addFragment(createNewsPagerItemFragment(NewsItemType.DECLARATION), "Declarations");

        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        pager.setPageTransformer(true, new TabletTransformer());
    }

    private NewsPagerItemFragment createNewsPagerItemFragment(NewsItemType type) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("type", type);
        final NewsPagerItemFragment fragment = new NewsPagerItemFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
