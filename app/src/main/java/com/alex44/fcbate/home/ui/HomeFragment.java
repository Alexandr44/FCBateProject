package com.alex44.fcbate.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex44.fcbate.R;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.view.HomeView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    HomePresenter presenter;

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
}
