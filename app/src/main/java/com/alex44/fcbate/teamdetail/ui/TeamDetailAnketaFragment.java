package com.alex44.fcbate.teamdetail.ui;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.R;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailAnketaPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailAnketaView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamDetailAnketaFragment extends MvpAppCompatFragment implements TeamDetailAnketaView {

    private View view;
    private Unbinder unbinder;

    @BindView(R.id.anketa_txt)
    protected TextView anketaText;

    @InjectPresenter
    TeamDetailAnketaPresenter presenter;

    public TeamDetailAnketaFragment() {}

    public TeamDetailAnketaFragment(TeamDetailAnketaPresenter teamDetailAnketaPresenter) {
        presenter = teamDetailAnketaPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail_anketa, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @ProvidePresenter
    protected TeamDetailAnketaPresenter createPresenter() {
        return this.presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setAnketaText(String text) {
        anketaText.setText(Html.fromHtml(text).toString().trim());
    }

}
