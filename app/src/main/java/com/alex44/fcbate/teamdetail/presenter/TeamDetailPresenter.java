package com.alex44.fcbate.teamdetail.presenter;

import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.model.dto.TeamDetailDTO;
import com.alex44.fcbate.teamdetail.model.repo.ITeamDetailRepo;
import com.alex44.fcbate.teamdetail.view.TeamDetailView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class TeamDetailPresenter extends MvpPresenter<TeamDetailView> {

    @Inject
    protected Router router;

    private TeamItemType type;
    private Long id;

    @Inject
    protected ITeamDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    @Getter
    private TeamDetailAnketaPresenter teamDetailAnketaPresenter = new TeamDetailAnketaPresenter();
    @Getter
    private TeamDetailBiographyPresenter teamDetailBiographyPresenter = new TeamDetailBiographyPresenter();

    private Map<String, String> ampMap = new HashMap<String, String>() {{
        put("amp1", "Вратарь");
        put("amp2", "Защитник");
        put("amp3", "Полузащитник");
        put("amp4", "Нападающий");
    }};

    public TeamDetailPresenter(TeamItemType type, Long id, Scheduler mainThreadScheduler) {
        this.type = type;
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
        if (type == null) {
            Timber.e("Type for team member not found");
            getViewState().showMessage("Type for team member not found");
            return;
        }

        Maybe<TeamDetailDTO> maybe = null;
        switch (type) {
            case PLAYERS:
                maybe = repo.getPlayer(id);
                break;
            case TRAINERS:
                maybe = repo.getTrainer(id);
                break;
        }

        if (maybe == null) return;

        disposable = maybe.observeOn(mainThreadScheduler)
                .subscribe(teamDetailDTO -> {
                    teamDetailAnketaPresenter.setData(teamDetailDTO);
                    teamDetailBiographyPresenter.setData(teamDetailDTO);
                    update(teamDetailDTO);
                    teamDetailAnketaPresenter.updateAnketaText();
                    teamDetailBiographyPresenter.updateBiographyText();
                }, throwable -> {
                    getViewState().showMessage("Team member detail load failed");
                    Timber.e(throwable);
                });
    }

    private void init() {
        getViewState().initPager();
    }

    private void update(TeamDetailDTO teamDetailDTO) {
        getViewState().setPhoto(teamDetailDTO.getPhotoUrl());
        if (type.equals(TeamItemType.PLAYERS)) {
            getViewState().setFIO(teamDetailDTO.getLastName()+"\n"+teamDetailDTO.getFirstName().replace(" ", "\n"));
            getViewState().setNum(String.valueOf(teamDetailDTO.getPlayerNumber()));
            getViewState().setSpec(ampMap.get(teamDetailDTO.getAmplua()));
            getViewState().setParams(teamDetailDTO.getAge(), teamDetailDTO.getContent());
            getViewState().setCountry(teamDetailDTO.getCitizenship());
        }
        else if (type.equals(TeamItemType.TRAINERS)) {
            getViewState().setFIO(teamDetailDTO.getTitle().replace(" ", "\n"));
            getViewState().setSpec(teamDetailDTO.getPost());
            getViewState().setAgeOnly(teamDetailDTO.getAge());
            getViewState().setCountryFromContent(teamDetailDTO.getContent());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void backClick() {
        router.exit();
    }

}
