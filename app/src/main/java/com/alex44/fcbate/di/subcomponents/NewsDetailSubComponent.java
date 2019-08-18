package com.alex44.fcbate.di.subcomponents;

import com.alex44.fcbate.di.modules.NewsDetailModule;
import com.alex44.fcbate.di.scopes.NewsDetailScope;
import com.alex44.fcbate.newsdetail.presenter.NewsDetailPresenter;
import com.alex44.fcbate.newsdetail.ui.NewsDetailFragment;

import dagger.Subcomponent;

@NewsDetailScope
@Subcomponent(modules = {NewsDetailModule.class})
public interface NewsDetailSubComponent {

    void inject(NewsDetailPresenter newsDetailPresenter);

    void inject(NewsDetailFragment newsDetailFragment);
}
