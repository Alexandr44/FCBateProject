package com.alex44.fcbate.team.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.team.model.api.ITeamSource;
import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamRepo implements ITeamRepo {

    private final ITeamSource teamSource;

    private final INetworkStatus networkStatus;

    public TeamRepo(ITeamSource teamSource, INetworkStatus networkStatus) {
        this.teamSource = teamSource;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<List<PlayerDTO>> getPlayers() {
        Timber.d("Requesting players");
        if (networkStatus.isOnline()) {
            return teamSource.getPlayers()
                    .filter(playerDTOS -> playerDTOS != null && !playerDTOS.isEmpty())
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }

    @Override
    public Maybe<List<TrainerDTO>> getTrainers() {
        Timber.d("Requesting trainers");
        if (networkStatus.isOnline()) {
            return teamSource.getTrainers()
                    .filter(trainerDTOS -> trainerDTOS != null && !trainerDTOS.isEmpty())
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }
}
