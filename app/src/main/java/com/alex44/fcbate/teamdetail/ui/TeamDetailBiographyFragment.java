package com.alex44.fcbate.teamdetail.ui;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamDetailBiographyFragment extends Fragment {

    private View view;
    private Unbinder unbinder;

    @BindView(R.id.biography_text)
    protected TextView biographyText;

    private String text;

    public TeamDetailBiographyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail_biography, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (text != null && !text.isEmpty()) {
            setBiographyText(text);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setBiographyText(String text) {
        this.text = text;
        if (biographyText != null) {
            biographyText.setText(Html.fromHtml(text).toString().trim());
        }
    }

}
