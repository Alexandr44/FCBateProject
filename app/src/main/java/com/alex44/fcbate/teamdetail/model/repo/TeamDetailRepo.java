package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.teamdetail.model.api.ITeamDetailSource;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailPhotoDTO;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamDetailRepo implements ITeamDetailRepo {

    private final ITeamDetailSource teamDetailSource;

    private final INetworkStatus networkStatus;

    public TeamDetailRepo(ITeamDetailSource teamDetailSource, INetworkStatus networkStatus) {
        this.teamDetailSource = teamDetailSource;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<TeamDetailDTO> getPlayer(Long id) {
        Timber.d("Requesting player %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getPlayerDetail(id)
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }

    @Override
    public Maybe<TeamDetailDTO> getTrainer(Long id) {
        Timber.d("Requesting trainer %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getTrainerDetail(id)
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }

    @Override
    public Maybe<List<TeamDetailPhotoDTO>> getPlayerPhotos(Long id) {
        Timber.d("Requesting player photos %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getPlayerPhotos(id)
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }

    @Override
    public Maybe<List<TeamDetailPhotoDTO>> getTrainerPhotos(Long id) {
        Timber.d("Requesting trainer photos %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getTrainerPhotos(id)
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }
}
