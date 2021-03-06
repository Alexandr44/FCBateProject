package com.alex44.fcbate.teamdetail.model.api;

import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;

import java.util.List;
import java.util.Map;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.alex44.fcbate.common.ApiStrings.PHOTOS_URL;
import static com.alex44.fcbate.common.ApiStrings.PLAYERS_URL;
import static com.alex44.fcbate.common.ApiStrings.STATS_URL;
import static com.alex44.fcbate.common.ApiStrings.TRAINERS_URL;

public interface ITeamDetailSource {

    @GET(PLAYERS_URL+"/{id}")
    Maybe<TeamDetailDTO> getPlayerDetail(@Path("id") Long id);

    @GET(TRAINERS_URL+"/{id}")
    Maybe<TeamDetailDTO> getTrainerDetail(@Path("id") Long id);

    @GET(PLAYERS_URL+"/{id}/"+PHOTOS_URL)
    Maybe<List<PhotoDTO>> getPlayerPhotos(@Path("id") Long id);

    @GET(TRAINERS_URL+"/{id}/"+PHOTOS_URL)
    Maybe<List<PhotoDTO>> getTrainerPhotos(@Path("id") Long id);

    @GET(PLAYERS_URL+"/{id}/"+STATS_URL)
    Maybe<Map<String, List<String>>> getPlayerStats(@Path("id") Long id);

}
