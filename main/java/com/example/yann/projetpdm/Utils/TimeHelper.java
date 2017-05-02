package com.example.yann.projetpdm.Utils;

public abstract class TimeHelper {
    public static final int SECOND = 0;
    public static final int MINUTE = 1;

    public static final int MAX_HOUR = 23;
    public static final int MAX_MIN = 59;

    public static Integer[] getMinSec(long timeInMs){
        int secs = (int) (timeInMs / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return new Integer[]{secs, mins};
    }
    public static long toMs(long minute, long seconde){
        return (minute * 60 + seconde) * 1000;
    }
    public static long millisecondsToMinutes(long milliseconds) {return millisecondsToSeconds(milliseconds) / 60;}
    public static long millisecondsToSeconds(long milliseconds) {return milliseconds / 1000;}
    public static long minutesToHours(long minutes) {return minutes / 60;}
    public static long millisecondsToHours(long milliseconds) {return minutesToHours(millisecondsToMinutes(milliseconds));}
    public static long hourToMilliseconds(long hour){
        return hour * 1000 * 3600;
    }
    public static long hourToMinutes(long hour){
        return hour * 60;
    }
    public static long minToMilliseconds(long min){
        return min * 1000 * 60;
    }

    public static String formatAffichageHeure(long milliseconds) {
        long hour = getHours(milliseconds);
        long minutes = getMinutes(milliseconds);
        long seconds = getSeconds(milliseconds);
        String format = "";
        format += hour < 10 ? "0" + hour : hour;
        format += ":";
        format += minutes < 10 ? "0" + minutes : minutes;
        format += ":";
        format += seconds < 10 ? "0" + seconds : seconds;
        return format;
    }
    public static long getHours(long milliseconds){
        return millisecondsToHours(milliseconds);
    }
    /**
     *
     * @param milliseconds
     * @return nombre de minutes (1h12 --> 12)
     */
    public static long getMinutes(long milliseconds){
        long hour = getHours(milliseconds);
        return millisecondsToMinutes(milliseconds - TimeHelper.hourToMilliseconds(hour));
    }
    /**
     *
     * @param milliseconds
     * @return nombre de minutes (1m12 --> 12)
     */
    public static long getSeconds(long milliseconds){
        long hour = getHours(milliseconds);
        long minutes = getMinutes(milliseconds);
        return millisecondsToSeconds(milliseconds - hourToMilliseconds(hour) - minToMilliseconds(minutes));
    }
}
