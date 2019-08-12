package com.alex44.fcbate;

import androidx.test.InstrumentationRegistry;

import com.alex44.fcbate.di.DaggerTestComponent;
import com.alex44.fcbate.di.TestComponent;
import com.alex44.fcbate.di.modules.ApiModule;
import com.alex44.fcbate.di.modules.AppModule;
import com.alex44.fcbate.di.modules.CacheModule;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.TeamDTO;
import com.alex44.fcbate.home.model.dto.TournamentDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.home.model.repo.IHomeRepo;
import com.alex44.fcbate.home.model.repo.IHomeRepoCache;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

import static org.junit.Assert.assertEquals;

public class HomeRepoInstrumentedTest {

    @Inject
    IHomeRepo homeRepo;

    private static MockWebServer mockWebServer;

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

    @BeforeClass
    public static void setupClass() throws IOException {
        Timber.plant(new Timber.DebugTree());
        Timber.d("setupClass");
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException{
        Timber.d("tearDownClass");
        mockWebServer.shutdown();
    }

    @Before
    public void setup(){
        Timber.d("setup");

        id1 = 10L;
        logoUrl1 = "AnyUrl1";
        title1 = "AnyTitle1";
        id2 = 20L;
        logoUrl2 = "AnyUrl2";
        title2 = "AnyTitle2";
        id = 5L;
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

        TestComponent component = DaggerTestComponent
                .builder()
                .appModule(new AppModule((App) InstrumentationRegistry.getTargetContext().getApplicationContext()))
                .apiModule(new ApiModule(){
                    @Override
                    public String baseUrlGithub() {
                        return mockWebServer.url("/").toString();
                    }
                })
                .cacheModule(new CacheModule() {
                    @Override
                    public IHomeRepoCache roomHomeRepoCache() {
                        final IHomeRepoCache cache = Mockito.mock(IHomeRepoCache.class);
                        final TeamDTO teamDTO1 = new TeamDTO(id1, logoUrl1, title1);
                        final TeamDTO teamDTO2 = new TeamDTO(id2, logoUrl2, title2);
                        final TournamentDTO tournamentDTO = new TournamentDTO(id, title, titleShort);
                        final MatchDTO matchDTO = new MatchDTO(id, teamDTO1, teamDTO2, tournamentDTO, anyDateStr, goals1, goals2, online);
                        final List<MatchDTO> list = new ArrayList<>();
                        list.add(matchDTO);
                        final List<NewsItemDTO> listNews = new ArrayList<>();
                        listNews.add(new NewsItemDTO(id, url, anyDateStr, title, titleShort));
                        final List<TournamentInfoDTO> infoList = new ArrayList<>();
                        infoList.add(new TournamentInfoDTO(position, title, games, wins, draws, loses, diffs, points));
                        Mockito.when(cache.getMatches()).thenReturn(list);
                        Mockito.when(cache.getNews(5)).thenReturn(listNews);
                        Mockito.when(cache.getTournamentsInfo()).thenReturn(infoList);
                        Mockito.when(cache.putMatches(Mockito.any())).thenReturn(true);
                        Mockito.when(cache.putNews(Mockito.any())).thenReturn(true);
                        Mockito.when(cache.putTournamentInfos(Mockito.any())).thenReturn(true);
                        return cache;
                    }
                })
                .build();
        component.inject(this);
    }

    @After
    public void tearDown(){
        Timber.d("tearDown");
    }

    @Test
    public void getMatches() {
        Timber.d("Test started: getMatches");

        mockWebServer.enqueue(createMatchResponse());
        TestObserver<List<MatchDTO>> observer = new TestObserver<>();
        homeRepo.getMatches().subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(1, observer.values().get(0).size());

        assertEquals(id, observer.values().get(0).get(0).getId());
        assertEquals(id1, observer.values().get(0).get(0).getLeftTeam().getId());
        assertEquals(logoUrl1, observer.values().get(0).get(0).getLeftTeam().getLogoUrl());
        assertEquals(title1, observer.values().get(0).get(0).getLeftTeam().getTitle());
        assertEquals(id2, observer.values().get(0).get(0).getRightTeam().getId());
        assertEquals(logoUrl2, observer.values().get(0).get(0).getRightTeam().getLogoUrl());
        assertEquals(title2, observer.values().get(0).get(0).getRightTeam().getTitle());
        assertEquals(title, observer.values().get(0).get(0).getTournament().getTitle());
        assertEquals(titleShort, observer.values().get(0).get(0).getTournament().getTitleShort());
        assertEquals(id, observer.values().get(0).get(0).getTournament().getId());
        assertEquals(anyDateStr, observer.values().get(0).get(0).getDateStr());
        assertEquals(goals1, observer.values().get(0).get(0).getGoalsLeft());
        assertEquals(goals2, observer.values().get(0).get(0).getGoalsRight());
        assertEquals(online, observer.values().get(0).get(0).isOnline());

        Timber.d("Test finished: getMatches");
    }

    @Test
    public void getNews() {
        Timber.d("Test started: getNews");

        mockWebServer.enqueue(createNewsResponse());
        TestObserver<List<NewsItemDTO>> observer = new TestObserver<>();
        homeRepo.getNews().subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(1, observer.values().get(0).size());

        assertEquals(id, observer.values().get(0).get(0).getId());
        assertEquals(titleShort, observer.values().get(0).get(0).getBrief());
        assertEquals(title, observer.values().get(0).get(0).getTitle());
        assertEquals(anyDateStr, observer.values().get(0).get(0).getCreated());
        assertEquals(url, observer.values().get(0).get(0).getPhotoUrl());

        Timber.d("Test finished: getNews");
    }

    @Test
    public void getTournamentInfos() {
        Timber.d("Test started: getTournamentInfos");

        mockWebServer.enqueue(createTournamentInfosResponse());
        TestObserver<List<TournamentInfoDTO>> observer = new TestObserver<>();
        homeRepo.getTournamentsInfo().subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(1, observer.values().get(0).size());

        assertEquals(position, observer.values().get(0).get(0).getPosition());
        assertEquals(title, observer.values().get(0).get(0).getTeamName());
        assertEquals(games, observer.values().get(0).get(0).getGames());
        assertEquals(wins, observer.values().get(0).get(0).getWins());
        assertEquals(draws, observer.values().get(0).get(0).getDraws());
        assertEquals(loses, observer.values().get(0).get(0).getLoses());
        assertEquals(diffs, observer.values().get(0).get(0).getDiffs());
        assertEquals(points, observer.values().get(0).get(0).getPoints());

        Timber.d("Test finished: getTournamentInfos");
    }

    private MockResponse createMatchResponse(){
        final String body = "{\n" +
                "    \"games\": [\n" +
                "        {\n" +
                "            \"game_general_id\":"+id+",\n" +
                "            \"team1\": {\n" +
                "                \"team_general_id\":"+id1+",\n" +
                "                \"logo\": \""+logoUrl1+"\",\n" +
                "                \"title\": \""+title1+"\"\n" +
                "            },\n" +
                "            \"team2\": {\n" +
                "                \"team_general_id\":"+id2+",\n" +
                "                \"logo\": \""+logoUrl2+"\",\n" +
                "                \"title\": \""+title2+"\"\n" +
                "            },\n" +
                "            \"tournament\": {\n" +
                "                \"tournament_id\":"+id+",\n" +
                "                \"title\": \""+title+"\",\n" +
                "                \"title_short\": \""+titleShort+"\"\n" +
                "            },\n" +
                "            \"game_date\": \""+anyDateStr+"\",\n" +
                "            \"goals1\":"+goals1+",\n" +
                "            \"goals2\":"+goals2+",\n" +
                "            \"online\":"+online+"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"current\": 3\n" +
                "}";
        return new MockResponse().setBody(body);
    }


    private MockResponse createNewsResponse(){
        final String body = "[" +
                    "{" +
                        "\"news_general_id\":\""+id+"\",\n" +
                        "\"photo\":\""+url+"\",\n" +
                        "\"created\":\""+anyDateStr+"\",\n" +
                        "\"title\":\""+title+"\",\n" +
                        "\"brief\":\""+titleShort+"\"\n" +
                    "}" +
                "]";
        return new MockResponse().setBody(body);
    }

    private MockResponse createTournamentInfosResponse() {
        final String body = "[\n" +
                "   [\n" +
                "        \""+position+"\",\n" +
                "        \""+title+"\",\n" +
                "        \""+games+"\",\n" +
                "        \""+wins+"\",\n" +
                "        \""+draws+"\",\n" +
                "        \""+loses+"\",\n" +
                "        \""+diffs+"\",\n" +
                "        \""+points+"\"\n" +
                "    ]"+
                "]";
        return new MockResponse().setBody(body);
    }

}
