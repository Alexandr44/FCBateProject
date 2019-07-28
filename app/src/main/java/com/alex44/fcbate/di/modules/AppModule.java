package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public App app() {
        return this.app;
    }

}
