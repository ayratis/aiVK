package com.iskhakovayrat.aivk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String getFormatedDate(long dateLong){
        SimpleDateFormat hoursFormat = new SimpleDateFormat("dd MMM HH:mm");
        Date date = new Date(dateLong * 1000);
        return hoursFormat.format(date);
    }
}
