package com.alex44.fcbate.team.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.team.presenter.TeamPagerItemPresenter;
import com.alex44.fcbate.team.view.TeamPagerItemView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamPagerItemFragment extends MvpAppCompatFragment implements TeamPagerItemView {

    private View view;
    private Unbinder unbinder;

    private TeamRVAdapter adapter;

    @InjectPresenter
    TeamPagerItemPresenter presenter;

    @BindView(R.id.team_pager_rv)
    RecyclerView recyclerView;

    public TeamPagerItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_pager_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @ProvidePresenter
    public TeamPagerItemPresenter createPresenter() {
        TeamItemType type = null;
        if (getArguments() != null && getArguments().getSerializable("type") instanceof TeamItemType) {
            type = (TeamItemType) getArguments().getSerializable("type");
        }
        else {
            Timber.e("Type of TeamPagerItemFragment is unavailable");
        }

        final TeamPagerItemPresenter presenter = new TeamPagerItemPresenter(AndroidSchedulers.mainThread(), type);
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void init() {
        adapter = new TeamRVAdapter(presenter);
        App.getInstance().getAppComponent().inject(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == TeamRVAdapter.TYPE_TITLE) {
                    return 2;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
