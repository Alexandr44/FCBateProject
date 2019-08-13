package com.alex44.fcbate.tournament.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.tournament.model.api.ITournamentSource;
import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TournamentRepo implements ITournamentRepo {

    private final ITournamentSource source;

    private final INetworkStatus networkStatus;

    private final ITournamentRepoCache repoCache;

    public TournamentRepo(ITournamentSource source, INetworkStatus networkStatus, ITournamentRepoCache repoCache) {
        this.source = source;
        this.networkStatus = networkStatus;
        this.repoCache = repoCache;
    }

    @Override
    public Maybe<List<TournamentInfoDTO>> getTournamentTable() {
        Timber.d("Requesting tournament table");
        if (networkStatus.isOnline()) {
            return source.getTournamentTable()
                    .map(lists -> {
                        final List<TournamentInfoDTO> info = new ArrayList<>();
                        for (List<String> list : lists) {
                            if (list.size() == 8) {
                                final TournamentInfoDTO tournamentInfo = new TournamentInfoDTO();
                                tournamentInfo.setPosition(Long.valueOf(list.get(0).replace(".", "")));
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
                        repoCache.putTournamentInfos(tournamentInfoDTOS);
                        return tournamentInfoDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<TournamentInfoDTO>>) emitter -> {
                final List<TournamentInfoDTO> tournamentInfos = repoCache.getTournamentsInfo();
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
