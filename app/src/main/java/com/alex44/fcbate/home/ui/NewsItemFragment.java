package com.alex44.fcbate.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.news.model.dto.NewsDTO;
import com.alex44.fcbate.home.presenter.NewsItemPresenter;
import com.alex44.fcbate.home.view.NewsItemView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class NewsItemFragment extends MvpAppCompatFragment implements NewsItemView {

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    NewsItemPresenter newsItemPresenter;

    @BindView(R.id.news_photo)
    protected ImageView newsPhoto;
    @BindView(R.id.news_title)
    protected TextView newsTitle;
    @BindView(R.id.news_date_time)
    protected TextView newsDateTime;

    public NewsItemFragment() {
        App.getInstance().getAppComponent().inject(this);
    }

    @ProvidePresenter
    protected NewsItemPresenter createPresenter() {
        NewsDTO newsDTO = null;
        if (getArguments() != null && getArguments().getSerializable("data") instanceof NewsDTO) {
            newsDTO = (NewsDTO) getArguments().getSerializable("data");
        }
        else {
            Timber.e("No data for NewsItemPresenter");
        }
        final NewsItemPresenter newsItemPresenter = new NewsItemPresenter(newsDTO);
        App.getInstance().getAppComponent().inject(newsItemPresenter);
        return newsItemPresenter;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_news_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        Timber.d("Click image");
        newsItemPresenter.imageClicked();
    }

}
