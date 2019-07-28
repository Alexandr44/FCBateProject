package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.utils.model.ISystemInfo;
import com.alex44.fcbate.utils.ui.SystemInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemModule {

    @Provides
    public ISystemInfo systemInfo() {
        return new SystemInfo();
    }

}
