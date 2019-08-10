package com.alex44.fcbate.home.model.repo;

import android.annotation.SuppressLint;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.common.model.ISystemInfo;
import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.MatchesListResponse;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.news.model.dto.NewsDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HomeRepo implements IHomeRepo {

    private final IHomeSource homeSource;

    private final ISystemInfo systemInfo;

    private final INetworkStatus networkStatus;

    private final IHomeRepoCache homeRepoCache;

    public HomeRepo(ISystemInfo systemInfo, IHomeSource homeSource, INetworkStatus networkStatus, IHomeRepoCache homeRepoCache) {
        this.homeSource = homeSource;
        this.systemInfo = systemInfo;
        this.networkStatus = networkStatus;
        this.homeRepoCache = homeRepoCache;
    }

    @Override
    @SuppressLint("NewApi")
    public Maybe<List<MatchDTO>> getMatches() {
        Timber.d("Requesting matches");
        if (networkStatus.isOnline()) {
            return homeSource.getMatches()
                    .map(MatchesListResponse::getList)
                    .filter(matchDTOs -> {
                        if (systemInfo.isStreamApiAvailable()) {
                            matchDTOs.removeIf(matchDTO -> matchDTO == null || matchDTO.getLeftTeam() == null || matchDTO.getRightTeam() == null);
                        }
                        return matchDTOs != null && !matchDTOs.isEmpty();
                    })
                    .subscribeOn(Schedulers.io())
                    .map(matchDTOS -> {
                        homeRepoCache.putMatches(matchDTOS);
                        return matchDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<MatchDTO>>) emitter -> {
                final List<MatchDTO> matches = homeRepoCache.getMatches();
                if (matches == null || matches.isEmpty()) {
                    emitter.onError(new RuntimeException("No matches found in local storage"));
                } else {
                    emitter.onSuccess(matches);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    @SuppressLint("NewApi")
    public Maybe<List<NewsDTO>> getNews() {
        Timber.d("Requesting news");
        if (networkStatus.isOnline()) {
            return homeSource.getNews()
                    .filter(newsDTOS -> {
                        if (systemInfo.isStreamApiAvailable()) {
                            newsDTOS.removeIf(newsDTO -> newsDTO == null || newsDTO.getId() == null || newsDTO.getId() == 0);
                        }
                        return newsDTOS != null && !newsDTOS.isEmpty();
                    })
                    .subscribeOn(Schedulers.io())
                    .map(newsDTOS -> {
                        homeRepoCache.putNews(newsDTOS);
                        return newsDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<NewsDTO>>) emitter -> {
                final List<NewsDTO> news = homeRepoCache.getNews(5);
                if (news == null || news.isEmpty()) {
                    emitter.onError(new RuntimeException("No news found in local storage"));
                } else {
                    emitter.onSuccess(news);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<TournamentInfoDTO>> getTournamentsInfo() {
        Timber.d("Requesting tournaments info");
        if (networkStatus.isOnline()) {
            return homeSource.getTournamentsInfo()
                    .map(lists -> {
                        final List<TournamentInfoDTO> info = new ArrayList<>();
                        for (List<String> list : lists) {
                            if (list.size() == 8) {
                                final TournamentInfoDTO tournamentInfo = new TournamentInfoDTO();
                                tournamentInfo.setPosition(Long.valueOf(list.get(0).replace(".","")));
                                tournamentInfo.setTeamName(list.get(1));
                                tournamentInfo.setGames(Long.valueOf(list.get(2)));
                                tournamentInfo.setWins(Long.valueOf(list.get(3)));
                                tournamentInfo.setDraws(Long.valueOf(list.get(4)));
                                tournamentInfo.setLoses(Long.valueOf(list.get(5)));
                                tournamentInfo.setDiffs(list.get(6));
                                tournamentInfo.setPoints(Long.valueOf(list.get(7)));
                                info.add(tournamentInfo);
                            }
                        }
                        return info;
                    })
                    .subscribeOn(Schedulers.io())
                    .map(tournamentInfoDTOS -> {
                        homeRepoCache.putTournamentInfos(tournamentInfoDTOS);
                        return tournamentInfoDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<TournamentInfoDTO>>) emitter -> {
                final List<TournamentInfoDTO> tournamentInfos = homeRepoCache.getTournamentsInfo();
                if (tournamentInfos == null || tournamentInfos.isEmpty()) {
                    emitter.onError(new RuntimeException("No tournaments info found in local storage"));
                } else {
                    emitter.onSuccess(tournamentInfos);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

}
