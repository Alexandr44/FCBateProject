package com.alex44.fcbate.teamdetail.model.api;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.alex44.fcbate.common.ApiStrings.PLAYERS_URL;
import static com.alex44.fcbate.common.ApiStrings.TRAINERS_URL;

public interface ITeamDetailSource {

    @GET(PLAYERS_URL+"/{id}")
    Maybe<TeamDetailDTO> getPlayerDetail(@Path("id") Long id);

    @GET(TRAINERS_URL+"/{id}")
    Maybe<TeamDetailDTO> getTrainerDetail(@Path("id") Long id);

}
