package com.healinghaven.bigmomma.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    public static final SimpleDateFormat BASIC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat MED_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");
    public static final SimpleDateFormat HISTORY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat BJM_FORMATTER_NO_SECONDS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm");
    public static final SimpleDateFormat GENERIC_LONG_DATE_FORMATTER_DAY_FIRST = new SimpleDateFormat("dd MMMMMM yyyy");
    public static final SimpleDateFormat HOUR_MINUTE_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("dd MMM");
    public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS");

    public static String getFullDateFormat(String dateInput) {
        try {
            Date date = new Date(Long.parseLong(dateInput.trim()));
            SimpleDateFormat sdf = FULL_DATE_FORMAT;
            sdf.setTimeZone(TimeZone.getTimeZone("Africa/Johannesburg"));
            return sdf.format(date);
        } catch (Exception e) {
            LOG.error("Error converting the epoch date [" + dateInput + "]");
            return dateInput;
        }
    }

    public static String getBasicDateFormat(String dateInput) {
        try {
            Date date = new Date(Long.parseLong(dateInput.trim()));
            SimpleDateFormat sdf = BASIC_DATE_FORMAT;
            sdf.setTimeZone(TimeZone.getTimeZone("Africa/Johannesburg"));
            return sdf.format(date);
        } catch (Exception e) {
            LOG.error("Error converting the epoch date [" + dateInput + "]", e);
            return dateInput;
        }
    }

    public static String getHistoryDateFormat(String dateInput) {
        try {
            Date date = new Date(Long.parseLong(dateInput.trim()));
            SimpleDateFormat sdf = HISTORY_DATE_FORMAT;
            sdf.setTimeZone(TimeZone.getTimeZone("Africa/Johannesburg"));
            return sdf.format(date);
        } catch (Exception e) {
            LOG.error("Error converting the epoch date [" + dateInput + "]", e);
            return dateInput;
        }
    }
}
