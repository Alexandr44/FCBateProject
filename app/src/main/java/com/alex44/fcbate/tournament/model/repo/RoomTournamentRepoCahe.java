package com.alex44.fcbate.tournament.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.tournament.model.room.RoomTournamentInfo;

import java.util.ArrayList;
import java.util.List;

public class RoomTournamentRepoCahe implements ITournamentRepoCache {

    @Override
    public List<TournamentInfoDTO> getTournamentsInfo() {
        final List<RoomTournamentInfo> tournamentInfos = DatabaseRoom.getInstance().getTournamentInfoDao().findFirstThree();
        final List<TournamentInfoDTO> tournamentInfoDTOs = new ArrayList<>();
        for (RoomTournamentInfo tournamentInfo : tournamentInfos) {
            tournamentInfoDTOs.add(new TournamentInfoDTO(tournamentInfo.getPosition(),
                    tournamentInfo.getTeamName(),
                    tournamentInfo.getGames(),
                    tournamentInfo.getWins(),
                    tournamentInfo.getDraws(),
                    tournamentInfo.getLoses(),
                    tournamentInfo.getDiffs(),
                    tournamentInfo.getPoints()
            ));
        }
        return tournamentInfoDTOs;
    }

    @Override
    public boolean putTournamentInfos(List<TournamentInfoDTO> tournamentInfos) {
        final List<RoomTournamentInfo> tournamentInfoList = new ArrayList<>();
        for(TournamentInfoDTO tournamentInfo : tournamentInfos) {
            tournamentInfoList.add(new RoomTournamentInfo(tournamentInfo.getPosition(),
                    tournamentInfo.getTeamName(),
                    tournamentInfo.getGames(),
                    tournamentInfo.getWins(),
                    tournamentInfo.getDraws(),
                    tournamentInfo.getLoses(),
                    tournamentInfo.getDiffs(),
                    tournamentInfo.getPoints()));
        }
        DatabaseRoom.getInstance().getTournamentInfoDao().insert(tournamentInfoList);
        return true;
    }
}
