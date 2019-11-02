package com.alex44.fcbate.teamdetail.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailPhotoPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailPhotoView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TeamDetailPhotoFragment extends MvpAppCompatFragment implements TeamDetailPhotoView {

    private View view;
    private Unbinder unbinder;

   private TeamDetailPhotoRVAdapter adapter;

    @InjectPresenter
    TeamDetailPhotoPresenter presenter;

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    @BindView(R.id.team_detail_photo_rv)
    RecyclerView recyclerView;

    public TeamDetailPhotoFragment() {
        App.getInstance().getAppComponent().inject(this);
    }

    @ProvidePresenter
    protected TeamDetailPhotoPresenter createPresenter() {
        TeamItemType type = null;
        Long id = null;
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            type = (TeamItemType)getArguments().getSerializable("type");
        }

        final TeamDetailPhotoPresenter teamDetailPhotoPresenter = new TeamDetailPhotoPresenter(type, id, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(teamDetailPhotoPresenter);
        return teamDetailPhotoPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail_photos, container, false);
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
        adapter = new TeamDetailPhotoRVAdapter(presenter);
        App.getInstance().getAppComponent().inject(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
