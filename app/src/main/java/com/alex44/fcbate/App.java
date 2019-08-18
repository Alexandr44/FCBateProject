package com.alex44.fcbate;

import android.app.Application;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.di.AppComponent;
import com.alex44.fcbate.di.ComponentManager;
import com.alex44.fcbate.di.DaggerAppComponent;
import com.alex44.fcbate.di.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;

import lombok.Getter;
import timber.log.Timber;

public class App extends Application {

    private static App instance;

    @Getter
    private AppComponent appComponent;

    @Getter
    private ComponentManager componentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        instance = this;
        Timber.plant(new Timber.DebugTree());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        componentManager = ComponentManager.getInstance(appComponent);
        DatabaseRoom.create(getApplicationContext());
    }

    public static App getInstance() {
        return instance;
    }

}
