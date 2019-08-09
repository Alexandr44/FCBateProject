package com.alex44.fcbate.newsdetail.presenter;

import com.alex44.fcbate.newsdetail.model.dto.NewsDetailDTO;
import com.alex44.fcbate.newsdetail.model.repo.INewsDetailRepo;
import com.alex44.fcbate.newsdetail.view.NewsDetailView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsDetailPresenter extends MvpPresenter<NewsDetailView> {

    @Inject
    protected Router router;

    private Long newsId;

    @Inject
    protected INewsDetailRepo repo;

    private Scheduler mainThreadScheduler;

    private Disposable disposable;

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public NewsDetailPresenter(Long newsId, Scheduler mainThreadScheduler) {
        this.newsId = newsId;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        init();
    }

    private void init() {
        if (newsId == null) {
            Timber.e("News id not found");
            getViewState().showMessage("News id not found");
            return;
        }
        disposable = repo.getNewsDetail(newsId)
                .observeOn(mainThreadScheduler)
                .subscribe(newsDetailDTO -> {
                    Timber.d(newsDetailDTO.toString());
                    update(newsDetailDTO);
                }, throwable -> {
                    getViewState().showMessage("News detail load failed");
                    Timber.e(throwable);
                });

    }

    private void update(NewsDetailDTO newsDetailDTO) {
        getViewState().setPhoto(newsDetailDTO.getPhotoUrl());

        getViewState().setText(newsDetailDTO.getContent());
        getViewState().setBrief(newsDetailDTO.getBrief());

        String dateStr = newsDetailDTO.getDateCreated();
        try {
            final Date date = dateTimeFormat.parse(newsDetailDTO.getDateCreated());
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
                getViewState().setDate(dateStr);
                return;
            }

            calendar.add(Calendar.HOUR_OF_DAY, -1);

            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) ) {
                dateStr = "Сегодня в " + timeOutFormat.format(date);
                getViewState().setDate(dateStr);
                return;
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);

            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) ) {
                dateStr = "Вчера в " + timeOutFormat.format(date);
                getViewState().setDate(dateStr);
                return;
            }

            getViewState().setDate(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
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
