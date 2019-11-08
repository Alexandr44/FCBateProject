package com.alex44.fcbate.about.model.repo;

import com.alex44.fcbate.about.model.api.IAboutSource;
import com.alex44.fcbate.about.model.dto.AboutDTO;
import com.alex44.fcbate.common.model.INetworkStatus;

import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AboutRepo implements IAboutRepo {

    private final IAboutSource aboutSource;

    private final IAboutRepoCache repoCache;

    private final INetworkStatus networkStatus;

    public AboutRepo(IAboutSource aboutSource, IAboutRepoCache repoCache, INetworkStatus networkStatus) {
        this.aboutSource = aboutSource;
        this.repoCache = repoCache;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<AboutDTO> getAbout() {
        Timber.d("Requesting about");
        if (networkStatus.isOnline()) {
            return aboutSource.getAbout()
                    .subscribeOn(Schedulers.io())
                    .map(aboutDTO -> {
                        repoCache.putAbout(aboutDTO);
                        return aboutDTO;
                    });
        }
        else {
            return Maybe.create((MaybeOnSubscribe<AboutDTO>) emitter -> {
                final AboutDTO aboutDTO = repoCache.getAbout();
                if (aboutDTO == null) {
                    emitter.onError(new RuntimeException("No about info found in local storage"));
                }
                else {
                    emitter.onSuccess(aboutDTO);
                }
            })
                    .subscribeOn(Schedulers.io());
        }
    }
}