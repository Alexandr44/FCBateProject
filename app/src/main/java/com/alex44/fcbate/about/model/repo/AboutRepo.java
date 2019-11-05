package com.alex44.fcbate.about.model.repo;

import com.alex44.fcbate.about.model.api.IAboutSource;
import com.alex44.fcbate.about.model.dto.AboutDTO;
import com.alex44.fcbate.common.model.INetworkStatus;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AboutRepo implements IAboutRepo {

    private final IAboutSource aboutSource;

    private final INetworkStatus networkStatus;

    public AboutRepo(IAboutSource aboutSource, INetworkStatus networkStatus) {
        this.aboutSource = aboutSource;
        this.networkStatus = networkStatus;
    }

    @Override
    public Maybe<AboutDTO> getAbout() {
        Timber.d("Requesting about");
        if (networkStatus.isOnline()) {
            return aboutSource.getAbout()
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }
}
