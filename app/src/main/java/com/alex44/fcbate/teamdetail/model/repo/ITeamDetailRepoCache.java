package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailPhotoDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailStatisticDTO;

import java.util.List;

public interface ITeamDetailRepoCache {

    TeamDetailDTO getPlayer(Long id);

    boolean putPlayer(TeamDetailDTO teamDetailDTO);

    TeamDetailDTO getTrainer(Long id);

    boolean putTrainer(TeamDetailDTO teamDetailDTO);

    List<TeamDetailPhotoDTO> getPlayerPhotos(Long id);

    boolean putPlayerPhotos(Long teamMemberId, List<TeamDetailPhotoDTO> teamDetailPhotoDTOS);

    List<TeamDetailPhotoDTO> getTrainerPhotos(Long id);

    boolean putTrainerPhotos(Long teamMemberId, List<TeamDetailPhotoDTO> teamDetailPhotoDTOS);

    List<TeamDetailStatisticDTO> getPlayerStats(Long id);

    boolean putPlayerStats(Long teamMemberId, List<TeamDetailStatisticDTO> teamDetailStatisticDTOs);

}
