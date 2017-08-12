package com.kamyshovcorp.simpletodo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getFormatedDateYyyyMmDd(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
