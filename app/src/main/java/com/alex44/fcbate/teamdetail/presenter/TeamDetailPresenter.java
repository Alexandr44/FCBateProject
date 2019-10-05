package com.alex44.fcbate.teamdetail.presenter;

import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.view.TeamDetailView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TeamDetailPresenter extends MvpPresenter<TeamDetailView> {

    @Inject
    protected Router router;

    private TeamItemType type;
    private Long id;

//    @Inject
//    protected INewsDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    public TeamDetailPresenter(TeamItemType type, Long id, Scheduler mainThreadScheduler) {
        this.type = type;
        this.id = id;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
        getViewState().showMessage(type+" - "+id);
    }

    private void init() {
        getViewState().initPager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void backClick() {
        router.exit();
    }

}
