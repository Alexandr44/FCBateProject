package com.alex44.fcbate.news.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.news.presenter.NewsPresenter;
import com.alex44.fcbate.news.view.NewsView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class NewsFragment extends MvpAppCompatFragment implements NewsView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    NewsPresenter presenter;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @ProvidePresenter
    protected NewsPresenter createPresenter() {
        final NewsPresenter newsPresenter = new NewsPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(newsPresenter);
        return newsPresenter;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = FrameLayout.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(params);
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
}
