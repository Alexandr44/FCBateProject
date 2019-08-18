package com.alex44.fcbate.di;

import com.alex44.fcbate.di.subcomponents.CalendarSubComponent;
import com.alex44.fcbate.di.subcomponents.HomeSubComponent;
import com.alex44.fcbate.di.subcomponents.NewsDetailSubComponent;
import com.alex44.fcbate.di.subcomponents.NewsSubComponent;
import com.alex44.fcbate.di.subcomponents.TournamentSubComponent;

public class ComponentManager {

    private static ComponentManager instance;

    private AppComponent appComponent;

    private NewsSubComponent newsSubComponent;

    private NewsDetailSubComponent newsDetailSubComponent;

    private HomeSubComponent homeSubComponent;

    private TournamentSubComponent tournamentSubComponent;

    private CalendarSubComponent calendarSubComponent;

    private ComponentManager(AppComponent appComponent){
        this.appComponent = appComponent;
    }

    public static ComponentManager getInstance(AppComponent appComponent) {
        ComponentManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ComponentManager.class) {
                localInstance = instance;       //double check
                if (localInstance == null) {
                    instance = localInstance = new ComponentManager(appComponent);
                }
            }
        }
        return localInstance;
    }

    public NewsSubComponent getNewsSubComponent() {
        if (newsSubComponent == null) {
            // start lifecycle of NewsSubComponent
            newsSubComponent = appComponent.newsSubComponent();
        }
        return newsSubComponent;
    }

    public void clearNewsSubComponent() {
        // end lifecycle of NewsSubComponent
        newsSubComponent = null;
    }

    public NewsDetailSubComponent getNewsDetailSubComponent() {
        if (newsDetailSubComponent == null) {
            // start lifecycle of NewsSubComponent
            newsDetailSubComponent = appComponent.newsDetailSubComponent();
        }
        return newsDetailSubComponent;
    }

    public void clearNewsDetailSubComponent() {
        // end lifecycle of NewsSubComponent
        newsDetailSubComponent = null;
    }

    public HomeSubComponent getHomeSubComponent() {
        if (homeSubComponent == null) {
            // start lifecycle of NewsSubComponent
            homeSubComponent = appComponent.homeSubComponent();
        }
        return homeSubComponent;
    }

    public void clearHomeSubComponent() {
        // end lifecycle of NewsSubComponent
        homeSubComponent = null;
    }

    public CalendarSubComponent getCalendarSubComponent() {
        if (calendarSubComponent == null) {
            // start lifecycle of NewsSubComponent
            calendarSubComponent = appComponent.calendarSubComponent();
        }
        return calendarSubComponent;
    }

    public void clearCalendarSubComponent() {
        // end lifecycle of NewsSubComponent
        calendarSubComponent = null;
    }

    public TournamentSubComponent getTournamentSubComponent() {
        if (tournamentSubComponent == null) {
            // start lifecycle of NewsSubComponent
            tournamentSubComponent = appComponent.tournamentSubComponent();
        }
        return tournamentSubComponent;
    }

    public void clearTournamentSubComponent() {
        // end lifecycle of NewsSubComponent
        tournamentSubComponent = null;
    }

}
