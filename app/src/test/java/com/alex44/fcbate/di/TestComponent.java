package com.alex44.fcbate.di;

import com.alex44.fcbate.HomePresenterTest;
import com.alex44.fcbate.di.modules.CiceroneModule;
import com.alex44.fcbate.di.modules.TestRepoModule;
import com.alex44.fcbate.home.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestRepoModule.class, CiceroneModule.class})
public interface TestComponent {

    void inject(HomePresenterTest homePresenterTest);

    void inject(HomePresenter githubPresenter);

}
