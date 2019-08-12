package com.alex44.fcbate.tournament.model.repo;

import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface ITournamentRepo {

    Maybe<List<TournamentInfoDTO>> getTournamentTable();

}

