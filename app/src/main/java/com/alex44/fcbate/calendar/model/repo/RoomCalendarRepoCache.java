package com.alex44.fcbate.calendar.model.repo;

import com.alex44.fcbate.calendar.model.dto.MatchDTO;
import com.alex44.fcbate.calendar.model.dto.TeamDTO;
import com.alex44.fcbate.calendar.model.dto.TournamentDTO;
import com.alex44.fcbate.calendar.model.room.RoomMatch;
import com.alex44.fcbate.calendar.model.room.RoomTeam;
import com.alex44.fcbate.calendar.model.room.RoomTournament;
import com.alex44.fcbate.common.model.db.DatabaseRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomCalendarRepoCache implements ICalendarRepoCache {
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    public List<MatchDTO> getGames(int lastCount) {
        final List<RoomMatch> matches = DatabaseRoom.getInstance().getMatchDao().findLast(lastCount);
        final List<MatchDTO> matchDTOs = new ArrayList<>();
        for (RoomMatch match : matches) {
            final MatchDTO matchDTO = new MatchDTO();
            final Date date = new Date(match.getDate());
            matchDTO.setId(match.getId());
            matchDTO.setDateStr(dateTimeFormat.format(date));
            matchDTO.setGoalsLeft(match.getGoalsLeft());
            matchDTO.setGoalsRight(match.getGoalsRight());
            matchDTO.setOnline(match.isOnline());
            final RoomTeam roomLeftTeam = DatabaseRoom.getInstance().getTeamDao().findById(match.getLeftTeamId());
            matchDTO.setLeftTeam(new TeamDTO(roomLeftTeam.getId(), roomLeftTeam.getLogoUrl(), roomLeftTeam.getTitle()));
            final RoomTeam roomRightTeam = DatabaseRoom.getInstance().getTeamDao().findById(match.getRightTeamId());
            matchDTO.setRightTeam(new TeamDTO(roomRightTeam.getId(), roomRightTeam.getLogoUrl(), roomRightTeam.getTitle()));
            final RoomTournament tournament = DatabaseRoom.getInstance().getTournamentDao().findById(match.getTournamentId());
            matchDTO.setTournament(new TournamentDTO(tournament.getId(), tournament.getTitle(), tournament.getTitleShort()));
            matchDTOs.add(matchDTO);
        }
        return matchDTOs;
    }

    @Override
    public boolean putGames(List<MatchDTO> matches) {
        final List<RoomMatch> roomMatchList = new ArrayList<>();
        for(MatchDTO matchDTO : matches) {
            try {
                RoomMatch roomMatch = new RoomMatch();
                roomMatch.setId(matchDTO.getId());
                Date date = dateTimeFormat.parse(matchDTO.getDateStr());
                roomMatch.setDate(date.getTime());
                roomMatch.setGoalsLeft(matchDTO.getGoalsLeft());
                roomMatch.setGoalsRight(matchDTO.getGoalsRight());
                roomMatch.setOnline(matchDTO.isOnline());
                final RoomTeam leftTeam = new RoomTeam(matchDTO.getLeftTeam().getId(),
                        matchDTO.getLeftTeam().getLogoUrl(),
                        matchDTO.getLeftTeam().getTitle());
                final RoomTeam rightTeam = new RoomTeam(matchDTO.getRightTeam().getId(),
                        matchDTO.getRightTeam().getLogoUrl(),
                        matchDTO.getRightTeam().getTitle());
                final RoomTournament tournament = new RoomTournament(matchDTO.getTournament().getId(),
                        matchDTO.getTournament().getTitle(), matchDTO.getTournament().getTitleShort());
                DatabaseRoom.getInstance().getTeamDao().insert(leftTeam, rightTeam);
                DatabaseRoom.getInstance().getTournamentDao().insert(tournament);
                roomMatch.setLeftTeamId(matchDTO.getLeftTeam().getId());
                roomMatch.setRightTeamId(matchDTO.getRightTeam().getId());
                roomMatch.setTournamentId(matchDTO.getTournament().getId());
                roomMatchList.add(roomMatch);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        DatabaseRoom.getInstance().getMatchDao().insert(roomMatchList);
        return true;
    }
}
