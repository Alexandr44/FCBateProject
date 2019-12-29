package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.common.model.INetworkStatus;
import com.alex44.fcbate.news.model.api.INewsSource;
import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NewsRepo implements INewsRepo {
    private static final int ELEMENTS_TO_REQUEST = 60;

    private final INewsSource source;

    private final INetworkStatus networkStatus;

    private final INewsRepoCache repoCache;

    public NewsRepo(INewsSource source, INetworkStatus networkStatus, INewsRepoCache repoCache) {
        this.source = source;
        this.networkStatus = networkStatus;
        this.repoCache = repoCache;
    }

    @Override
    public Maybe<List<NewsItemDTO>> getNews() {
        Timber.d("Requesting news");
        if (networkStatus.isOnline()) {
            return source.getNews(ELEMENTS_TO_REQUEST)
                    .filter(newsDTOS -> newsDTOS != null && !newsDTOS.isEmpty())
                    .subscribeOn(Schedulers.io())
                    .map(newsDTOS -> {
                        repoCache.putNews(newsDTOS);
                        return newsDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<NewsItemDTO>>) emitter -> {
                final List<NewsItemDTO> result = repoCache.getNews(ELEMENTS_TO_REQUEST);
                if (result == null || result.isEmpty()) {
                    emitter.onError(new RuntimeException("No news found in local storage"));
                }
                else {
                    emitter.onSuccess(result);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<NewsItemDTO>> getPresses() {
        Timber.d("Requesting presses");
        if (networkStatus.isOnline()) {
            return source.getPresses(ELEMENTS_TO_REQUEST)
                    .filter(pressDTOS -> pressDTOS != null && !pressDTOS.isEmpty())
                    .subscribeOn(Schedulers.io())
                    .map(pressDTOS -> {
                        repoCache.putPress(pressDTOS);
                        return pressDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<NewsItemDTO>>) emitter -> {
                final List<NewsItemDTO> result = repoCache.getPress(ELEMENTS_TO_REQUEST);
                if (result == null || result.isEmpty()) {
                    emitter.onError(new RuntimeException("No press found in local storage"));
                }
                else {
                    emitter.onSuccess(result);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Maybe<List<NewsItemDTO>> getDeclarations() {
        Timber.d("Requesting declarations");
        if (networkStatus.isOnline()) {
            return source.getDeclarations(ELEMENTS_TO_REQUEST)
                    .filter(declarationsDTOS -> declarationsDTOS != null && !declarationsDTOS.isEmpty())
                    .subscribeOn(Schedulers.io())
                    .map(declarationsDTOS -> {
                        repoCache.putDeclarations(declarationsDTOS);
                        return declarationsDTOS;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<List<NewsItemDTO>>) emitter -> {
                final List<NewsItemDTO> result = repoCache.getDeclarations(ELEMENTS_TO_REQUEST);
                if (result == null || result.isEmpty()) {
                    emitter.onError(new RuntimeException("No news found in local storage"));
                }
                else {
                    emitter.onSuccess(result);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }
}
