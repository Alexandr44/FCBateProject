package com.alex44.fcbate.di;

import com.alex44.fcbate.about.presenter.AboutPresenter;
import com.alex44.fcbate.about.ui.AboutFragment;
import com.alex44.fcbate.calendar.presenter.CalendarPresenter;
import com.alex44.fcbate.calendar.ui.CalendarRVAdapter;
import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.CiceroneModule;
import com.alex44.fcbate.di.modules.ImageModule;
import com.alex44.fcbate.di.modules.RepoModule;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.presenter.MatchItemPresenter;
import com.alex44.fcbate.home.presenter.NewsItemPresenter;
import com.alex44.fcbate.home.ui.MatchItemFragment;
import com.alex44.fcbate.home.ui.NewsItemFragment;
import com.alex44.fcbate.info.presenter.InfoPresenter;
import com.alex44.fcbate.main.presenter.MainPresenter;
import com.alex44.fcbate.main.ui.MainActivity;
import com.alex44.fcbate.match.presenter.MatchOnlinePresenter;
import com.alex44.fcbate.match.presenter.MatchPhotoPresenter;
import com.alex44.fcbate.match.presenter.MatchPresenter;
import com.alex44.fcbate.match.presenter.MatchVideoPresenter;
import com.alex44.fcbate.match.ui.MatchFragment;
import com.alex44.fcbate.match.ui.MatchPhotoRVAdapter;
import com.alex44.fcbate.news.presenter.NewsPagerItemPresenter;
import com.alex44.fcbate.news.presenter.NewsPresenter;
import com.alex44.fcbate.news.ui.NewsRVAdapter;
import com.alex44.fcbate.newsdetail.presenter.NewsDetailPresenter;
import com.alex44.fcbate.newsdetail.ui.NewsDetailFragment;
import com.alex44.fcbate.team.presenter.TeamPagerItemPresenter;
import com.alex44.fcbate.team.presenter.TeamPresenter;
import com.alex44.fcbate.team.ui.TeamRVAdapter;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailPhotoPresenter;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailPresenter;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailStatisticPresenter;
import com.alex44.fcbate.teamdetail.ui.TeamDetailFragment;
import com.alex44.fcbate.teamdetail.ui.TeamDetailPhotoFragment;
import com.alex44.fcbate.teamdetail.ui.TeamDetailPhotoRVAdapter;
import com.alex44.fcbate.tournament.presenter.TournamentPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageModule.class, CiceroneModule.class})
public interface AppComponent {
    void inject(HomePresenter homePresenter);

    void inject(NewsPresenter newsPresenter);

    void inject(MainPresenter mainPresenter);

    void inject(NewsDetailPresenter newsDetailPresenter);

    void inject(NewsItemPresenter newsItemPresenter);

    void inject(MatchItemFragment matchItemFragment);

    void inject(NewsItemFragment newsItemFragment);

    void inject(NewsDetailFragment newsDetailFragment);

    void inject(MainActivity mainActivity);

    void inject(NewsPagerItemPresenter presenter);

    void inject(NewsRVAdapter adapter);

    void inject(TournamentPresenter tournamentPresenter);

    void inject(CalendarPresenter calendarPresenter);

    void inject(CalendarRVAdapter adapter);

    void inject(TeamPresenter teamPresenter);

    void inject(TeamPagerItemPresenter presenter);

    void inject(TeamRVAdapter adapter);

    void inject(TeamDetailPresenter teamDetailPresenter);

    void inject(TeamDetailFragment teamDetailFragment);

    void inject(TeamDetailPhotoFragment teamDetailPhotoFragment);

    void inject(TeamDetailPhotoPresenter teamDetailPhotoPresenter);

    void inject(TeamDetailPhotoRVAdapter adapter);

    void inject(TeamDetailStatisticPresenter teamDetailStatisticPresenter);

    void inject(AboutFragment aboutFragment);

    void inject(AboutPresenter aboutPresenter);

    void inject(InfoPresenter infoPresenter);

    void inject(MatchFragment matchFragment);

    void inject(MatchPresenter matchPresenter);

    void inject(MatchItemPresenter matchItemPresenter);

    void inject(MatchOnlinePresenter matchOnlinePresenter);

    void inject(MatchPhotoPresenter matchPhotoPresenter);

    void inject(MatchPhotoRVAdapter adapter);

    void inject(MatchVideoPresenter matchVideoPresenter);

}
