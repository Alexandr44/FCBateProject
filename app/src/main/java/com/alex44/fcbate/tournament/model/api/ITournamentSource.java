package com.alex44.fcbate.tournament.model.api;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;

import static com.alex44.fcbate.common.ApiStrings.TOURNAMENTS_URL;

public interface ITournamentSource {

    @GET(TOURNAMENTS_URL)
    Maybe<List<List<String>>> getTournamentTable();

}
