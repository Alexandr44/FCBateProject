package com.alex44.fcbate.about.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.about.presenter.AboutPresenter;
import com.alex44.fcbate.about.view.AboutView;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.common.ui.HtmlParser;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends MvpAppCompatFragment implements AboutView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    private HtmlParser htmlParser;

    @InjectPresenter
    AboutPresenter presenter;

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    @BindView(R.id.about_photo)
    protected ImageView aboutPhoto;
    @BindView(R.id.text_layout)
    protected LinearLayout linearLayout;

    public AboutFragment() {
        App.getInstance().getAppComponent().inject(this);
    }

    public static Fragment newInstance() {
        return new AboutFragment();
    }

    @ProvidePresenter
    protected AboutPresenter createPresenter() {
        final AboutPresenter aboutPresenter = new AboutPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(aboutPresenter);
        return aboutPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);
        htmlParser = new HtmlParser(getContext(), imageLoader);
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
    public void setText(String text) {
        htmlParser.parseHtmlTextInto(text, linearLayout);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
