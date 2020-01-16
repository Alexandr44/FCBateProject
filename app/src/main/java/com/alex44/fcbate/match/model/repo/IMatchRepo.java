package com.alex44.fcbate.match.model.repo;

import com.alex44.fcbate.match.model.dto.MatchOnlineMessageDTO;
import com.alex44.fcbate.match.model.dto.VideoDTO;
import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface IMatchRepo {

    Maybe<List<MatchOnlineMessageDTO>> getMessages(Long matchId);

    Maybe<List<PhotoDTO>> getMatchPhotos(Long matchId);

    Maybe<List<VideoDTO>> getMatchVideos(Long matchId);

}
