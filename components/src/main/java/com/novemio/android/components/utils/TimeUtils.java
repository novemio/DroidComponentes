package com.novemio.android.components.utils;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * Created by xix on 8/16/17.
 */

public class TimeUtils {

    private static final String LOG_TAG = TimeUtils.class.getSimpleName();

    public static final String DATE_FORMAT_LOCAL = "dd.MM.yyyy";
    public static final String DATE_FORMAT_CLOUD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_ISO_8061 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT_SERVER_ACCEPTABLE = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
    public static final String DATE_FORMAT_TRACKING_DISPLAY = "EEE dd. MMM, k:mm a";
    private static final String DATE_REGEX = "([0-3][0-9]).([0-1][0-9]).([0-9]{4})";

    private static final String NOTIF_DATA_FORMAT = "EEE, MMM d, h:mm a";
    private static final String NOTIF_DATA_FORMAT_TIME = "h:mm a";

    private static final long DAY_MILLISECONDS = 24 * 3600 * 1000;
    private static final long MIN_MILLISECONDS = 60 * 1000;
    private static final long HOUR_MILLISECONDS = MIN_MILLISECONDS * 60;

    public static SimpleDateFormat getServerAcceptableDateTimeFormat() {
        return new SimpleDateFormat(DATE_FORMAT_SERVER_ACCEPTABLE, Locale.getDefault());
    }

    public static long getCurrentTimeMilis() {

        return System.currentTimeMillis();
    }

    public static Date getDate(long utcTime) {
        return new Date(utcTime * 1000);
    }

    public static Date getCurrentDate() {
        return new Date();
    }


    public static long getCurrentDateLong() {
        return new Date().getTime();
    }

    public static String getCurrentDateString() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_LOCAL, Locale.US);
        dateFormat.setLenient(false);
        return dateFormat.format(date);
    }

    public static String getCurrentDateString(String dataFormat) {
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
        dateFormat.setLenient(false);
        return dateFormat.format(date);
    }


    public static String getTodayGMT(String dataFormat) {
        Date date = todayStart();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        dateFormat.setLenient(false);
        return dateFormat.format(date);
    }

    public static String getToday(String dataFormat) {
        Date date = todayStart();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat, Locale.getDefault());
        dateFormat.setLenient(false);
        return dateFormat.format(date);
    }

    public static String formatDateToString(long milis, String dateFormat) {
        Date date = new Date(milis);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        format.setLenient(false);
        return format.format(date);
    }

    public static String formatDateToString(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        format.setLenient(false);
        return format.format(date);
    }

    public static boolean isDateValid(String format, String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isDateValid(String date) {
        try {
            if (!date.matches(DATE_REGEX)) {
                return false;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_LOCAL, Locale.US);
            dateFormat.setLenient(false);
            Date parse = dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public static String covertLocalDateToCloudFormat(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_LOCAL, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parse = simpleDateFormat.parse(date);

            String format = new SimpleDateFormat(DATE_FORMAT_CLOUD, Locale.US).format(parse);
            Log.d(LOG_TAG, "covertLocalDateToCloudFormat: " + format);
            return format;
        } catch (Exception e) {
            Log.e(LOG_TAG, "getFormattedDateString: ", e);
        }

        return date;
    }

    public static String covertUTCLocalFormat(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CLOUD, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parse = simpleDateFormat.parse(date);

            String format = new SimpleDateFormat(DATE_FORMAT_LOCAL, Locale.US).format(parse);
            Log.d(LOG_TAG, "covertLocalDateToCloudFormat: " + format);
            return format;
        } catch (Exception e) {
            Log.e(LOG_TAG, "getFormattedDateString: ", e);
        }

        return date;
    }

    public static Long covertIso8601toLong(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_ISO_8061);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parse = simpleDateFormat.parse(date);

            return parse.getTime();
        } catch (Exception e) {
            Log.e(LOG_TAG, "getFormattedDateString: ", e);
        }

        return 0l;
    }


    public static Date covertUTC(String dateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CLOUD, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = simpleDateFormat.parse(dateString);

            return date;
        } finally {
            return new Date();
        }
    }


    public static String convertToLocalFormat(Date time) {
        return formatDateToString(time, DATE_FORMAT_LOCAL);
    }

    public static String formatNotificationDateString(long notifTime) {

        Date now = new Date();
        return formatNotificationDateString(notifTime, now.getTime());
    }


    public static String formatNotificationDateString(long notifTime, long currentTime) {

        long offset = currentTime - notifTime;
        Date notifDate = new Date(notifTime);
        Date todayStart = todayStart();
        Date yesterdayStart = yesterdayStart();

//        Log.d(LOG_TAG, "formatNotificationDateString: " + offset / MIN_MILLISECONDS);
//        Log.d(LOG_TAG, "formatNotificationDateString: " + "\n notifDate: "+notifDate +"\n currentDate: "+currentDate +"\n yesterday: "
//                + ""+today00_00 +"\n yesterdayStart: "+ yesterday00_00);
//
//        Log.d(LOG_TAG, "formatNotificationDateString: \n before Today "+beforeToday +"\n after yesterday start "+afterYesterdayStart);

        if (offset < MIN_MILLISECONDS) {
            return "Just now";
        } else if (offset >= MIN_MILLISECONDS && offset <= 30 * MIN_MILLISECONDS) {
            return offset / MIN_MILLISECONDS + " min ago";
        } else if (offset > 30 * MIN_MILLISECONDS && notifDate.after(todayStart)) {
            return "Today, " + formatDateToString(notifTime, NOTIF_DATA_FORMAT_TIME);
        } else if (notifDate.before(todayStart) && notifDate.after(yesterdayStart)) {
            return "Yesterday, " + formatDateToString(notifTime, NOTIF_DATA_FORMAT_TIME);
        } else {
            return formatDateToString(notifTime, NOTIF_DATA_FORMAT);
        }
    }

    public static String formatTrackingDateString(long notifTime) {

        long offset = new Date().getTime() - notifTime;
        Date notifDate = new Date(notifTime);
        Date todayStart = todayStart();
        Date yesterdayStart = yesterdayStart();

        if (offset < MIN_MILLISECONDS) {
            return "Just now";
        } else if (offset > MIN_MILLISECONDS && notifDate.after(todayStart)) {
            StringBuilder stringBuilder = new StringBuilder();
            long hour = offset / HOUR_MILLISECONDS;
            long min;
            if (hour > 0) {
                min = (offset % HOUR_MILLISECONDS) / MIN_MILLISECONDS;
            } else {
                min = offset / MIN_MILLISECONDS;
            }
            if (hour > 0) {
                stringBuilder.append(hour).append(hour > 1 ? "hr" : "hrs").append(" ");
            }
            stringBuilder.append(min).append(min > 1 ? "min" : "mins").append(" ago");

            return stringBuilder.toString();

        } else if (notifDate.before(todayStart) && notifDate.after(yesterdayStart)) {
            return "Yesterday, " + formatDateToString(notifTime, NOTIF_DATA_FORMAT_TIME);
        } else {
            return formatDateToString(notifTime, NOTIF_DATA_FORMAT);
        }
    }

    private static Date todayStart() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal.getTime();
    }


    private static Date yesterdayStart() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal.getTime();
    }

    /**
     * Set initial start time for baby track logs
     *
     * @return String in required format
     */
    public static String getCurrentTimeFormattedIso860() {
        Date currentTime = Calendar.getInstance().getTime();
        return getServerAcceptableDateTimeFormat().format(currentTime);
    }


    public static String parseStringTime(String totalTime) {

        String[] split = totalTime.split(":");

        String time = "";
        if (!split[0].equals("0")) {
            String s = split[0];
            int hour = Integer.parseInt(s);
            if (hour > 1) {
                time = time + hour + "hrs ";
            } else {
                time = time + hour + "hr ";
            }
        }

        if (!split[1].equals("00")) {
            String s = split[1];
            int min = Integer.parseInt(s);
            time = time + min + "min ";
        }
        if (!split[2].equals("00")) {
            String s = split[2];
            int sec = Integer.parseInt(s);
            time = time + sec + "sec ";

        }

        return time;
    }

    public static int getDurationInSeconds(String durationFormatted, String pattern) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = timeFormat.parse(durationFormatted);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime());
            return (int) seconds;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
