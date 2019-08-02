package com.alex44.fcbate;

import androidx.test.InstrumentationRegistry;

import com.alex44.fcbate.di.DaggerTestComponent;
import com.alex44.fcbate.di.TestComponent;
import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.dto.TeamDTO;
import com.alex44.fcbate.home.model.dto.TournamentDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.home.model.repo.IHomeRepoCache;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import timber.log.Timber;

import static org.junit.Assert.assertEquals;

public class HomeRepoCacheInstrumentedTest {

    @Named("Room")
    @Inject
    IHomeRepoCache homeRepoCache;

    private Long id1;
    private String logoUrl1;
    private String title1;
    private Long id2;
    private String logoUrl2;
    private String title2;
    private Long id;
    private String title;
    private String titleShort;
    private String anyDateStr;
    private int goals1;
    private int goals2;
    private boolean online;
    private String url;
    private Long position;
    private Long games;
    private Long wins;
    private Long draws;
    private Long loses;
    private String diffs;
    private Long points;

    @Before
    public void setup() {
        Timber.d("setup");

        id1 = 10114L;
        logoUrl1 = "AnyUrl1";
        title1 = "AnyTitle1";
        id2 = 20324L;
        logoUrl2 = "AnyUrl2";
        title2 = "AnyTitle2";
        id = 3244L;
        title = "SimplsTitle";
        titleShort = "ShortTitle";
        anyDateStr = "2019-07-17 21:00:00";
        goals1 = 2;
        goals2 = 3;
        online = false;
        url = "AnyUrl";
        position = 1L;
        games = 10L;
        wins = 11L;
        draws = 12L;
        loses = 13L;
        diffs = "10 - 20";
        points = 14L;

        final TestComponent component = DaggerTestComponent
                .builder()
                .appModule(new AppModule((App) InstrumentationRegistry.getTargetContext().getApplicationContext()))
                .build();
        component.inject(this);
    }

    @Test
    public void getMatch() {
        Timber.d("Test started: getMatches");

        final TeamDTO teamDTO1 = new TeamDTO(id1, logoUrl1, title1);
        final TeamDTO teamDTO2 = new TeamDTO(id2, logoUrl2, title2);
        final TournamentDTO tournamentDTO = new TournamentDTO(id, title, titleShort);
        final MatchDTO matchDTO = new MatchDTO(id, teamDTO1, teamDTO2, tournamentDTO, anyDateStr, goals1, goals2, online);
        final List<MatchDTO> list = new ArrayList<>();
        list.add(matchDTO);

        TestObserver<MatchDTO> observer = new TestObserver<>();
        Single.fromCallable(() -> {
            homeRepoCache.putMatches(list);
            return homeRepoCache.getMatchById(matchDTO.getId());
        }).subscribe(observer);
        observer.awaitTerminalEvent();

        observer.assertValueCount(1);

        assertEquals(id, observer.values().get(0).getId());
        assertEquals(id1, observer.values().get(0).getLeftTeam().getId());
        assertEquals(logoUrl1, observer.values().get(0).getLeftTeam().getLogoUrl());
        assertEquals(title1, observer.values().get(0).getLeftTeam().getTitle());
        assertEquals(id2, observer.values().get(0).getRightTeam().getId());
        assertEquals(logoUrl2, observer.values().get(0).getRightTeam().getLogoUrl());
        assertEquals(title2, observer.values().get(0).getRightTeam().getTitle());
        assertEquals(title, observer.values().get(0).getTournament().getTitle());
        assertEquals(titleShort, observer.values().get(0).getTournament().getTitleShort());
        assertEquals(id, observer.values().get(0).getTournament().getId());
        assertEquals(anyDateStr, observer.values().get(0).getDateStr());
        assertEquals(goals1, observer.values().get(0).getGoalsLeft());
        assertEquals(goals2, observer.values().get(0).getGoalsRight());
        assertEquals(online, observer.values().get(0).isOnline());

        Timber.d("Test finished: getMatches");
    }

    @Test
    public void getNews() {
        Timber.d("Test started: getNews");

        final List<NewsDTO> list = new ArrayList<>();
        final NewsDTO newsDTO = new NewsDTO(id, url, anyDateStr, title, titleShort);
        list.add(newsDTO);

        TestObserver<NewsDTO> observer = new TestObserver<>();
        Single.fromCallable(() -> {
            homeRepoCache.putNews(list);
            return homeRepoCache.getNewsById(newsDTO.getId());
        }).subscribe(observer);
        observer.awaitTerminalEvent();

        observer.assertValueCount(1);

        assertEquals(id, observer.values().get(0).getId());
        assertEquals(titleShort, observer.values().get(0).getBrief());
        assertEquals(title, observer.values().get(0).getTitle());
        assertEquals(anyDateStr, observer.values().get(0).getCreated());
        assertEquals(url, observer.values().get(0).getPhotoUrl());

        Timber.d("Test finished: getNews");
    }

    @Test
    public void getTournamentInfos() {
        Timber.d("Test started: getTournamentInfos");

        final List<TournamentInfoDTO> list = new ArrayList<>();
        final TournamentInfoDTO tournamentInfoDTO = new TournamentInfoDTO(position, title, games, wins, draws, loses, diffs, points);
        list.add(tournamentInfoDTO);

        TestObserver<TournamentInfoDTO> observer = new TestObserver<>();
        Single.fromCallable(() -> {
            homeRepoCache.putTournamentInfos(list);
            return homeRepoCache.getTournamentInfoByPosition(tournamentInfoDTO.getPosition());
        }).subscribe(observer);
        observer.awaitTerminalEvent();

        observer.assertValueCount(1);

        assertEquals(position, observer.values().get(0).getPosition());
        assertEquals(title, observer.values().get(0).getTeamName());
        assertEquals(games, observer.values().get(0).getGames());
        assertEquals(wins, observer.values().get(0).getWins());
        assertEquals(draws, observer.values().get(0).getDraws());
        assertEquals(loses, observer.values().get(0).getLoses());
        assertEquals(diffs, observer.values().get(0).getDiffs());
        assertEquals(points, observer.values().get(0).getPoints());

        Timber.d("Test finished: getTournamentInfos");
    }

}
