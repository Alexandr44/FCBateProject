package com.alex44.fcbate.common.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat timeOutFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static final SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    public static String getFormattedDateStr(String dateStr) {
        try {
            final Date date = dateTimeFormat.parse(dateStr);
            final Calendar now = Calendar.getInstance();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.HOUR_OF_DAY, 1);
            if (calendar.after(now)) {  //за последний час
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                final long minutes = (now.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / 60;
                dateStr = String.valueOf(minutes);
                final String lastDigit = dateStr.substring(dateStr.length() - 1);
                if (lastDigit.equals("1")) {
                    dateStr += " минуту назад";
                } else if (lastDigit.equals("2") || lastDigit.equals("3") || lastDigit.equals("4")) {
                    dateStr += " минуты назад";
                } else {
                    dateStr += " минут назад";
                }
                return dateStr;
            }

            calendar.add(Calendar.HOUR_OF_DAY, -1);

            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                dateStr = "Сегодня в " + timeOutFormat.format(date);
                return dateStr;
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);

            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) &&
                    calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                    calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                dateStr = "Вчера в " + timeOutFormat.format(date);
                return dateStr;
            }

            calendar.add(Calendar.DAY_OF_MONTH, -1);

            dateStr = dateOutFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

}
