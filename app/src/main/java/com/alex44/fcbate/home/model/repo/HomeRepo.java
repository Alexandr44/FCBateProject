package com.alex44.fcbate.home.model.repo;

import android.annotation.SuppressLint;

import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.MatchesListResponse;
import com.alex44.fcbate.utils.api.ApiHolder;
import com.alex44.fcbate.utils.model.ISystemInfo;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class HomeRepo {

    private final IHomeSource homeSource;

    private final ISystemInfo systemInfo;

    public HomeRepo(ISystemInfo systemInfo) {
        homeSource = ApiHolder.getInstance().getRetrofit().create(IHomeSource.class);
        this.systemInfo = systemInfo;
    }

    @SuppressLint("NewApi")
    public Maybe<List<MatchDTO>> getMatches() {
        return homeSource.getMatches()
                .map(MatchesListResponse::getList)
                .filter(matchDTOs -> {
                    if (systemInfo.isStreamApiAvailable()) {
                        matchDTOs.removeIf(matchDTO -> matchDTO == null || matchDTO.getLeftTeam() == null || matchDTO.getRightTeam() == null);
                    }
                    return matchDTOs != null && !matchDTOs.isEmpty();
                })
                .subscribeOn(Schedulers.io());
    }

}
