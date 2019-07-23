package com.alex44.fcbate.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex44.fcbate.R;
import com.alex44.fcbate.home.presenter.INewsItemPresenter;
import com.alex44.fcbate.home.view.NewsItemView;
import com.alex44.fcbate.utils.model.IImageLoader;
import com.alex44.fcbate.utils.ui.GlideImageLoader;
import com.arellomobile.mvp.MvpAppCompatFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewsItemFragment extends MvpAppCompatFragment implements NewsItemView {

    private View view;
    private Unbinder unbinder;
    private int pos;
    private IImageLoader<ImageView> imageLoader = new GlideImageLoader();
    private INewsItemPresenter newsItemPresenter;

    @BindView(R.id.news_photo)
    protected ImageView newsPhoto;
    @BindView(R.id.news_title)
    protected TextView newsTitle;
    @BindView(R.id.news_date_time)
    protected TextView newsDateTime;

    public NewsItemFragment() {
        // Required empty public constructor
    }

    public NewsItemFragment(int pos, INewsItemPresenter newsItemPresenter) {
        this.pos = pos;
        this.newsItemPresenter = newsItemPresenter;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_news_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        newsItemPresenter.update(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public void setPhoto(@NotNull String photoUrl) {
        imageLoader.loadInto(photoUrl, newsPhoto, 20);
    }

    @Override
    public void setTitle(@NotNull String title) {
        newsTitle.setText(title);
    }

    @Override
    public void setDateTime(@NotNull String dateTime, boolean isInLastHour) {
        newsDateTime.setText(dateTime);
        if (isInLastHour) {
            newsDateTime.setTextColor(getResources().getColor(R.color.defaultBlueFontColor));
            newsDateTime.setBackgroundColor(getResources().getColor(R.color.defaultYellow));
        }
        else {
            newsDateTime.setTextColor(Color.WHITE);
            newsDateTime.setBackgroundColor(0x000);
        }
    }

    @OnClick(R.id.news_photo)
    public void imageClick() {
        newsItemPresenter.imageClicked(pos);
    }

}
