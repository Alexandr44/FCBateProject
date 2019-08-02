package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;

import java.util.List;

public interface IHomeRepoCache {

    List<MatchDTO> getMatches();

    MatchDTO getMatchById(Long id);

    List<NewsDTO> getNews();

    NewsDTO getNewsById(Long id);

    List<TournamentInfoDTO> getTournamentsInfo();

    TournamentInfoDTO getTournamentInfoByPosition(Long position);

    boolean putMatches(List<MatchDTO> matches);

    boolean putNews(List<NewsDTO> news);

    boolean putTournamentInfos(List<TournamentInfoDTO> tournamentInfos);

}
