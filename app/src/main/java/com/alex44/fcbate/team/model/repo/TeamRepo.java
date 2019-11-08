package com.alex44.fcbate.team.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.team.model.api.ITeamSource;
import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamRepo implements ITeamRepo {

    private final ITeamSource teamSource;

    private final ITeamRepoCache repoCache;

    private final INetworkStatus networkStatus;

    public TeamRepo(ITeamSource teamSource, ITeamRepoCache repoCache, INetworkStatus networkStatus) {
        this.teamSource = teamSource;
        this.repoCache = repoCache;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<List<PlayerDTO>> getPlayers() {
        Timber.d("Requesting players");
        if (networkStatus.isOnline()) {
            return teamSource.getPlayers()
                    .filter(playerDTOS -> playerDTOS != null && !playerDTOS.isEmpty())
                    .subscribeOn(Schedulers.io())
                    .map(playerDTOs -> {
                        repoCache.putPlayers(playerDTOs);
                        return playerDTOs;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<PlayerDTO>>) emitter -> {
                final List<PlayerDTO> playerDTOS = repoCache.getPlayers();
                if (playerDTOS == null || playerDTOS.isEmpty()) {
                    emitter.onError(new RuntimeException("No players found in local storage"));
                }
                else {
                    emitter.onSuccess(playerDTOS);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<TrainerDTO>> getTrainers() {
        Timber.d("Requesting trainers");
        if (networkStatus.isOnline()) {
            return teamSource.getTrainers()
                    .filter(trainerDTOS -> trainerDTOS != null && !trainerDTOS.isEmpty())
                    .subscribeOn(Schedulers.io())
                    .map(trainerDTOS -> {
                        repoCache.putTrainers(trainerDTOS);
                        return trainerDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<TrainerDTO>>) emitter -> {
                final List<TrainerDTO> trainerDTOS = repoCache.getTrainers();
                if (trainerDTOS == null || trainerDTOS.isEmpty()) {
                    emitter.onError(new RuntimeException("No trainers found in local storage"));
                }
                else {
                    emitter.onSuccess(trainerDTOS);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }
}
