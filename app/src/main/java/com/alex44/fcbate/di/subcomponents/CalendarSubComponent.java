package com.alex44.fcbate.di.subcomponents;

import com.alex44.fcbate.calendar.presenter.CalendarPresenter;
import com.alex44.fcbate.calendar.ui.CalendarRVAdapter;
import com.alex44.fcbate.di.modules.CalendarModule;
import com.alex44.fcbate.di.scopes.CalendarScope;

import dagger.Subcomponent;

@CalendarScope
@Subcomponent(modules = {CalendarModule.class})
public interface CalendarSubComponent {


    void inject(CalendarPresenter calendarPresenter);

    void inject(CalendarRVAdapter adapter);
}
