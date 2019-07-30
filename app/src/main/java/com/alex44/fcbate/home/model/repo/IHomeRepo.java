package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface IHomeRepo {

    Maybe<List<MatchDTO>> getMatches();

    Maybe<List<NewsDTO>> getNews();

    Maybe<List<TournamentInfoDTO>> getTournamentsInfo();

}
