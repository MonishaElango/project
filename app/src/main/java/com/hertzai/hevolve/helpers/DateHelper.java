package com.hertzai.hevolve.helpers;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateHelper {
    private static final String T_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String TZ_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String AMPM_TIME_FORMAT="hh:mm aa";
    private static final String DATE_FORMAT="yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT="yyyy-MM-dd_HH:mm:ss";
    private static final String DAY_FORMAT="EEEE";

    private static final String DATE_SLASH_FORMAT="dd/MM/yyyy";
    private static final String CALENDAR_DAY_FORMAT="yyyy-MM-dd";


    private static final String TRENDS_DATE_FORMAT = "hh:mm, EEEE, MM/dd";
    private static final String TRENDS_TIME_FORMAT = "hh:mm";
    private static final String TRENDS_DAY_FORMAT = "hh:mm, EEEE";
    private static final String TRENDS_FULL_DATE_FORMAT = "MM/dd/yyyy, hh:mm";

    private static final String TRENDS_FULL_DAY_FORMAT = "EEEE, MM/dd/yyyy, hh:mm";

    private static final String EVENT_DATE_FORMAT = "dd";
    private static final String EVENT_MONTH_FORMAT = "M";
    private static final String EVENT_YEAR_FORMAT = "yyyy";
    private static final String CAL_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzzz yyyy";

    private static final String PROGRESS_DATE_FORMAT = "MM/dd/yyyy hh:mm aa";
    private static final String PROGRESS_DAY_FORMAT = "EEEE";

    public static String getTimeString(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat(AMPM_TIME_FORMAT, Locale.getDefault());
        Date date=new Date();
        date.setTime(timeInMillis);
        return sdf.format(date);
    }

    public static String getFormattedDate(String smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(smsTimeInMilis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);


        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return " Today, " + DateFormat.format(TRENDS_FULL_DATE_FORMAT, smsTime) ;
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return " Yesterday " +DateFormat.format(TRENDS_FULL_DATE_FORMAT, smsTime)  ;
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) < 7  ){
            return DateFormat.format(TRENDS_FULL_DAY_FORMAT, smsTime) + "" ;
        }
        else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(TRENDS_FULL_DAY_FORMAT, smsTime).toString();
        } else {
            return DateFormat.format(TRENDS_FULL_DAY_FORMAT, smsTime).toString();
        }
    }

    public static String getDateFromTZtoDate(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);

        return DateFormat.format(EVENT_DATE_FORMAT, smsTime).toString();

    }

    public static String getMonthFromTZtoDate(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);
        return DateFormat.format(EVENT_MONTH_FORMAT, smsTime).toString();

    }

    public static Date getDate(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);
        return date;

    }

    public static Date getLastWeekDate(String tDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(T_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(tDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);
        return date;

    }

    public static String getYearFromTZtoDate(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);

        return DateFormat.format(EVENT_YEAR_FORMAT, smsTime).toString();

    }

    public static String getProgressDate(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);

        return DateFormat.format(PROGRESS_DATE_FORMAT, smsTime).toString();

    }


    public static String getWeekDay(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TZ_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);

        return DateFormat.format(PROGRESS_DAY_FORMAT, smsTime).toString();

    }



    public static long getFrequencyMillis(int frequencyPosition) {
        long timeInMillis = 0;
        switch (frequencyPosition) {
            case 0:
                timeInMillis = TimeUnit.HOURS.toMillis(1);
                break;
            case 1:
                timeInMillis = TimeUnit.HOURS.toMillis(2);
                break;
            case 2:
                timeInMillis = TimeUnit.HOURS.toMillis(4);
                break;
            case 3:
                timeInMillis = TimeUnit.HOURS.toMillis(6);
                break;
        }
        return timeInMillis;
    }

    public static String getTimeFromMills(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        SimpleDateFormat simpleFormat = new SimpleDateFormat(DATE_TIME_FORMAT,Locale.getDefault());
        return simpleFormat.format(calendar.getTime());
    }

    public static long getTodayTimeInMillis(int hour,int minute){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        return calendar.getTimeInMillis();

    }

    public static long getTimeFromMills(String s) {
        SimpleDateFormat simpleFormat=new SimpleDateFormat(AMPM_TIME_FORMAT,Locale.getDefault());
        try {
            Date date=simpleFormat.parse(s);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentDateInTFormat() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                T_DATE_FORMAT, Locale.getDefault());
          dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(cal.getTime());
    }

    public static String getMonthlyProgressDate(String tzDate) {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(CALENDAR_DAY_FORMAT);
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = sdf.parse(tzDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dateTime = date.getTime();
        smsTime.setTimeInMillis(dateTime);

        return DateFormat.format(T_DATE_FORMAT, smsTime).toString();

    }

    public static String getLastWeekDateInTzFormat() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.add(Calendar.DAY_OF_YEAR, -6);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                T_DATE_FORMAT, Locale.getDefault());
        //  dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(cal.getTime());
    }

    public static String getLastMonthLastDateInTzFormat() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                T_DATE_FORMAT, Locale.getDefault());
        //  dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(cal.getTime());
    }

    public static String getLastMonthFirstDateInTzFormat() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());

        Calendar aCalendar = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                T_DATE_FORMAT, Locale.getDefault());
        //  dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(cal.getTime());
    }

    public static Calendar getLastMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return calendar;
    }


    public static String firstDayOfLastWeek()
    {
        Calendar c = Calendar.getInstance();

        c = (Calendar) c.clone();
        // last week
        c.add(Calendar.WEEK_OF_YEAR, -1);
        // first day
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                T_DATE_FORMAT, Locale.getDefault());
        //  dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(c.getTime());
    }

    public static String lastDayOfLastWeek()
    {
        Calendar c = Calendar.getInstance();

        c = (Calendar) c.clone();
        // first day of this week
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        // last day of previous week
        c.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                T_DATE_FORMAT, Locale.getDefault());
        //  dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(c.getTime());
    }
}
