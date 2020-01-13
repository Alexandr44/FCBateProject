package com.alex44.fcbate.news.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.alex44.fcbate.news.presenter.NewsPagerItemPresenter;
import com.alex44.fcbate.news.view.NewsPagerItemView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsPagerItemFragment extends MvpAppCompatFragment implements NewsPagerItemView {

    private View view;
    private Unbinder unbinder;

    private NewsRVAdapter adapter;

    @InjectPresenter
    NewsPagerItemPresenter presenter;

    @BindView(R.id.news_pager_rv)
    RecyclerView recyclerView;

    private int currentVisiblePosition = 0;

    public NewsPagerItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_pager_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @ProvidePresenter
    public NewsPagerItemPresenter createPresenter() {
        NewsItemType type = null;
        if (getArguments() != null && getArguments().getSerializable("type") instanceof NewsItemType) {
            type = (NewsItemType) getArguments().getSerializable("type");
        }
        else {
            Timber.e("Type of NewsPagerItemFragment is unavailable");
        }

        final NewsPagerItemPresenter presenter = new NewsPagerItemPresenter(AndroidSchedulers.mainThread(), type);
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
        adapter = new NewsRVAdapter(presenter);
        App.getInstance().getAppComponent().inject(adapter);
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
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

    @Override
    public void onPause() {
        super.onPause();
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(currentVisiblePosition);
    }
}
