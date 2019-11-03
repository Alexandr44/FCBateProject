package com.alex44.fcbate.teamdetail.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailStatisticPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailStatisticView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TeamDetailStatisticFragment extends MvpAppCompatFragment implements TeamDetailStatisticView {

    private View view;
    private Unbinder unbinder;

   private TeamDetailStatisticRVAdapter adapter;

    @InjectPresenter
    TeamDetailStatisticPresenter presenter;

    @BindView(R.id.team_detail_statistic_rv)
    RecyclerView recyclerView;

    public TeamDetailStatisticFragment() {
    }

    @ProvidePresenter
    protected TeamDetailStatisticPresenter createPresenter() {
        Long id = null;
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }

        final TeamDetailStatisticPresenter teamDetailStatisticPresenter = new TeamDetailStatisticPresenter(id, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(teamDetailStatisticPresenter);
        return teamDetailStatisticPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail_stats, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initRV() {
        adapter = new TeamDetailStatisticRVAdapter(presenter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
