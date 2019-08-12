package com.alex44.fcbate;

import com.alex44.fcbate.di.DaggerTestComponent;
import com.alex44.fcbate.di.TestComponent;
import com.alex44.fcbate.di.modules.TestRepoModule;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.TeamDTO;
import com.alex44.fcbate.home.model.dto.TournamentDTO;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.presenter.HomePresenter;
import com.alex44.fcbate.home.view.HomeView;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;
import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.schedulers.TestScheduler;
import timber.log.Timber;

public class HomePresenterTest {

    private HomePresenter homePresenter;
    private TestScheduler testScheduler;

    @Mock
    HomeView homeView;

    @Inject
    IHomeRepo homeRepo;

    private MatchDTO matchDTO;
    private NewsItemDTO newsDTO;
    private TournamentInfoDTO tournamentInfoDTO;

    @BeforeClass
    public static void setupClass() {
        Timber.plant(new Timber.DebugTree());
        Timber.d("setupClass");
    }

    @AfterClass
    public static void tearDownClass() {
        Timber.d("tearDownClass");
    }

    @Before
    public void setup() {
        Timber.d("setup");
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        homePresenter = Mockito.spy(new HomePresenter(testScheduler));

        final TeamDTO teamLeft = new TeamDTO(5L, "anyLogoUrl", "TeamN1");
        final TeamDTO teamRight = new TeamDTO(10L, "otherLogoUrl", "TeamN2");
        final TournamentDTO tournamentDTO = new TournamentDTO(20L, "TournamentName", "Name");
        matchDTO = new MatchDTO(1L, teamLeft, teamRight, tournamentDTO, "DateStr", 2, 3, true);
        newsDTO = new NewsItemDTO(50L, "NewsPhotoUrl", "createdAt", "PhotoTitle", "Brief");
        tournamentInfoDTO = new TournamentInfoDTO(1L, "AnyTeamName", 20L, 15L, 15L, 30L, "20 - 15", 15L);
    }

    @After
    public void tearDown(){
        Timber.d("tearDown");
    }

    @Test
    public void loadSucess() {
        Timber.d("Test started: loadSucess");
        final TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public IHomeRepo homeRepo() {
                        final IHomeRepo homeRepo = super.homeRepo();
                        final List<MatchDTO> matches = new ArrayList<>();
                        matches.add(matchDTO);
                        matches.add(matchDTO);
                        matches.add(matchDTO);
                        matches.add(matchDTO);
                        matches.add(matchDTO);
                        final List<NewsItemDTO> news = new ArrayList<>();
                        news.add(newsDTO);
                        news.add(newsDTO);
                        news.add(newsDTO);
                        news.add(newsDTO);
                        news.add(newsDTO);
                        final List<TournamentInfoDTO> tournamentInfos = new ArrayList<>();
                        tournamentInfos.add(tournamentInfoDTO);
                        tournamentInfos.add(tournamentInfoDTO);
                        tournamentInfos.add(tournamentInfoDTO);
                        Mockito.when(homeRepo.getMatches()).thenReturn(Maybe.just(matches));
                        Mockito.when(homeRepo.getNews()).thenReturn(Maybe.just(news));
                        Mockito.when(homeRepo.getTournamentsInfo()).thenReturn(Maybe.just(tournamentInfos));
                        return homeRepo;
                    }
                })
                .build();

        testComponent.inject(this);
        testComponent.inject(homePresenter);
        homePresenter.attachView(homeView);

        Mockito.verify(homePresenter).initMatchPager();
        Mockito.verify(homePresenter).initNewsPager();
        Mockito.verify(homePresenter).initTournamentsTable();

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        final List<MatchDTO> matches = new ArrayList<>();
        matches.add(matchDTO);
        matches.add(matchDTO);
        matches.add(matchDTO);
        matches.add(matchDTO);
        matches.add(matchDTO);
        final List<NewsItemDTO> news = new ArrayList<>();
        news.add(newsDTO);
        news.add(newsDTO);
        news.add(newsDTO);
        news.add(newsDTO);
        news.add(newsDTO);

        Mockito.verify(homeView).initMatchPager(matches);
        Mockito.verify(homeView).initNewsPager(news);
        final List<TournamentInfoDTO> list = new ArrayList<>();
        list.add(tournamentInfoDTO);
        list.add(tournamentInfoDTO);
        list.add(tournamentInfoDTO);
        Mockito.verify(homeView).fillTable(list);

        Timber.d("Test finished: loadSucess");
    }

    @Test
    public void loadFailed() {
        Timber.d("Test started: loadFailed");
        final TestComponent testComponent = DaggerTestComponent.builder()
                .testRepoModule(new TestRepoModule() {
                    @Override
                    public IHomeRepo homeRepo() {
                        final IHomeRepo homeRepo = super.homeRepo();
                        Mockito.when(homeRepo.getMatches()).thenReturn(Maybe.error(new Throwable("No matches")));
                        Mockito.when(homeRepo.getNews()).thenReturn(Maybe.error(new Throwable("No news")));
                        Mockito.when(homeRepo.getTournamentsInfo()).thenReturn(Maybe.error(new Throwable("No tournament infos")));
                        return homeRepo;
                    }
                })
                .build();

        testComponent.inject(this);
        testComponent.inject(homePresenter);
        homePresenter.attachView(homeView);

        Mockito.verify(homePresenter).initMatchPager();
        Mockito.verify(homePresenter).initNewsPager();
        Mockito.verify(homePresenter).initTournamentsTable();

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(homeView).showMessage("Matches load failed");
        Mockito.verify(homeView).showMessage("News load failed");
        Mockito.verify(homeView).showMessage("Tournaments info load failed");

        Timber.d("Test finished: loadFailed");
    }

}
