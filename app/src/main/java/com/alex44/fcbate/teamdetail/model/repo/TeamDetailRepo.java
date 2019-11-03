package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.teamdetail.model.api.ITeamDetailSource;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailPhotoDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailStatisticDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Override
    public Maybe<List<TeamDetailStatisticDTO>> getPlayerStats(Long id) {
        Timber.d("Requesting player stats %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getPlayerStats(id)
                    .map(map -> {
                        final List<TeamDetailStatisticDTO> statsList = new ArrayList<>();
                        final Set<String> keys = map.keySet();
                        for (String key: keys) {
                            final List<String> stats = map.get(key);
                            if (stats.size() == 8) {
                                final TeamDetailStatisticDTO statisticDTO = new TeamDetailStatisticDTO();
                                statisticDTO.setMatches(parseStatString(stats.get(0), 0));
                                statisticDTO.setMinutes(Long.valueOf(stats.get(1)));
                                statisticDTO.setGoals(parseStatString(stats.get(2), 0));
                                statisticDTO.setGoalPasses(parseStatString(stats.get(3), 0));
                                statisticDTO.setYellowCards(parseStatString(stats.get(4), 0));
                                statisticDTO.setRedCards(parseStatString(stats.get(5), 0));
                                statisticDTO.setDryMatches(parseStatString(stats.get(6), 0));
                                statisticDTO.setMissedGoals(parseStatString(stats.get(7), 0));
                                statisticDTO.setYear(Integer.parseInt(key));
                                statsList.add(statisticDTO);
                            }
                        }
                        return statsList;
                    })
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }

    private int parseStatString(String str, int def) {
        if (str == null || str.isEmpty() || str.equals("-")) return def;
        return Integer.parseInt(str);
    }

}
