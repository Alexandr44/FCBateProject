package com.alex44.fcbate.match.model.api;

import com.alex44.fcbate.match.model.dto.MatchOnlineMessageDTO;
import com.alex44.fcbate.match.model.dto.VideoDTO;
import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.alex44.fcbate.common.ApiStrings.COMMENTS_URL;
import static com.alex44.fcbate.common.ApiStrings.GAMES_URL;
import static com.alex44.fcbate.common.ApiStrings.PHOTOS_URL;
import static com.alex44.fcbate.common.ApiStrings.VIDEOS_URL;

public interface IMatchSource {

    @GET(GAMES_URL+"/{matchId}/"+COMMENTS_URL)
    Maybe<List<MatchOnlineMessageDTO>> getMessages(@Path("matchId") Long matchId);

    @GET(GAMES_URL+"/{matchId}/"+PHOTOS_URL)
    Maybe<List<PhotoDTO>> getMatchPhotos(@Path("matchId") Long matchId);

    @GET(GAMES_URL+"/{matchId}/"+VIDEOS_URL)
    Maybe<List<VideoDTO>> getMatchVideo(@Path("matchId") Long matchId);

}
