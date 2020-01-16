package com.alex44.fcbate.match.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.match.model.api.IMatchSource;
import com.alex44.fcbate.match.model.dto.MatchOnlineMessageDTO;
import com.alex44.fcbate.match.model.dto.VideoDTO;
import com.alex44.fcbate.teamdetail.model.dto.PhotoDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MatchRepo implements IMatchRepo {

    private final IMatchSource matchOnlineSource;

//    private final ITeamDetailRepoCache repoCache;

    private final INetworkStatus networkStatus;

    public MatchRepo(IMatchSource matchOnlineSource, /*ITeamDetailRepoCache teamDetailRepoCache,*/ INetworkStatus networkStatus) {
        this.matchOnlineSource = matchOnlineSource;
//        this.repoCache = teamDetailRepoCache;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<List<MatchOnlineMessageDTO>> getMessages(Long matchId) {
        Timber.d("Requesting messages %s", matchId);
        if (networkStatus.isOnline()) {
            return matchOnlineSource.getMessages(matchId)
                    .map(messagesList -> {
                        //put in db
                        return messagesList;
                    })
                    .subscribeOn(Schedulers.io());
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<MatchOnlineMessageDTO>>) emitter -> {
                //TODO: get from db
                final List<MatchOnlineMessageDTO> list = new ArrayList<>();
                if (list == null || list.isEmpty()) {
                    emitter.onError(new RuntimeException("No messages found for current match in local storage"));
                }
                else {
                    emitter.onSuccess(list);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<PhotoDTO>> getMatchPhotos(Long matchId) {
        Timber.d("Requesting photos %s", matchId);
        if (networkStatus.isOnline()) {
            return matchOnlineSource.getMatchPhotos(matchId)
                    .map(photoList -> {
                        //put in db
                        return photoList;
                    })
                    .subscribeOn(Schedulers.io());
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<PhotoDTO>>) emitter -> {
                //TODO: get from db
                final List<PhotoDTO> list = new ArrayList<>();
                if (list.isEmpty()) {
                    emitter.onError(new RuntimeException("No photos found for current match in local storage"));
                }
                else {
                    emitter.onSuccess(list);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<VideoDTO>> getMatchVideos(Long matchId) {
        Timber.d("Requesting videos %s", matchId);
        if (networkStatus.isOnline()) {
            return matchOnlineSource.getMatchVideo(matchId)
                    .map(videoList -> {
                        //put in db
                        return videoList;
                    })
                    .subscribeOn(Schedulers.io());
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<VideoDTO>>) emitter -> {
                //TODO: get from db
                final List<VideoDTO> list = new ArrayList<>();
                if (list.isEmpty()) {
                    emitter.onError(new RuntimeException("No videos found for current match in local storage"));
                }
                else {
                    emitter.onSuccess(list);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }
}
