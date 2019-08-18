package com.alex44.fcbate.di;

import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.CiceroneModule;
import com.alex44.fcbate.di.modules.ImageModule;
import com.alex44.fcbate.di.subcomponents.CalendarSubComponent;
import com.alex44.fcbate.di.subcomponents.HomeSubComponent;
import com.alex44.fcbate.di.subcomponents.NewsDetailSubComponent;
import com.alex44.fcbate.di.subcomponents.NewsSubComponent;
import com.alex44.fcbate.di.subcomponents.TournamentSubComponent;
import com.alex44.fcbate.main.presenter.MainPresenter;
import com.alex44.fcbate.main.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ImageModule.class, CiceroneModule.class})
public interface AppComponent {

    NewsSubComponent newsSubComponent();

    NewsDetailSubComponent newsDetailSubComponent();

    HomeSubComponent homeSubComponent();

    TournamentSubComponent tournamentSubComponent();

    CalendarSubComponent calendarSubComponent();

    void inject(MainPresenter mainPresenter);

    void inject(MainActivity mainActivity);

}
