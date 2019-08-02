package com.alex44.fcbate.di;

import com.alex44.fcbate.HomeRepoCacheInstrumentedTest;
import com.alex44.fcbate.HomeRepoInstrumentedTest;
import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.RepoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepoModule.class, AppModule.class})
public interface TestComponent {

    void inject(HomeRepoInstrumentedTest homeRepoInstrumentedTest);

    void inject(HomeRepoCacheInstrumentedTest homeRepoCacheInstrumentedTest);
}
