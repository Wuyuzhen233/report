package com.example.report.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by aimu on 2018/12/7.
 */
public class DateUtil {

    public String getDate_yyyyMMdd() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return dateFormat.format(calendar.getTime());
    }
    /**
     * -----------------------------------------------------------------
     */
    private DateUtil() {
    }

    private static class DateUtilHelper {
        private static final DateUtil INSTANCE = new DateUtil();
    }

    public static DateUtil getInstance() {
        return DateUtilHelper.INSTANCE;
    }
}
