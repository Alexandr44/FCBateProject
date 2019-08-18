package com.alex44.fcbate.di.subcomponents;

import com.alex44.fcbate.di.modules.NewsModule;
import com.alex44.fcbate.di.scopes.NewsScope;
import com.alex44.fcbate.news.presenter.NewsPagerItemPresenter;
import com.alex44.fcbate.news.presenter.NewsPresenter;
import com.alex44.fcbate.news.ui.NewsRVAdapter;

import dagger.Subcomponent;

@NewsScope
@Subcomponent(modules = {NewsModule.class})
public interface NewsSubComponent {

    void inject(NewsPresenter newsPresenter);

    void inject(NewsPagerItemPresenter presenter);

    void inject(NewsRVAdapter adapter);

}
