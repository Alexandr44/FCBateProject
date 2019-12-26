package com.alex44.fcbate.match.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.match.presenter.MatchOnlinePresenter;
import com.alex44.fcbate.match.view.MatchOnlineView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MatchOnlineFragment extends MvpAppCompatFragment implements MatchOnlineView {

    private View view;
    private Unbinder unbinder;

   private MatchOnlineRVAdapter adapter;

    @InjectPresenter
    MatchOnlinePresenter presenter;

    @BindView(R.id.match_online_rv)
    RecyclerView recyclerView;

    public MatchOnlineFragment() {
    }

    @ProvidePresenter
    protected MatchOnlinePresenter createPresenter() {
        Long id = null;
        if (getArguments() != null) {
            id = getArguments().getLong("matchId");
        }

        final MatchOnlinePresenter matchOnlinePresenter = new MatchOnlinePresenter(id, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(matchOnlinePresenter);
        return matchOnlinePresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_online
                , container, false);
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
        adapter = new MatchOnlineRVAdapter(presenter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
