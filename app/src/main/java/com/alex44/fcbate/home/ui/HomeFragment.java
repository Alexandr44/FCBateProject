package com.alex44.fcbate.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.alex44.fcbate.R;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.view.HomeView;
import com.alex44.fcbate.utils.model.ISystemInfo;
import com.alex44.fcbate.utils.ui.SystemInfo;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    private View view;
    private Unbinder unbinder;

    private ISystemInfo systemInfo = new SystemInfo();

    @InjectPresenter
    HomePresenter presenter;

    @BindView(R.id.home_pager)
    protected ViewPager pager;
    @BindView(R.id.home_news_pager)
    protected ViewPager newsPager;

    @ProvidePresenter
    protected HomePresenter createPresenter() {
        return new HomePresenter(AndroidSchedulers.mainThread(), systemInfo);
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
        final HomePagerAdapter pagerAdapter = new HomePagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new MatchItemFragment(0, presenter.getMatchItemPresenter()), "Match 1");
        pagerAdapter.addFragment(new MatchItemFragment(1, presenter.getMatchItemPresenter()), "Match 2");
        pagerAdapter.addFragment(new MatchItemFragment(2, presenter.getMatchItemPresenter()), "Match 3");
        pagerAdapter.addFragment(new MatchItemFragment(3, presenter.getMatchItemPresenter()), "Match 4");
        pagerAdapter.addFragment(new MatchItemFragment(4, presenter.getMatchItemPresenter()), "Match 5");

        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(2);
        pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean lastPage = true;
            boolean lastPageDragEnabled = false;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (lastPage && position == 4 && lastPageDragEnabled) {
                    lastPage = false;
                    presenter.goToCalendarScreen();
                }
            }

            @Override
            public void onPageSelected(int position) {
                lastPage = position == 4;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (lastPage) {
                    lastPageDragEnabled = state == ViewPager.SCROLL_STATE_DRAGGING;
                }
                else {
                    lastPageDragEnabled = false;
                }
            }
        });
    }

    @Override
    public void initNewsPager() {
        final HomePagerAdapter pagerAdapter = new HomePagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new NewsItemFragment(0, presenter.getNewsItemPresenter()), "News 1");
        pagerAdapter.addFragment(new NewsItemFragment(1, presenter.getNewsItemPresenter()), "News 2");
        pagerAdapter.addFragment(new NewsItemFragment(2, presenter.getNewsItemPresenter()), "News 3");
        pagerAdapter.addFragment(new NewsItemFragment(3, presenter.getNewsItemPresenter()), "News 4");
        pagerAdapter.addFragment(new NewsItemFragment(4, presenter.getNewsItemPresenter()), "News 5");

        newsPager.setAdapter(pagerAdapter);
        newsPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        newsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean lastPage = true;
            boolean lastPageDragEnabled = false;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (lastPage && position == 4 && lastPageDragEnabled) {
                    lastPage = false;
                    presenter.goToNewsScreen();
                }
            }

            @Override
            public void onPageSelected(int position) {
                lastPage = position == 4;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (lastPage) {
                    lastPageDragEnabled = state == ViewPager.SCROLL_STATE_DRAGGING;
                }
                else {
                    lastPageDragEnabled = false;
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
