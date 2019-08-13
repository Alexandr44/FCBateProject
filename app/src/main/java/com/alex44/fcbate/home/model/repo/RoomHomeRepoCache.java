package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.TeamDTO;
import com.alex44.fcbate.home.model.dto.TournamentDTO;
import com.alex44.fcbate.home.model.room.RoomMatch;
import com.alex44.fcbate.home.model.room.RoomTeam;
import com.alex44.fcbate.home.model.room.RoomTournament;
import com.alex44.fcbate.tournament.model.room.RoomTournamentInfo;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;
import com.alex44.fcbate.news.model.room.RoomNews;
import com.alex44.fcbate.tournament.model.dto.TournamentInfoDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomHomeRepoCache implements IHomeRepoCache {
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    public List<MatchDTO> getMatches() {
        final List<RoomMatch> matches = DatabaseRoom.getInstance().getMatchDao().findLastFive();
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
    public MatchDTO getMatchById(Long id) {
        final RoomMatch match = DatabaseRoom.getInstance().getMatchDao().findById(id);
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
        return matchDTO;
    }

    @Override
    public List<NewsItemDTO> getNews(int count) {
        final List<RoomNews> news = DatabaseRoom.getInstance().getNewsDao().findLast(count);
        final List<NewsItemDTO> newsDTOs = new ArrayList<>();
        for (RoomNews roomNews : news) {
            final Date date = new Date(roomNews.getCreated());
            newsDTOs.add(new NewsItemDTO(roomNews.getId(),
                    roomNews.getPhotoUrl(),
                    dateTimeFormat.format(date),
                    roomNews.getTitle(),
                    roomNews.getBrief()
                    ));
        }
        return newsDTOs;
    }

    @Override
    public NewsItemDTO getNewsById(Long id) {
        final RoomNews news = DatabaseRoom.getInstance().getNewsDao().findById(id);
        final Date date = new Date(news.getCreated());
        return new NewsItemDTO(news.getId(),
                news.getPhotoUrl(),
                dateTimeFormat.format(date),
                news.getTitle(),
                news.getBrief()
        );
    }

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
    public TournamentInfoDTO getTournamentInfoByPosition(Long position) {
        final RoomTournamentInfo tournamentInfo = DatabaseRoom.getInstance().getTournamentInfoDao().findByPosition(position);
        return new TournamentInfoDTO(tournamentInfo.getPosition(),
                tournamentInfo.getTeamName(),
                tournamentInfo.getGames(),
                tournamentInfo.getWins(),
                tournamentInfo.getDraws(),
                tournamentInfo.getLoses(),
                tournamentInfo.getDiffs(),
                tournamentInfo.getPoints()
        );
    }

    @Override
    public boolean putMatches(List<MatchDTO> matches) {
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

    @Override
    public boolean putNews(List<NewsItemDTO> news) {
        final List<RoomNews> roomNewsList = new ArrayList<>();

        for (NewsItemDTO newsDTO : news) {
            try {
                final Date date = dateTimeFormat.parse(newsDTO.getCreated());
                roomNewsList.add(new RoomNews(newsDTO.getId(),
                        newsDTO.getPhotoUrl(),
                        date.getTime(),
                        newsDTO.getTitle(),
                        newsDTO.getBrief()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        DatabaseRoom.getInstance().getNewsDao().insert(roomNewsList);
        return true;
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
