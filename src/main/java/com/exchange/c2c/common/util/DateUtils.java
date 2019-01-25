package com.exchange.c2c.common.util;

import java.time.*;
import java.util.Date;

public class DateUtils {
    /**
     * 获得日期的开始时间
     */
    public static Date getDateStart(Date date) {
        return parseDate(toLocalDate(date), LocalTime.MIN);
    }

    /**
     * 获得日期的结束时间
     */
    public static Date getDateEnd(Date date) {
        return parseDate(toLocalDate(date), LocalTime.MAX);
    }

    private static Date parseDate(LocalDate localDate, LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return toDate(localDateTime);
    }

    public static Date toDate(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return toDate(zonedDateTime);
    }

    public static Date toDate(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return toDate(zonedDateTime);
    }

    private static Date toDate(ZonedDateTime zonedDateTime) {
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
