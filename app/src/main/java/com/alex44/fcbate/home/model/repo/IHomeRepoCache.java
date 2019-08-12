package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

public interface IHomeRepoCache {

    List<MatchDTO> getMatches();

    MatchDTO getMatchById(Long id);

    List<NewsItemDTO> getNews(int count);

    NewsItemDTO getNewsById(Long id);

    List<TournamentInfoDTO> getTournamentsInfo();

    TournamentInfoDTO getTournamentInfoByPosition(Long position);

    boolean putMatches(List<MatchDTO> matches);

    boolean putNews(List<NewsItemDTO> news);

    boolean putTournamentInfos(List<TournamentInfoDTO> tournamentInfos);

}
