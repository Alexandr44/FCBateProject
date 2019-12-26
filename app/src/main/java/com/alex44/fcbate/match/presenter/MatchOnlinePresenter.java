package com.alex44.fcbate.match.presenter;

import com.alex44.fcbate.match.model.dto.MatchOnlineMessageDTO;
import com.alex44.fcbate.match.model.repo.IMatchRepo;
import com.alex44.fcbate.match.view.MatchOnlineRVItemView;
import com.alex44.fcbate.match.view.MatchOnlineView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class MatchOnlinePresenter extends MvpPresenter<MatchOnlineView> {

    @Inject
    protected Router router;

    private Long id;

    @Inject
    protected IMatchRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    @Getter
    private List<MatchOnlineMessageDTO> messages = new ArrayList<>();

    public MatchOnlinePresenter(Long id, Scheduler mainThreadScheduler) {
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
            Timber.e("Match id not found");
            getViewState().showMessage("Match id not found");
            return;
        }

        disposable = repo.getMessages(id)
                .observeOn(mainThreadScheduler)
                .subscribe(messagesList -> {
                    messages = messagesList;
                    getViewState().updateData();
                }, throwable -> getViewState().showMessage("Match online data load failed"));
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

    public void bind(MatchOnlineRVItemView holder) {
        holder.setText(messages.get(holder.getPos()).getComment());
        holder.setTime(messages.get(holder.getPos()).getGameTime());
    }

}
