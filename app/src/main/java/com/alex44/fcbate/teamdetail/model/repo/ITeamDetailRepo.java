package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;

import io.reactivex.Maybe;

public interface ITeamDetailRepo {

    Maybe<TeamDetailDTO> getPlayer(Long id);

    Maybe<TeamDetailDTO> getTrainer(Long id);

}
