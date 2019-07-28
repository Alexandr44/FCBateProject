package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.model.dto.MatchDTO;
import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.model.repo.HomeRepo;
import com.alex44.fcbate.home.view.HomeView;
import com.alex44.fcbate.home.view.MatchItemView;
import com.alex44.fcbate.home.view.NewsItemView;
import com.alex44.fcbate.utils.model.ISystemInfo;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import timber.log.Timber;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private HomeRepo homeRepo;

    private ISystemInfo systemInfo;

    private Scheduler mainThreadScheduler;

    private Disposable matchesDisposable;
    private Disposable newsDisposable;
    private Disposable tournamentsDisposable;

    @Getter
    private MatchItemPresenter matchItemPresenter;

    @Getter
    private NewsItemPresenter newsItemPresenter;

    public HomePresenter(Scheduler mainThreadScheduler, ISystemInfo systemInfo) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.systemInfo = systemInfo;
        homeRepo = new HomeRepo(systemInfo);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        matchItemPresenter = new MatchItemPresenter();
        newsItemPresenter = new NewsItemPresenter();
        initMatchPager();
        initNewsPager();
        initTournamentsTable();
    }

    void initMatchPager() {
        matchesDisposable = homeRepo.getMatches()
                .observeOn(mainThreadScheduler)
                .subscribe(matchDTOS -> {
                    matchItemPresenter.getMatches().clear();
                    matchItemPresenter.getMatches().addAll(matchDTOS);
                    getViewState().initMatchPager();
                }, throwable -> {
                    getViewState().showMessage("Matches load failed");
                    Timber.e(throwable);
                });
    }

    void initNewsPager() {
        newsDisposable = homeRepo.getNews()
                .observeOn(mainThreadScheduler)
                .subscribe(newsDTOS -> {
                    newsDTOS.subList(5, newsDTOS.size()).clear();
                    newsItemPresenter.getNews().clear();
                    newsItemPresenter.getNews().addAll(newsDTOS);
                    getViewState().initNewsPager();
                }, throwable -> {
                    getViewState().showMessage("News load failed");
                    Timber.e(throwable);
                });
    }

    private void initTournamentsTable() {
        getViewState().initTable();
        tournamentsDisposable = homeRepo.getTournamentsInfo()
                .observeOn(mainThreadScheduler)
                .subscribe(infoList -> {
                    if (infoList.size() >= 3) {
                        getViewState().fillTable(infoList);
                    }
                }, throwable -> {
                    getViewState().showMessage("Tournaments info load failed");
                    Timber.e(throwable);
                });
    }

    public void goToCalendarScreen() {
        Timber.d("ToDo: go to calendar");
    }

    public void goToNewsScreen() {
        Timber.d("ToDo: go to news");
    }

    public void goToTournamentScreen() {
        Timber.d("ToDo: go to tournaments");
    }

    private void goToNewsDetailScreen(Long id) {
        Timber.d("ToDo: go to news detail with id: "+id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (matchesDisposable != null && !matchesDisposable.isDisposed())
            matchesDisposable.dispose();
        if (newsDisposable != null && !newsDisposable.isDisposed())
            newsDisposable.dispose();
        if (tournamentsDisposable != null && !tournamentsDisposable.isDisposed())
            tournamentsDisposable.dispose();
    }

    private class MatchItemPresenter implements IMatchItemPresenter {

        private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        private final SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd MMMM, E", Locale.getDefault());
        private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        @Getter
        private List<MatchDTO> matches = new ArrayList<>();

        @Override
        public void update(MatchItemView view) {
            final MatchDTO matchDTO = matches.get(view.getPos());
            view.setLeftLogo(matchDTO.getLeftTeam().getLogoUrl());
            view.setLeftTeamTitle(matchDTO.getLeftTeam().getTitle());
            view.setRightLogo(matchDTO.getRightTeam().getLogoUrl());
            view.setRightTeamTitle(matchDTO.getRightTeam().getTitle());
            view.setChampTitle(matchDTO.getTournament().getTitle());
            view.setTournamentLogo(matchDTO.getTournament().getTitleShort());
            if (matchDTO.getGoalsLeft() == 0 && matchDTO.getGoalsRight() == 0) {
                view.setScore("V");
            }
            else {
                view.setScore(matchDTO.getGoalsLeft()+" - "+matchDTO.getGoalsRight());
            }
            try {
                Date date = dateTimeFormat.parse(matchDTO.getDateStr());
                view.setDate(dateOutFormat.format(date));
                view.setTime(timeOutFormat.format(date));
            } catch (ParseException e) {
                Timber.e(e);
            }
        }
    }

    private class NewsItemPresenter implements INewsItemPresenter {

        private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        @Getter
        private List<NewsDTO> news = new ArrayList<>();


        @Override
        public void update(NewsItemView view) {
            final NewsDTO newsDTO = news.get(view.getPos());
            view.setPhoto(newsDTO.getPhotoUrl());
            view.setTitle(newsDTO.getTitle());
            String dateStr = newsDTO.getCreated();

            try {
                final Date date = dateTimeFormat.parse(newsDTO.getCreated());
                final Calendar now = Calendar.getInstance();
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                calendar.add(Calendar.HOUR_OF_DAY, 1);
                if (calendar.after(now)) {  //за последний час
                    calendar.add(Calendar.HOUR_OF_DAY, -1);
                    final long minutes = (now.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / 60;
                    dateStr = String.valueOf(minutes);
                    final String lastDigit = dateStr.substring(dateStr.length()-1);
                    if (lastDigit.equals("1")) {
                        dateStr += " минуту назад";
                    }
                    else if (lastDigit.equals("2") || lastDigit.equals("3") || lastDigit.equals("4")) {
                        dateStr += " минуты назад";
                    }
                    else {
                        dateStr += " минут назад";
                    }
                    view.setDateTime(dateStr, true);
                    return;
                }

                calendar.add(Calendar.HOUR_OF_DAY, -1);

                if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) ) {
                    dateStr = "Сегодня в " + timeOutFormat.format(date);
                    view.setDateTime(dateStr, false);
                    return;
                }

                calendar.add(Calendar.DAY_OF_MONTH, 1);

                if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) ) {
                    dateStr = "Вчера в " + timeOutFormat.format(date);
                    view.setDateTime(dateStr, false);
                    return;
                }

                view.setDateTime(dateStr, false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void imageClicked(int pos) {
            goToNewsDetailScreen(newsItemPresenter.getNews().get(pos).getId());
            Timber.d(newsItemPresenter.getNews().get(pos).getTitle());
        }
    }

}
