package ru.kamyshovcorp.weekplanner.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static Date getWeekStartDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return calendar.getTime();
        } else if (dayOfWeek == 7) {
            calendar.add(Calendar.DATE, -dayOfWeek + 1);
            return calendar.getTime();
        } else {
            calendar.add(Calendar.DATE, -dayOfWeek + 1);
            return calendar.getTime();
        }
    }

    public static Date getWeekEndDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            calendar.add(Calendar.DATE, 6);
            return calendar.getTime();
        } else if (dayOfWeek == 7) {
            return calendar.getTime();
        } else {
            calendar.add(Calendar.DATE, 7 - dayOfWeek);
            return calendar.getTime();
        }
    }

}
