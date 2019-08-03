package com.alex44.fcbate.di;

import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.CiceroneModule;
import com.alex44.fcbate.di.modules.ImageModule;
import com.alex44.fcbate.di.modules.RepoModule;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.ui.MatchItemFragment;
import com.alex44.fcbate.home.ui.NewsItemFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageModule.class, CiceroneModule.class})
public interface AppComponent {
    void inject(HomePresenter homePresenter);

    void inject(MatchItemFragment matchItemFragment);

    void inject(NewsItemFragment newsItemFragment);
}
