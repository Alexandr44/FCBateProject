package com.alex44.fcbate.di;

import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.CiceroneModule;
import com.alex44.fcbate.di.modules.ImageModule;
import com.alex44.fcbate.di.modules.RepoModule;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.presenter.NewsItemPresenter;
import com.alex44.fcbate.home.ui.MatchItemFragment;
import com.alex44.fcbate.home.ui.NewsItemFragment;
import com.alex44.fcbate.main.presenter.MainPresenter;
import com.alex44.fcbate.main.ui.MainActivity;
import com.alex44.fcbate.news.presenter.NewsPagerItemPresenter;
import com.alex44.fcbate.news.presenter.NewsPresenter;
import com.alex44.fcbate.news.ui.NewsRVAdapter;
import com.alex44.fcbate.newsdetail.presenter.NewsDetailPresenter;
import com.alex44.fcbate.newsdetail.ui.NewsDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageModule.class, CiceroneModule.class})
public interface AppComponent {
    void inject(HomePresenter homePresenter);

    void inject(NewsPresenter newsPresenter);

    void inject(MainPresenter mainPresenter);

    void inject(NewsDetailPresenter newsDetailPresenter);

    void inject(NewsItemPresenter newsItemPresenter);

    void inject(MatchItemFragment matchItemFragment);

    void inject(NewsItemFragment newsItemFragment);

    void inject(NewsDetailFragment newsDetailFragment);

    void inject(MainActivity mainActivity);

    void inject(NewsPagerItemPresenter presenter);

    void inject(NewsRVAdapter adapter);
}
