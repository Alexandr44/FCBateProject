package com.alex44.fcbate.teamdetail.presenter;

import com.alex44.fcbate.teamdetail.model.dto.TeamDetailStatisticDTO;
import com.alex44.fcbate.teamdetail.model.repo.ITeamDetailRepo;
import com.alex44.fcbate.teamdetail.view.TeamDetailStatisticRVItemView;
import com.alex44.fcbate.teamdetail.view.TeamDetailStatisticView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import timber.log.Timber;

@InjectViewState
public class TeamDetailStatisticPresenter extends MvpPresenter<TeamDetailStatisticView> {

    private Long id;

    @Inject
    protected ITeamDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    @Getter
    private List<TeamDetailStatisticDTO> statsData = new ArrayList<>();

    public TeamDetailStatisticPresenter(Long id, Scheduler mainThreadScheduler) {
        this.id = id;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
        getInfo();
    }

    private void getInfo() {
        if (id == null) {
            Timber.e("Team member id not found");
            getViewState().showMessage("Team member id not found");
            return;
        }

        disposable = repo.getPlayerStats(id)
                .observeOn(mainThreadScheduler)
                .subscribe(list -> {
                    statsData.addAll(list);
                    getViewState().updateData();
                }, throwable -> {
                    getViewState().showMessage("Team member stats load failed");
                    Timber.e(throwable);
                });
    }

    private void init() {
        getViewState().initRV();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void bind(TeamDetailStatisticRVItemView holder) {
        TeamDetailStatisticDTO statisticDTO = statsData.get(holder.getPos());
        holder.setYear(statisticDTO.getYear());
        holder.setMatches(statisticDTO.getMatches());
        holder.setMinutes(statisticDTO.getMinutes());
        holder.setGoals(statisticDTO.getGoals());
        holder.setGoalPasses(statisticDTO.getGoalPasses());
    }
}
