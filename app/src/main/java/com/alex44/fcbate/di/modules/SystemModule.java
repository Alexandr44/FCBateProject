package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.common.model.ISystemInfo;
import com.alex44.fcbate.common.ui.SystemInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemModule {

    @Provides
    public ISystemInfo systemInfo() {
        return new SystemInfo();
    }

}
