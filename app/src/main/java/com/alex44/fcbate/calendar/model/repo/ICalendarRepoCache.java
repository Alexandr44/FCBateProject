package com.alex44.fcbate.calendar.model.repo;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;

import java.util.List;

public interface ICalendarRepoCache {

    List<MatchDTO> getGames(int lastCount);

    boolean putGames (List<MatchDTO> matches);

}
