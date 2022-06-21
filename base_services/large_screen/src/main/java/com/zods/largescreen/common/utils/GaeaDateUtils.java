package com.zods.largescreen.common.utils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public abstract class GaeaDateUtils {
    public GaeaDateUtils() {
    }

    public static String toString(Date date, String pattern) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate fromString(String dateString, String pattern) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));
    }

    public static Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String formatFromSecond(long seconds) {
        StringBuilder builder = new StringBuilder();
        long hourSeconds = 3600L;
        long daySeconds = hourSeconds * 24L;
        long days = seconds / daySeconds;
        if (days > 0L) {
            builder.append(days).append("${day)");
        }

        long hours = seconds % daySeconds / hourSeconds;
        if (hours > 0L) {
            builder.append(hours).append("${hour}");
        }

        long minus = seconds % daySeconds % hourSeconds / 60L;
        if (minus > 0L) {
            builder.append(minus).append("${minute}");
        }

        long resultSeconds = seconds % daySeconds % hourSeconds % 60L;
        if (resultSeconds > 0L) {
            builder.append(resultSeconds).append("${second}");
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap();
        map.put("username", "admin");
        map.put("age", 24);
        String s = GaeaUtils.replaceFormatString("${username} is ${age}", map);
        System.out.println(s);
        String s1 = formatFromSecond(40000L);
        System.out.println(s1);
    }
}
