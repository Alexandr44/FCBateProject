package com.alex44.fcbate.calendar.model.api;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.alex44.fcbate.common.ApiStrings.GAMES_URL;

public interface ICalendarSource {

    @GET(GAMES_URL)
    Maybe<List<MatchDTO>> getGames(@Query("limit") int count, @Query("direction") int direction);

}
