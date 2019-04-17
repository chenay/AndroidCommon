package com.chenay.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static String getCurTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yy hh.mm.ss ");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static String getCurTime1() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    public static String getCurTime2() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 比较两次按下的时间间隔
     * 比较当前系统时间和time 的差
     *
     * @param time          传入时间
     * @param interval_time 间隔
     * @return 如果时间间隔大于interval_time 返回 当前时间 否则返回传入时间
     */
    public static long compareTime(long time, long interval_time) {
        long sysTime = System.currentTimeMillis();
        if (sysTime - time > interval_time) {
            return sysTime;
        }
        return time;
    }

    /**
     * 由数字格式转为分钟与秒混全的时间格式
     *
     * @param time
     * @return
     */
    public static String secondToMinute(int time) {
        StringBuffer timeBuffer = null;
        if (time <= 0) {
            return "00:00";
        }

        if (timeBuffer == null) {
            timeBuffer = new StringBuffer();
        } else {
            timeBuffer.delete(0, timeBuffer.length());
        }

        int minute = time / 60;
        int second = time % 60;

        timeBuffer.append(minute / 10);
        timeBuffer.append(minute % 10);
        timeBuffer.append(":");
        timeBuffer.append(second / 10);
        timeBuffer.append(second % 10);
        //String str=""+minute/10+minute%10+":"+second/10+second%10;
        return timeBuffer.toString();
    }


    /**
     * 将String 转换成时间戳
     * @param time
     * @return
     */
    public static long getTimestamp(String time) {
        //Date或者String转化为时间戳
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time1 = date.getTime();
        System.out.print("Format To times:"+ time1);
        return time1;
    }
    public static String getCurrentYear() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
        String sYear = sDateFormat.format(new Date());
        return sYear;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        String sMonth = sDateFormat.format(new Date());
        return sMonth;
    }


//    public static String timeToSS(String log_time) {
//        if (log_time != null) {
//            int endIndex = log_time.lastIndexOf(".");
//            if (endIndex <= 0) {
//                endIndex = log_time.length();
//            }
//            return log_time.substring(0, endIndex);
//        }
//        return "";
//    }
}
