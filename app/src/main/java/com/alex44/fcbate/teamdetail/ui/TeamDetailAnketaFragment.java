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

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamDetailAnketaFragment extends Fragment {

    private View view;
    private Unbinder unbinder;

    @BindView(R.id.anketa_txt)
    protected TextView anketaText;

    private String text;

    public TeamDetailAnketaFragment() {
        if (getArguments() != null) {
            if (getArguments().getString("anketa") != null) {
                this.text = getArguments().getString("anketa");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail_anketa, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (text != null && !text.isEmpty()) {
            setAnketaText(text);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setAnketaText(String text) {
        this.text = text;
        if (anketaText != null) {
            anketaText.setText(Html.fromHtml(text).toString().trim());
        }
    }

}
