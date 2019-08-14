package com.alex44.fcbate.calendar.ui;


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
import com.alex44.fcbate.calendar.presenter.CalendarPresenter;
import com.alex44.fcbate.calendar.view.CalendarView;
import com.alex44.fcbate.common.ui.BackButtonListener;
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
public class CalendarFragment extends MvpAppCompatFragment implements CalendarView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    CalendarPresenter presenter;

    CalendarRVAdapter adapter;

    @BindView(R.id.calendar_rv)
    RecyclerView recyclerView;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new CalendarFragment();
    }

    @ProvidePresenter
    protected CalendarPresenter createPresenter() {
        final CalendarPresenter calendarPresenter = new CalendarPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(calendarPresenter);
        return calendarPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void init() {
        adapter = new CalendarRVAdapter(presenter);
        App.getInstance().getAppComponent().inject(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public Boolean backClick() {
        presenter.backClick();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
