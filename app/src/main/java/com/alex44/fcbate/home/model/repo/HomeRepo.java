package com.alex44.fcbate.home.model.repo;

import android.annotation.SuppressLint;

import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.MatchesListResponse;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.utils.api.ApiHolder;
import com.alex44.fcbate.utils.model.ISystemInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HomeRepo {

    private final IHomeSource homeSource;

    private final ISystemInfo systemInfo;

    public HomeRepo(ISystemInfo systemInfo) {
        homeSource = ApiHolder.getInstance().getRetrofit().create(IHomeSource.class);
        this.systemInfo = systemInfo;
    }

    @SuppressLint("NewApi")
    public Maybe<List<MatchDTO>> getMatches() {
        return homeSource.getMatches()
                .map(MatchesListResponse::getList)
                .filter(matchDTOs -> {
                    if (systemInfo.isStreamApiAvailable()) {
                        matchDTOs.removeIf(matchDTO -> matchDTO == null || matchDTO.getLeftTeam() == null || matchDTO.getRightTeam() == null);
                    }
                    return matchDTOs != null && !matchDTOs.isEmpty();
                })
                .subscribeOn(Schedulers.io());
    }

    @SuppressLint("NewApi")
    public Maybe<List<NewsDTO>> getNews() {
        Timber.d("Requesting news");
        return homeSource.getNews()
                .filter(newsDTOS -> {
                    if (systemInfo.isStreamApiAvailable()) {
                        newsDTOS.removeIf(newsDTO -> newsDTO == null || newsDTO.getId() == null || newsDTO.getId() == 0);
                    }
                    return newsDTOS != null && !newsDTOS.isEmpty();
                })
                .subscribeOn(Schedulers.io());
    }

    public Maybe<List<TournamentInfoDTO>> getTournamentsInfo() {
        Timber.d("Requesting tournaments info");
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
                .subscribeOn(Schedulers.io());
    }

}
