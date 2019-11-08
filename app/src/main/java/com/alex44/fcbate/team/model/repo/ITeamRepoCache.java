package com.alex44.fcbate.team.model.repo;

import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;

import java.util.List;

public interface ITeamRepoCache {

    boolean putPlayers(List<PlayerDTO> playerDTOS);

    List<PlayerDTO> getPlayers();

    boolean putTrainers(List<TrainerDTO> trainerDTOS);

    List<TrainerDTO> getTrainers();

}
