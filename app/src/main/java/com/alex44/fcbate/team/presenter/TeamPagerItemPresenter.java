package com.alex44.fcbate.team.presenter;

import com.alex44.fcbate.common.navigation.Screens;
import com.alex44.fcbate.team.model.dto.PlayerDTO;
import com.alex44.fcbate.team.model.dto.TrainerDTO;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.team.model.repo.ITeamRepo;
import com.alex44.fcbate.team.view.TeamPagerItemView;
import com.alex44.fcbate.team.view.TeamRVItemView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class TeamPagerItemPresenter extends MvpPresenter<TeamPagerItemView> {
    private Scheduler mainThreadScheduler;
    private TeamItemType type;

    @Inject
    protected ITeamRepo teamRepo;

    @Inject
    protected Router router;

    private Disposable disposable;
    private Disposable clickDisposable;

    private List<PlayerDTO> playersData = new ArrayList<>();
    private List<TrainerDTO> trainersData = new ArrayList<>();

    @Getter
    private PublishSubject<TeamRVItemView> clickSubject = PublishSubject.create();

    private Map<String, String> ampMap = new HashMap<String, String>() {{
        put("amp1", "Вратари");
        put("amp2", "Защитники");
        put("amp3", "Полузащитники");
        put("amp4", "Нападающие");
    }};

    public TeamPagerItemPresenter(Scheduler mainThreadScheduler, TeamItemType type) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.type = type;
    }

    public void bind(TeamRVItemView view) {
        if (TeamItemType.PLAYERS.equals(type)) {
            final PlayerDTO playerDTO = playersData.get(view.getPos());
            final String firstName = playerDTO.getFirstName().contains(" ") ?
                    playerDTO.getFirstName().substring(0, playerDTO.getFirstName().indexOf(" ")) :
                    playerDTO.getFirstName();
            view.setPhoto(playerDTO.getPhotoUrl());
            view.setFirstName(firstName);
            view.setLastName(playerDTO.getLastName(), true);
            view.setInfo(String.valueOf(playerDTO.getPlayerNumber()), true);
        }
        else if (TeamItemType.TRAINERS.equals(type)) {
            final TrainerDTO trainerDTO = trainersData.get(view.getPos());
            final String firstName = trainerDTO.getTitle().contains(" ") ?
                    trainerDTO.getTitle().substring(trainerDTO.getTitle().indexOf(" ")) : "";
            final String lastName = trainerDTO.getTitle().contains(" ") ?
                    trainerDTO.getTitle().substring(0, trainerDTO.getTitle().indexOf(" ")) :
                    trainerDTO.getTitle();
            view.setPhoto(trainerDTO.getPhotoUrl());
            view.setFirstName(firstName);
            view.setLastName(lastName, false);
            view.setInfo(trainerDTO.getPost(), false);
        }
    }

    public void bindTitle(TeamRVItemView view) {
        if (TeamItemType.PLAYERS.equals(type)) {
            view.setInfo(playersData.get(view.getPos()).getAmplua(), false);
        }
        else if (TeamItemType.TRAINERS.equals(type)) {
            view.setInfo(trainersData.get(view.getPos()).getTitle(), false);
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
        processClicks();
    }

    private void processClicks() {
        clickDisposable = clickSubject.subscribe(newsRVItemView -> {
            if (TeamItemType.PLAYERS.equals(type)) {
                final PlayerDTO playerDTO = playersData.get(newsRVItemView.getPos());
                router.navigateTo(new Screens.TeamDetailScreen(type, playerDTO.getId()));
            }
            else if (TeamItemType.TRAINERS.equals(type)) {
                final TrainerDTO trainerDTO = trainersData.get(newsRVItemView.getPos());
                router.navigateTo(new Screens.TeamDetailScreen(type, trainerDTO.getId()));
            }
        });
    }

    private void loadData() {
        switch (type) {
            case PLAYERS:
                disposable = teamRepo.getPlayers()
                        .map(playersDTOS -> {
                            Collections.sort(playersDTOS, (o1, o2) -> o2.getAmplua().compareTo(o1.getAmplua()));
                            String curAmplua = "";
                            for (int i = 0; i < playersDTOS.size(); i++) {
                                if (!curAmplua.equals(playersDTOS.get(i).getAmplua())) {
                                    curAmplua = playersDTOS.get(i).getAmplua();
                                    final PlayerDTO title = new PlayerDTO(0L);
                                    title.setAmplua(ampMap.get(curAmplua));
                                    playersDTOS.add(i++, title);
                                }
                            }
                            return playersDTOS;
                        })
                        .observeOn(mainThreadScheduler)
                        .subscribe(players -> {
                            playersData = players;
                            getViewState().updateData();
                        }, throwable -> {
                            getViewState().showMessage(type.toString()+" load failed");
                            Timber.e(throwable);
                        });
                break;
            case TRAINERS:
                disposable = teamRepo.getTrainers().observeOn(mainThreadScheduler)
                        .subscribe(trainers -> {
                            trainersData = trainers;
                            getViewState().updateData();
                        }, throwable -> {
                            getViewState().showMessage(type.toString()+" load failed");
                            Timber.e(throwable);
                        });
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (clickDisposable != null && !clickDisposable.isDisposed()) {
            clickDisposable.dispose();
        }
    }

    public int getCount() {
        return TeamItemType.PLAYERS.equals(type) ? playersData.size() :
                TeamItemType.TRAINERS.equals(type) ? trainersData.size() : 0;
    }

    public boolean isTitle(int pos) {
        return type.equals(TeamItemType.PLAYERS) ? playersData.get(pos).getId() == 0 :
                trainersData.get(pos).getId() == 0;
    }

}
