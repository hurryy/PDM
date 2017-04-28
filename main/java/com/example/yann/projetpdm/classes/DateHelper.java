package com.example.yann.projetpdm.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yann on 24/04/2017.
 */

public class DateHelper {
    public static long convertMillisecondsToMinutes(long milliseconds) {return milliseconds / (1000 * 60);}
    public static long convertHourToMilliseconds(int hour){
        return hour * 1000 * 3600;
    }
    public static long convertMinToMilliseconds(int min){
        return min * 1000 * 60;
    }
    public static SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
    }
    public static Date convertMillisecondsToDate(long date){Date d = new Date(); d.setTime(date); return d;}
}
