package com.alex44.fcbate.teamdetail.ui;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailBiographyPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailBiographyView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamDetailBiographyFragment extends MvpAppCompatFragment implements TeamDetailBiographyView {

    private View view;
    private Unbinder unbinder;

    @BindView(R.id.biography_text)
    protected TextView biographyText;

    @InjectPresenter
    TeamDetailBiographyPresenter presenter;

    public TeamDetailBiographyFragment() {
        // Required empty public constructor
    }

    public TeamDetailBiographyFragment(TeamDetailBiographyPresenter teamDetailBiographyPresenter) {
        presenter = teamDetailBiographyPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail_biography, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @ProvidePresenter
    protected TeamDetailBiographyPresenter createPresenter() {
        return this.presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setBiographyText(String text) {
        biographyText.setText(Html.fromHtml(text).toString().trim());
    }
}
