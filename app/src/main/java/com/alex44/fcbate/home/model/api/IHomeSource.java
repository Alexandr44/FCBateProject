package com.alex44.fcbate.home.model.api;

import com.alex44.fcbate.home.model.dto.MatchesListResponse;
import com.alex44.fcbate.news.model.dto.NewsDTO;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;

import static com.alex44.fcbate.common.ApiStrings.MATCHES_URL;
import static com.alex44.fcbate.common.ApiStrings.NEWS_URL;
import static com.alex44.fcbate.common.ApiStrings.TOURNAMENTS_URL;

public interface IHomeSource {

    @GET(MATCHES_URL)
    Maybe<MatchesListResponse> getMatches();

    @GET(NEWS_URL)
    Maybe<List<NewsDTO>> getNews();

    @GET(TOURNAMENTS_URL)
    Maybe<List<List<String>>> getTournamentsInfo();

}
