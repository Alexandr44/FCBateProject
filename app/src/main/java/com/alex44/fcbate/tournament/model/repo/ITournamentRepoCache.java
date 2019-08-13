package com.alex44.fcbate.tournament.model.repo;

import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;

import java.util.List;

public interface ITournamentRepoCache {

    List<TournamentInfoDTO> getTournamentsInfo();

    boolean putTournamentInfos(List<TournamentInfoDTO> tournamentInfos);

}
