package com.example.yann.projetpdm.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yann on 24/04/2017.
 */

public class DateHelper {
    public static SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
    }
    public static Date convertMillisecondsToDate(long date){Date d = new Date(); d.setTime(date); return d;}
}
