package com.alex44.fcbate.teamdetail.model.api;

import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;

import static com.alex44.fcbate.common.ApiStrings.PLAYERS_URL;
import static com.alex44.fcbate.common.ApiStrings.TRAINERS_URL;

public interface ITeamDetailSource {

    @GET(PLAYERS_URL)
    Maybe<List<PlayerDTO>> getPlayers();

    @GET(TRAINERS_URL)
    Maybe<List<TrainerDTO>> getTrainers();

}
