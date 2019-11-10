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
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamDetailRepo implements ITeamDetailRepo {

    private final ITeamDetailSource teamDetailSource;

    private final ITeamDetailRepoCache repoCache;

    private final INetworkStatus networkStatus;

    public TeamDetailRepo(ITeamDetailSource teamDetailSource, ITeamDetailRepoCache teamDetailRepoCache, INetworkStatus networkStatus) {
        this.teamDetailSource = teamDetailSource;
        this.repoCache = teamDetailRepoCache;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<TeamDetailDTO> getPlayer(Long id) {
        Timber.d("Requesting player %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getPlayerDetail(id)
                    .subscribeOn(Schedulers.io())
                    .map(teamDetailDTO -> {
                        repoCache.putPlayer(teamDetailDTO);
                        return teamDetailDTO;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<TeamDetailDTO>) emitter -> {
                final TeamDetailDTO playerDetailDTO = repoCache.getPlayer(id);
                if (playerDetailDTO == null || playerDetailDTO.getId() == null) {
                    emitter.onError(new RuntimeException("No detail info for player found in local storage"));
                }
                else {
                    emitter.onSuccess(playerDetailDTO);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<TeamDetailDTO> getTrainer(Long id) {
        Timber.d("Requesting trainer %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getTrainerDetail(id)
                    .subscribeOn(Schedulers.io())
                    .map(teamDetailDTO -> {
                        repoCache.putTrainer(teamDetailDTO);
                        return teamDetailDTO;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<TeamDetailDTO>) emitter -> {
                final TeamDetailDTO trainerDetailDTO = repoCache.getTrainer(id);
                if (trainerDetailDTO == null || trainerDetailDTO.getId() == null) {
                    emitter.onError(new RuntimeException("No detail info for trainer found in local storage"));
                }
                else {
                    emitter.onSuccess(trainerDetailDTO);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<TeamDetailPhotoDTO>> getPlayerPhotos(Long id) {
        Timber.d("Requesting player photos %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getPlayerPhotos(id)
                    .subscribeOn(Schedulers.io())
                    .map(teamDetailPhotoDTOS -> {
                        repoCache.putPlayerPhotos(id, teamDetailPhotoDTOS);
                        return teamDetailPhotoDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<TeamDetailPhotoDTO>>) emitter -> {
                final List<TeamDetailPhotoDTO> list = repoCache.getPlayerPhotos(id);
                if (list == null || list.isEmpty()) {
                    emitter.onError(new RuntimeException("No photo for player found in local storage"));
                }
                else {
                    emitter.onSuccess(list);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<TeamDetailPhotoDTO>> getTrainerPhotos(Long id) {
        Timber.d("Requesting trainer photos %s", id);
        if (networkStatus.isOnline()) {
            return teamDetailSource.getTrainerPhotos(id)
                    .subscribeOn(Schedulers.io())
                    .map(teamDetailPhotoDTOS -> {
                        repoCache.putTrainerPhotos(id, teamDetailPhotoDTOS);
                        return teamDetailPhotoDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<TeamDetailPhotoDTO>>) emitter -> {
                final List<TeamDetailPhotoDTO> list = repoCache.getTrainerPhotos(id);
                if (list == null || list.isEmpty()) {
                    emitter.onError(new RuntimeException("No photo for trainer found in local storage"));
                }
                else {
                    emitter.onSuccess(list);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
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
                                statisticDTO.setMinutes((long) parseStatString(stats.get(1), 0));
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
                        repoCache.putPlayerStats(id, statsList);
                        return statsList;
                    })
                    .subscribeOn(Schedulers.io());
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<TeamDetailStatisticDTO>>) emitter -> {
                final List<TeamDetailStatisticDTO> list = repoCache.getPlayerStats(id);
                if (list == null || list.isEmpty()) {
                    emitter.onError(new RuntimeException("No stats for player found in local storage"));
                }
                else {
                    emitter.onSuccess(list);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    private int parseStatString(String str, int def) {
        if (str == null || str.isEmpty() || str.equals("-")) return def;
        return Integer.parseInt(str);
    }

}
