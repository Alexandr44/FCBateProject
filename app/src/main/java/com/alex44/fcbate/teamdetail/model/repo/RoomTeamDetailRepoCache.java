package com.alex44.fcbate.teamdetail.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailStatisticDTO;
import com.alex44.fcbate.teamdetail.model.room.RoomTeamDetail;
import com.alex44.fcbate.teamdetail.model.room.RoomTeamDetailPhoto;
import com.alex44.fcbate.teamdetail.model.room.RoomTeamDetailStatistic;

import java.util.ArrayList;
import java.util.List;

public class RoomTeamDetailRepoCache implements ITeamDetailRepoCache {
    private final int TYPE_PLAYER = 10;
    private final int TYPE_TRAINER = 20;

    @Override
    public TeamDetailDTO getPlayer(Long id) {
        final String detailId = TYPE_PLAYER+"_"+id;
        final RoomTeamDetail roomTeamDetail = DatabaseRoom.getInstance().getRoomTeamDetailDao().findByDetailId(detailId);
        if (roomTeamDetail == null) return null;

        return new TeamDetailDTO(
                roomTeamDetail.getId(),
                roomTeamDetail.getPhotoUrl(),
                roomTeamDetail.getAmplua(),
                roomTeamDetail.getPost(),
                roomTeamDetail.getTitle(),
                roomTeamDetail.getPlayerNumber(),
                roomTeamDetail.getCitizenship(),
                roomTeamDetail.getAge(),
                roomTeamDetail.getFirstName(),
                roomTeamDetail.getLastName(),
                roomTeamDetail.getShortName(),
                roomTeamDetail.getBornDate(),
                roomTeamDetail.getBornPlace(),
                roomTeamDetail.getContent(),
                roomTeamDetail.getBiography(),
                roomTeamDetail.getAnketa()
        );
    }

    @Override
    public boolean putPlayer(TeamDetailDTO teamDetailDTO) {
        final String detailId = TYPE_PLAYER+"_"+teamDetailDTO.getId();
        final RoomTeamDetail roomTeamDetail = new RoomTeamDetail(
                detailId,
                teamDetailDTO.getId(),
                teamDetailDTO.getPhotoUrl(),
                teamDetailDTO.getAmplua(),
                teamDetailDTO.getPost(),
                teamDetailDTO.getTitle(),
                teamDetailDTO.getPlayerNumber(),
                teamDetailDTO.getCitizenship(),
                teamDetailDTO.getAge(),
                teamDetailDTO.getFirstName(),
                teamDetailDTO.getLastName(),
                teamDetailDTO.getShortName(),
                teamDetailDTO.getBornDate(),
                teamDetailDTO.getBornPlace(),
                teamDetailDTO.getContent(),
                teamDetailDTO.getBiography(),
                teamDetailDTO.getAnketa()
        );
        DatabaseRoom.getInstance().getRoomTeamDetailDao().insert(roomTeamDetail);
        return true;
    }

    @Override
    public TeamDetailDTO getTrainer(Long id) {
        final String detailId = TYPE_TRAINER+"_"+id;
        final RoomTeamDetail roomTeamDetail = DatabaseRoom.getInstance().getRoomTeamDetailDao().findByDetailId(detailId);
        if (roomTeamDetail == null) return null;

        return new TeamDetailDTO(
                roomTeamDetail.getId(),
                roomTeamDetail.getPhotoUrl(),
                roomTeamDetail.getAmplua(),
                roomTeamDetail.getPost(),
                roomTeamDetail.getTitle(),
                roomTeamDetail.getPlayerNumber(),
                roomTeamDetail.getCitizenship(),
                roomTeamDetail.getAge(),
                roomTeamDetail.getFirstName(),
                roomTeamDetail.getLastName(),
                roomTeamDetail.getShortName(),
                roomTeamDetail.getBornDate(),
                roomTeamDetail.getBornPlace(),
                roomTeamDetail.getContent(),
                roomTeamDetail.getBiography(),
                roomTeamDetail.getAnketa()
        );
    }

    @Override
    public boolean putTrainer(TeamDetailDTO teamDetailDTO) {
        final String detailId = TYPE_TRAINER+"_"+teamDetailDTO.getId();
        final RoomTeamDetail roomTeamDetail = new RoomTeamDetail(
                detailId,
                teamDetailDTO.getId(),
                teamDetailDTO.getPhotoUrl(),
                teamDetailDTO.getAmplua(),
                teamDetailDTO.getPost(),
                teamDetailDTO.getTitle(),
                teamDetailDTO.getPlayerNumber(),
                teamDetailDTO.getCitizenship(),
                teamDetailDTO.getAge(),
                teamDetailDTO.getFirstName(),
                teamDetailDTO.getLastName(),
                teamDetailDTO.getShortName(),
                teamDetailDTO.getBornDate(),
                teamDetailDTO.getBornPlace(),
                teamDetailDTO.getContent(),
                teamDetailDTO.getBiography(),
                teamDetailDTO.getAnketa()
        );
        DatabaseRoom.getInstance().getRoomTeamDetailDao().insert(roomTeamDetail);
        return true;
    }

    @Override
    public List<PhotoDTO> getPlayerPhotos(Long id) {
        final String memberId = TYPE_PLAYER+"_"+id;
        final List<RoomTeamDetailPhoto> roomPhotoList = DatabaseRoom.getInstance().getRoomTeamDetailPhotoDao().findByMemberId(memberId);
        final List<PhotoDTO> photoDTOList = new ArrayList<>();
        for (RoomTeamDetailPhoto roomPhoto : roomPhotoList) {
            photoDTOList.add(new PhotoDTO(
                roomPhoto.getId(),
                roomPhoto.getBigPhotoUrl(),
                roomPhoto.getSmallPhotoUrl(),
                roomPhoto.getTitle()
            ));
        }
        return photoDTOList;
    }

    @Override
    public boolean putPlayerPhotos(Long teamMemberId, List<PhotoDTO> photoDTOS) {
        final String memberId = TYPE_PLAYER+"_"+teamMemberId;
        final List<RoomTeamDetailPhoto> roomPhotoList = new ArrayList<>();
        for (PhotoDTO photoDTO: photoDTOS) {
            final String photoId = TYPE_PLAYER+"_"+photoDTO.getId();
            roomPhotoList.add(new RoomTeamDetailPhoto(
                    photoId,
                    memberId,
                    photoDTO.getId(),
                    photoDTO.getBigPhotoUrl(),
                    photoDTO.getSmallPhotoUrl(),
                    photoDTO.getTitle()
            ));
        }
        DatabaseRoom.getInstance().getRoomTeamDetailPhotoDao().insert(roomPhotoList);
        return true;
    }

    @Override
    public List<PhotoDTO> getTrainerPhotos(Long id) {
        final String memberId = TYPE_TRAINER+"_"+id;
        final List<RoomTeamDetailPhoto> roomPhotoList = DatabaseRoom.getInstance().getRoomTeamDetailPhotoDao().findByMemberId(memberId);
        final List<PhotoDTO> photoDTOList = new ArrayList<>();
        for (RoomTeamDetailPhoto roomPhoto : roomPhotoList) {
            photoDTOList.add(new PhotoDTO(
                    roomPhoto.getId(),
                    roomPhoto.getBigPhotoUrl(),
                    roomPhoto.getSmallPhotoUrl(),
                    roomPhoto.getTitle()
            ));
        }
        return photoDTOList;
    }

    @Override
    public boolean putTrainerPhotos(Long teamMemberId, List<PhotoDTO> photoDTOS) {
        final String memberId = TYPE_TRAINER+"_"+teamMemberId;
        final List<RoomTeamDetailPhoto> roomPhotoList = new ArrayList<>();
        for (PhotoDTO photoDTO: photoDTOS) {
            final String photoId = TYPE_TRAINER+"_"+photoDTO.getId();
            roomPhotoList.add(new RoomTeamDetailPhoto(
                    photoId,
                    memberId,
                    photoDTO.getId(),
                    photoDTO.getBigPhotoUrl(),
                    photoDTO.getSmallPhotoUrl(),
                    photoDTO.getTitle()
            ));
        }
        DatabaseRoom.getInstance().getRoomTeamDetailPhotoDao().insert(roomPhotoList);
        return true;
    }

    @Override
    public List<TeamDetailStatisticDTO> getPlayerStats(Long id) {
        final List<RoomTeamDetailStatistic> roomStatisticList  = DatabaseRoom.getInstance().getRoomTeamDetailStatisticDao().findByPlayerId(id);
        final List<TeamDetailStatisticDTO> statisticDTOList = new ArrayList<>();
        for (RoomTeamDetailStatistic roomStatistic : roomStatisticList) {
            statisticDTOList.add(new TeamDetailStatisticDTO(
                    roomStatistic.getMatches(),
                    roomStatistic.getMinutes(),
                    roomStatistic.getGoals(),
                    roomStatistic.getGoalPasses(),
                    roomStatistic.getYellowCards(),
                    roomStatistic.getRedCards(),
                    roomStatistic.getDryMatches(),
                    roomStatistic.getMissedGoals(),
                    roomStatistic.getYear()
            ));
        }
        return statisticDTOList;
    }

    @Override
    public boolean putPlayerStats(Long teamMemberId, List<TeamDetailStatisticDTO> teamDetailStatisticDTOs) {
        final List<RoomTeamDetailStatistic> roomStatisticList = new ArrayList<>();
        for (TeamDetailStatisticDTO statisticDTO : teamDetailStatisticDTOs) {
            final String itemId = teamMemberId+"_"+statisticDTO.getYear();
            roomStatisticList.add(new RoomTeamDetailStatistic(
                    itemId,
                    teamMemberId,
                    statisticDTO.getMatches(),
                    statisticDTO.getMinutes(),
                    statisticDTO.getGoals(),
                    statisticDTO.getGoalPasses(),
                    statisticDTO.getYellowCards(),
                    statisticDTO.getRedCards(),
                    statisticDTO.getDryMatches(),
                    statisticDTO.getMissedGoals(),
                    statisticDTO.getYear()
            ));
        }
        DatabaseRoom.getInstance().getRoomTeamDetailStatisticDao().insert(roomStatisticList);
        return true;
    }
}
