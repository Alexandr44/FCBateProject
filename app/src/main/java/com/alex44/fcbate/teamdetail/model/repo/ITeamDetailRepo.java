package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailPhotoDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface ITeamDetailRepo {

    Maybe<TeamDetailDTO> getPlayer(Long id);

    Maybe<TeamDetailDTO> getTrainer(Long id);

    Maybe<List<TeamDetailPhotoDTO>>getPlayerPhotos(Long id);

    Maybe<List<TeamDetailPhotoDTO>>getTrainerPhotos(Long id);
}
