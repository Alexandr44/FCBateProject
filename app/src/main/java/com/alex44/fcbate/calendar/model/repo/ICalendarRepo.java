package com.alex44.fcbate.calendar.model.repo;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface ICalendarRepo {

    Maybe<List<MatchDTO>> getGames(int count, int direction);

}
