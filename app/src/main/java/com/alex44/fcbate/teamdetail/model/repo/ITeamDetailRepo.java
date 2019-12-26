package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailStatisticDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface ITeamDetailRepo {

    Maybe<TeamDetailDTO> getPlayer(Long id);

    Maybe<TeamDetailDTO> getTrainer(Long id);

    Maybe<List<PhotoDTO>>getPlayerPhotos(Long id);

    Maybe<List<PhotoDTO>>getTrainerPhotos(Long id);

    Maybe<List<TeamDetailStatisticDTO>> getPlayerStats(Long id);
}
