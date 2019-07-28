package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.utils.model.INetworkStatus;
import com.alex44.fcbate.utils.ui.NetworkStatus;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    public INetworkStatus networkStatus() {
        return new NetworkStatus();
    }

}
