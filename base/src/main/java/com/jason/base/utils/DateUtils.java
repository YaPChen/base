package com.jason.base.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhangcuicui on 2016/5/5.
 */
public class DateUtils {
    private static DateUtils mInstance = new DateUtils();
    private Context mContext;
    public static DateFormat TOPIC_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat TOPIC_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static DateFormat TOPIC_CREATE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static DateFormat TOPIC_CREATE_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    public static DateFormat TOPIC_CREATE_DATES_FORMAT = new SimpleDateFormat("yyyy.MM.dd");
    public static DateFormat TOPIC_CREATE_DATES_TIME_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    public static DateFormat TOPIC_SUBJECT_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public static DateFormat DATE_FULL_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static DateFormat TOPIC_SUBJECT_DATE_TIME_S_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
    private String day6;
    private String day7;
    private String[] mMonthOfYear;

    private DateUtils() {
    }

    public static DateUtils getInstance() {
        return mInstance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public static String toRelativeTimeSpanString(long created) {
        long now = System.currentTimeMillis();
        long difference = now - created;
        Object text = difference >= 0L && difference <= 60000L?"刚刚":android.text.format.DateUtils.getRelativeTimeSpanString(created, now, 60000L, 262144);
        return ((CharSequence)text).toString();
    }
}
