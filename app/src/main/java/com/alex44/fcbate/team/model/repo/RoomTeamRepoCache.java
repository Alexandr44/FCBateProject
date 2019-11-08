package com.alex44.fcbate.team.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;
import com.alex44.fcbate.team.model.room.RoomPlayer;
import com.alex44.fcbate.team.model.room.RoomTrainer;

import java.util.ArrayList;
import java.util.List;

public class RoomTeamRepoCache implements ITeamRepoCache {

    @Override
    public boolean putPlayers(List<PlayerDTO> playerDTOS) {
        DatabaseRoom.getInstance().getRoomPlayerDao().deleteAll();
        final List<RoomPlayer> roomPlayers = new ArrayList<>();
        for (PlayerDTO playerDTO : playerDTOS) {
            roomPlayers.add(new RoomPlayer(
                    playerDTO.getId(),
                    playerDTO.getPhotoUrl(),
                    playerDTO.getAmplua(),
                    playerDTO.getPlayerNumber(),
                    playerDTO.getAge(),
                    playerDTO.getFirstName(),
                    playerDTO.getLastName(),
                    playerDTO.getShortName(),
                    playerDTO.getBornDate(),
                    playerDTO.getBornPlace()
            ));
        }
        DatabaseRoom.getInstance().getRoomPlayerDao().insert(roomPlayers);
        return true;
    }

    @Override
    public List<PlayerDTO> getPlayers() {
        final List<RoomPlayer> roomPlayers = DatabaseRoom.getInstance().getRoomPlayerDao().findAll();
        final List<PlayerDTO> playerDTOS = new ArrayList<>();
        for (RoomPlayer roomPlayer : roomPlayers) {
            playerDTOS.add(new PlayerDTO(
                    roomPlayer.getId(),
                    roomPlayer.getPhotoUrl(),
                    roomPlayer.getAmplua(),
                    roomPlayer.getPlayerNumber(),
                    roomPlayer.getAge(),
                    roomPlayer.getFirstName(),
                    roomPlayer.getLastName(),
                    roomPlayer.getShortName(),
                    roomPlayer.getBornDate(),
                    roomPlayer.getBornPlace()
            ));
        }
        return playerDTOS;
    }

    @Override
    public boolean putTrainers(List<TrainerDTO> trainerDTOS) {
        DatabaseRoom.getInstance().getRoomTrainerDao().deleteAll();
        final List<RoomTrainer> roomTrainers = new ArrayList<>();
        for (TrainerDTO trainerDTO : trainerDTOS) {
            roomTrainers.add(new RoomTrainer(
                    trainerDTO.getId(),
                    trainerDTO.getPhotoUrl(),
                    trainerDTO.getAge(),
                    trainerDTO.getTitle(),
                    trainerDTO.getPost(),
                    trainerDTO.getBornDate()
            ));
        }
        DatabaseRoom.getInstance().getRoomTrainerDao().insert(roomTrainers);
        return true;
    }

    @Override
    public List<TrainerDTO> getTrainers() {
        final List<RoomTrainer> roomTrainers = DatabaseRoom.getInstance().getRoomTrainerDao().findAll();
        final List<TrainerDTO> trainerDTOS = new ArrayList<>();
        for (RoomTrainer roomTrainer : roomTrainers) {
            trainerDTOS.add(new TrainerDTO(
                    roomTrainer.getId(),
                    roomTrainer.getPhotoUrl(),
                    roomTrainer.getAge(),
                    roomTrainer.getTitle(),
                    roomTrainer.getPost(),
                    roomTrainer.getBornDate()
            ));
        }
        return trainerDTOS;
    }
}
