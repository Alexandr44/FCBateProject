package com.alex44.fcbate.di;

import com.alex44.fcbate.HomeRepoCacheInstrumentedTest;
import com.alex44.fcbate.HomeRepoInstrumentedTest;
import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.HomeModule;
import com.alex44.fcbate.di.scopes.HomeScope;

import dagger.Component;

@HomeScope
@Component(modules = {HomeModule.class, AppModule.class})
public interface TestComponent {

    void inject(HomeRepoInstrumentedTest homeRepoInstrumentedTest);

    void inject(HomeRepoCacheInstrumentedTest homeRepoCacheInstrumentedTest);
}
