package com.alex44.fcbate.calendar.model.repo;

import com.alex44.fcbate.calendar.model.api.ICalendarSource;
import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.common.model.INetworkStatus;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CalendarRepo implements ICalendarRepo {
    private static final int GAMES_CACHE_COUNT = 25;

    private final ICalendarSource calendarSource;

    private final INetworkStatus networkStatus;

    private final ICalendarRepoCache repoCache;

    public CalendarRepo(ICalendarSource calendarSource, INetworkStatus networkStatus, ICalendarRepoCache repoCache) {
        this.calendarSource = calendarSource;
        this.networkStatus = networkStatus;
        this.repoCache = repoCache;
    }

    @Override
    public Maybe<List<MatchDTO>> getGames(int count, int direction) {
        Timber.d("Requesting games");
        if (networkStatus.isOnline()) {
            return calendarSource.getGames(count, direction)
                    .filter(gameDTOs -> gameDTOs != null && !gameDTOs.isEmpty())
                    .subscribeOn(Schedulers.io())
                    .map(matchDTOS -> {
                        repoCache.putGames(matchDTOS);
                        return matchDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<MatchDTO>>) emitter -> {
                final List<MatchDTO> matches = repoCache.getGames(GAMES_CACHE_COUNT);
                if (matches == null || matches.isEmpty()) {
                    emitter.onError(new RuntimeException("No games found in local storage"));
                } else {
                    emitter.onSuccess(matches);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }
}