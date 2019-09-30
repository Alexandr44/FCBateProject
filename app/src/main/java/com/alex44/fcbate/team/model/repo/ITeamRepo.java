package com.alex44.fcbate.team.model.repo;

import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface ITeamRepo {

    Maybe<List<PlayerDTO>> getPlayers();

    Maybe<List<TrainerDTO>> getTrainers();

}
