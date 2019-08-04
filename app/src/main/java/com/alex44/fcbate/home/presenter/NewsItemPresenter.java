package com.alex44.fcbate.home.presenter;

import com.alex44.fcbate.home.model.dto.NewsDTO;
import com.alex44.fcbate.home.view.NewsItemView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsItemPresenter extends MvpPresenter<NewsItemView> {

    @Inject
    protected Router router;

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private NewsDTO newsDTO;

    public NewsItemPresenter(NewsDTO newsDTO) {
        this.newsDTO = newsDTO;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        update();
    }

    public void update() {
        getViewState().setPhoto(newsDTO.getPhotoUrl());
        getViewState().setTitle(newsDTO.getTitle());
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
                getViewState().setDateTime(dateStr, true);
                return;
            }

            calendar.add(Calendar.HOUR_OF_DAY, -1);

            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) ) {
                dateStr = "Сегодня в " + timeOutFormat.format(date);
                getViewState().setDateTime(dateStr, false);
                return;
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);

            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) ) {
                dateStr = "Вчера в " + timeOutFormat.format(date);
                getViewState().setDateTime(dateStr, false);
                return;
            }

            getViewState().setDateTime(dateStr, false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void imageClicked() {
        Timber.d("Go to detail news with id:" + newsDTO.getId() + ": "+newsDTO.getTitle());
//        router.navigateTo(TODO: create new screen);
    }

}
