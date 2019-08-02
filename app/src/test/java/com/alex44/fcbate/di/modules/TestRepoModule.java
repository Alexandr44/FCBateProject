package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.home.model.repo.IHomeRepo;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestRepoModule {

    @Provides
    public IHomeRepo homeRepo() {
        return Mockito.mock(IHomeRepo.class);
    }

}
