package xyz.cheng7.blog.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeUtil {
    private static String dateSplit = "-";
    private static String timeSplit = ":";
    public static String longToString(Long timeMills) {
        return longToString(timeMills, "yyyy-MM-dd HH:mm:ss");
    }
    public static String longToString(Long timeMills, String pattern) {
        DateTime dateTime = new DateTime(timeMills.longValue());
        return dateTime.toString(pattern);
    }

    public static DateTime stringToDate(String year, String month) {
        return stringToDate(year, month, "1", "0", "0", "0");
    }

    public static DateTime stringToDate(String year, String month, String day, String hour, String minute, String second) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy" + dateSplit + "MM" + dateSplit + "dd HH" + timeSplit + "mm" + timeSplit + "ss");
        DateTime dateTime = timeFormatter.parseDateTime(getTimeString(year, month, day, hour, minute, second));
        return dateTime;
    }

    public static String getTimeString(String year, String month, String day, String hour, String minute, String second) {
        return year + dateSplit + month + dateSplit + day + " " + hour + timeSplit + minute + timeSplit + second;
    }


}
