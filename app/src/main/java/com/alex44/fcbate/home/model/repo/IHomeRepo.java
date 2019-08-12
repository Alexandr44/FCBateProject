package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface IHomeRepo {

    Maybe<List<MatchDTO>> getMatches();

    Maybe<List<NewsItemDTO>> getNews();

    Maybe<List<TournamentInfoDTO>> getTournamentsInfo();

}
