package com.example.yann.projetpdm.classes;

import java.text.SimpleDateFormat;

/**
 * Created by Yann on 24/04/2017.
 */

public class DateHelper {
    public static long convertHourToMilliseconds(int hour){
        return hour * 1000 * 3600;
    }
    public static long convertMinToMilliseconds(int min){
        return min * 1000 * 60;
    }
    public static SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    }
}
