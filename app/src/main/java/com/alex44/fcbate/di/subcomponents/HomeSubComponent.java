package com.alex44.fcbate.di.subcomponents;

import com.alex44.fcbate.di.modules.HomeModule;
import com.alex44.fcbate.di.scopes.HomeScope;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.presenter.NewsItemPresenter;
import com.alex44.fcbate.home.ui.MatchItemFragment;
import com.alex44.fcbate.home.ui.NewsItemFragment;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = {HomeModule.class})
public interface HomeSubComponent {

    void inject(NewsItemFragment newsItemFragment);

    void inject(NewsItemPresenter newsItemPresenter);

    void inject(MatchItemFragment matchItemFragment);

    void inject(HomePresenter homePresenter);
}
