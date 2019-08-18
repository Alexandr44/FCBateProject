package com.alex44.fcbate.newsdetail.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.news.model.enums.NewsItemType;
import com.alex44.fcbate.newsdetail.presenter.NewsDetailPresenter;
import com.alex44.fcbate.newsdetail.view.NewsDetailView;
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
public class NewsDetailFragment extends MvpAppCompatFragment implements NewsDetailView, BackButtonListener {

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    NewsDetailPresenter presenter;

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    @BindView(R.id.news_detail_photo)
    protected ImageView newsPhoto;
    @BindView(R.id.news_detail_text)
    protected TextView newsText;
    @BindView(R.id.news_detail_brief)
    protected TextView briefText;
    @BindView(R.id.news_detail_date)
    protected TextView dateText;

    public NewsDetailFragment() {
        App.getInstance().getComponentManager().getNewsDetailSubComponent().inject(this);
    }

    public static Fragment newInstance(NewsItemType type, Long newsId) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("type", type);
        arguments.putLong("newsId", newsId);
        final NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @ProvidePresenter
    protected NewsDetailPresenter createPresenter() {
        NewsItemType type = null;
        Long newsId = null;
        if (getArguments() != null) {
            newsId = getArguments().getLong("newsId");
            type = (NewsItemType)getArguments().getSerializable("type");
        }

        final NewsDetailPresenter newsDetailPresenter = new NewsDetailPresenter(type, newsId, AndroidSchedulers.mainThread());
        App.getInstance().getComponentManager().getNewsDetailSubComponent().inject(newsDetailPresenter);
        return newsDetailPresenter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_detail, container, false);
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
    public void setPhoto(String photoUrl) {
        imageLoader.loadInto(photoUrl, newsPhoto, 20);
    }

    @Override
    public void setText(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            newsText.setText(Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT).toString().trim());
        } else {
            newsText.setText(Html.fromHtml(text).toString().trim());
        }
    }

    @Override
    public void setBrief(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            briefText.setText(Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT).toString().trim());
        } else {
            briefText.setText(Html.fromHtml(text).toString().trim());
        }
    }

    @Override
    public void setDate(String text, boolean isInLastHour) {
        dateText.setText(text);
        if (isInLastHour) {
            dateText.setTextColor(getResources().getColor(R.color.defaultBlueFontColor));
            dateText.setBackgroundColor(getResources().getColor(R.color.defaultYellow));
        }
        else {
            dateText.setTextColor(Color.WHITE);
            dateText.setBackgroundColor(0x000);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
