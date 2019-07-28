package com.alex44.fcbate.home.model.repo;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.dto.TournamentInfoDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface IHomeRepo {

    public Maybe<List<MatchDTO>> getMatches();

    public Maybe<List<NewsDTO>> getNews();

    public Maybe<List<TournamentInfoDTO>> getTournamentsInfo();

}
