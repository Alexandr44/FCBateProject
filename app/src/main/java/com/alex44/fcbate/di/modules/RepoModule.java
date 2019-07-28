package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.repo.HomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.utils.model.INetworkStatus;
import com.alex44.fcbate.utils.model.ISystemInfo;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SystemModule.class, ApiModule.class, NetworkModule.class})
public class RepoModule {

    @Provides
    public IHomeRepo homeRepo(ISystemInfo systemInfo, IHomeSource homeSource, INetworkStatus networkStatus) {
        return new HomeRepo(systemInfo, homeSource, networkStatus);
    }

}
