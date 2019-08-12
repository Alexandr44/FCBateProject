package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.newsdetail.model.api.INewsDetailSource;
import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NewsDetailRepo implements INewsDetailRepo {

    private final INewsDetailSource newsDetailSource;

    private final INewsDetailRepoCache newsDetailRepoCache;

    private final INetworkStatus networkStatus;

    public NewsDetailRepo(INewsDetailSource newsDetailSource, INewsDetailRepoCache newsDetailRepoCache, INetworkStatus networkStatus) {
        this.newsDetailSource = newsDetailSource;
        this.newsDetailRepoCache = newsDetailRepoCache;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<NewsItemDetailDTO> getNewsDetail(Long id) {
        Timber.d("Requesting news detail for id: %s", id);
        if (networkStatus.isOnline()) {
            return newsDetailSource.getNewsDetail(id)
                    .subscribeOn(Schedulers.io())
                    .map(newsDetailDTO -> {
                        newsDetailRepoCache.putNewsDetail(newsDetailDTO);
                        return newsDetailDTO;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<NewsItemDetailDTO>) emitter -> {
                final NewsItemDetailDTO newsDetailDTO = newsDetailRepoCache.getNewsDetail(id);
                if (newsDetailDTO == null) {
                    emitter.onError(new RuntimeException("No news detail found in local storage"));
                }
                else {
                    emitter.onSuccess(newsDetailDTO);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<NewsItemDetailDTO> getPressDetail(Long id) {
        Timber.d("Requesting press detail for id: %s", id);
        if (networkStatus.isOnline()) {
            return newsDetailSource.getPressDetail(id)
                    .subscribeOn(Schedulers.io())
                    .map(pressDetailDTO -> {
                        newsDetailRepoCache.putPressDetail(pressDetailDTO);
                        return pressDetailDTO;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<NewsItemDetailDTO>) emitter -> {
                final NewsItemDetailDTO itemDetailDTO = newsDetailRepoCache.getPressDetail(id);
                if (itemDetailDTO == null) {
                    emitter.onError(new RuntimeException("No press detail found in local storage"));
                }
                else {
                    emitter.onSuccess(itemDetailDTO);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<NewsItemDetailDTO> getDeclarationDetail(Long id) {
        Timber.d("Requesting declarations detail for id: %s", id);
        if (networkStatus.isOnline()) {
            return newsDetailSource.getDeclarationsDetail(id)
                    .subscribeOn(Schedulers.io())
                    .map(declarationDetailDTO -> {
                        newsDetailRepoCache.putDeclarationsDetail(declarationDetailDTO);
                        return declarationDetailDTO;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<NewsItemDetailDTO>) emitter -> {
                final NewsItemDetailDTO itemDetailDTO = newsDetailRepoCache.getDeclarationsDetail(id);
                if (itemDetailDTO == null) {
                    emitter.onError(new RuntimeException("No declaration detail found in local storage"));
                }
                else {
                    emitter.onSuccess(itemDetailDTO);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

}
