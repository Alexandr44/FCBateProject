package com.alex44.fcbate.home.model.api;

import com.alex44.fcbate.home.model.dto.MatchesListResponse;

import io.reactivex.Maybe;
import retrofit2.http.GET;

import static com.alex44.fcbate.utils.model.ApiStrings.MATCHES_URL;

public interface IHomeSource {

    @GET(MATCHES_URL)
    Maybe<MatchesListResponse> getMatches();

}
