package com.alex44.fcbate.tournament.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.tournament.presenter.TournamentPresenter;
import com.alex44.fcbate.tournament.view.TournamentView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TournamentFragment extends MvpAppCompatFragment implements TournamentView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    TournamentPresenter presenter;

    TournamentRVAdapter adapter;

    @BindView(R.id.tournament_rv)
    RecyclerView recyclerView;

    public static Fragment newInstance() {
        return new TournamentFragment();
    }
    public TournamentFragment() {
        // Required empty public constructor
    }

    @ProvidePresenter
    protected TournamentPresenter createPresenter() {
        final TournamentPresenter tournamentPresenter = new TournamentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getComponentManager().getTournamentSubComponent().inject(tournamentPresenter);
        return tournamentPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_table, container, false);
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
    public void init() {
        adapter = new TournamentRVAdapter(presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
