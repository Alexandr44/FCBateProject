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
import com.alex44.fcbate.match.presenter.MatchPhotoPresenter;
import com.alex44.fcbate.match.view.MatchPhotoView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MatchPhotoFragment extends MvpAppCompatFragment implements MatchPhotoView {

    private View view;
    private Unbinder unbinder;

   private MatchPhotoRVAdapter adapter;

    @InjectPresenter
    MatchPhotoPresenter presenter;

    @BindView(R.id.match_photo_rv)
    RecyclerView recyclerView;

    public MatchPhotoFragment() {
    }

    @ProvidePresenter
    protected MatchPhotoPresenter createPresenter() {
        Long matchId = null;
        if (getArguments() != null) {
            matchId = getArguments().getLong("matchId");
        }

        final MatchPhotoPresenter matchPhotoPresenter = new MatchPhotoPresenter(matchId, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(matchPhotoPresenter);
        return matchPhotoPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_photos, container, false);
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
        adapter = new MatchPhotoRVAdapter(presenter);
        App.getInstance().getAppComponent().inject(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
