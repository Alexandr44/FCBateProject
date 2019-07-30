package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;

import java.util.List;

public interface IHomeRepoCache {

    List<MatchDTO> getMatches();

    List<NewsDTO> getNews();

    List<TournamentInfoDTO> getTournamentsInfo();

    boolean putMatches(List<MatchDTO> matches);

    boolean putNews(List<NewsDTO> news);

    boolean putTournamentInfos(List<TournamentInfoDTO> tournamentInfos);

}
