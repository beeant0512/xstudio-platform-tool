package com.xstudio.tool.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类 继承自 {@link org.apache.commons.lang3.time.DateUtils} 可以在该类中扩展一些方法
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2017/6/7.
 */
public class DateUtil extends DateUtils {
    /**
     * 日期格式化字符串：yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * 无间隔日期格式， 20171110
     */
    public static final String FORMAT_DATE_NODASH = "yyyyMMdd";

    /**
     * 时间格式化字符串：yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式化字符串：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss.S";

    private static Logger logger = LogManager.getLogger(DateUtil.class);

    /**
     * 休眠
     *
     * @param seconds
     */
    public static void sleep(Integer seconds) {
        try {
            Thread.sleep((seconds * 1000));
        } catch (Exception e) {
            logger.error("休眠 {}s 错误{}", seconds, e);
        }
    }

    /**
     * 昨天
     *
     * @return
     */
    public static String yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(FORMAT_DATE).format(cal.getTime());
    }

    /**
     * 下个月
     *
     * @return
     */
    public static Date nextMonth() {
        Calendar calendar = DateUtil.toCalendar(new Date());
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 下个月
     *
     * @param date
     * @return
     */
    public static Date nextMonth(String date) {
        Date date1 = DateUtil.parseDate(date);
        Calendar calendar = DateUtil.toCalendar(date1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 下个月
     *
     * @param date
     * @return
     */
    public static Date nextMonth(Date date) {
        Calendar calendar = DateUtil.toCalendar(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 今天
     *
     * @return
     */
    public static String today() {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat(FORMAT_DATE).format(cal.getTime());
    }

    /**
     * 计算两个月份间的总天数
     *
     * @param beginMonth 开始月份
     * @param endMonth   结束月份
     * @return 两个月份间总天数
     */
    public static Integer totalDays(Integer beginMonth, Integer endMonth) {
        Integer begin = beginMonth;
        Calendar calendar = Calendar.getInstance();

        if (begin.equals(endMonth)) {
            calendar.set(Calendar.MONTH, beginMonth - 1);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        int days = 0;
        if (endMonth < begin) {
            while (begin <= 12) {
                //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
                calendar.set(Calendar.MONTH, begin - 1);
                days = days + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                begin = begin + 1;
            }
            begin = 1;
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            while (begin <= endMonth) {
                //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
                calendar.set(Calendar.MONTH, begin - 1);
                days = days + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                begin = begin + 1;
            }
        } else {
            while (begin <= endMonth) {
                //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
                calendar.set(Calendar.MONTH, begin - 1);
                days = days + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                begin = begin + 1;
            }
        }

        return days;
    }

    public static String nowTimeString() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 返回 2017_07_12 12_23_40格式的日期字符串
     *
     * @return String
     */
    public static String nowTimeStringForFileName() {
        return format(new Date(), "yyyy_MM_dd HH_mm_ss");
    }

    public static long nowTimestamp() {
        Date date = new Date();
        return date.getTime();
    }

    public static long newTimestamp(boolean millisecond ) {
        long timestamp = nowTimestamp();
        if (millisecond) {
            return timestamp;
        }

        return timestamp / 1000;
    }

    public static Integer nonMillisecondStamp() {
        long timestamp = nowTimestamp();
        return Math.toIntExact(timestamp / 1000);
    }

    public static String nowTimestampString() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date.getTime());
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date.getTime());
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(FORMAT_DATE).format(date.getTime());
    }

    public static String plusDays(String date, int days) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(FORMAT_DATE);
        LocalDate parseTime = LocalDate.parse(date, format);
        return parseTime.plusDays(days).format(format);
    }

    public static boolean isBefore(String date, String dateEnd) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(FORMAT_DATE);
        LocalDate dateBefore = LocalDate.parse(date, format);
        LocalDate dateAfter = LocalDate.parse(dateEnd, format);
        return dateBefore.isBefore(dateAfter);
    }

    /**
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(FORMAT_DATE);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            logger.error("日期字符串转换Date类型错误 {}", date);
        }

        return null;
    }

    /**
     * 返回Date类型的时间，
     *
     * @param date 2017-01-01 22:14:14.333
     * @return
     */
    public static Date parseTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        if (date.length() == 19) {
            date = date.concat(".000");
        }

        if (date.length() == 10) {
            date = date.concat(" 00:00:00.000");
        }

        DateFormat format = new SimpleDateFormat(FORMAT_DATE_TIME);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            format = new SimpleDateFormat(FORMAT_DATE);
            try {
                return format.parse(date);
            } catch (ParseException e1) {
                logger.error("日期字符串转换Date类型错误 {}", date);
            }
        }

        return null;
    }

    /**
     * 返回Date类型的时间，
     *
     * @param date 2017-01-01 22:14:14.333
     * @return
     */
    public static Date parseTime(String date, String formatStr) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            logger.error("日期字符串转换Date类型错误 {}", date);
        }

        return null;
    }

    public static String[] daysBetween(Date dateEnd, Date dateBegin) {
        Integer integer = diffDays(dateEnd, dateBegin);
        if (null == integer) {
            return new String[]{};
        }
        if (integer == 0) {
            return new String[]{format(dateEnd, FORMAT_DATE)};
        }

        String[] days = new String[integer + 1];
        String dateBeginString = format(dateBegin, FORMAT_DATE);
        days[0] = dateBeginString;
        Integer index = 1;
        while (index <= integer) {
            dateBeginString = plusDays(dateBeginString, 1);
            days[index] = dateBeginString;
            index++;
        }

        return days;
    }

    /**
     * 返回两个日期间的月份情况（带年）
     *
     * @param dateEnd
     * @param dateBegin
     * @return 举例 dateBegin 2018-11-12 dateEnd 2018-12-12 返回 [201811, 201812]
     */
    public static String[] monthBetween(Date dateEnd, Date dateBegin) {
        Calendar end = toCalendar(dateEnd);
        Calendar start = toCalendar(dateBegin);
        int startMonth = start.get(Calendar.MONTH);

        Integer integer = diffMonth(end, start);
        String firstMonth = "";
        if (integer == 0) {
            if (startMonth < 9) {
                firstMonth = start.get(Calendar.YEAR) + "0" + (startMonth + 1);
            } else {
                firstMonth = "" + start.get(Calendar.YEAR) + (startMonth + 1);
            }
            return new String[]{firstMonth};
        }
        String[] months = new String[integer + 1];
        months[0] = firstMonth;
        Integer index = 0;
        Integer yearOffset = 0;
        int month = startMonth;
        while (index <= integer) {
            month = month + 1;
            if (month < 10) {
                months[index] = (start.get(Calendar.YEAR) + yearOffset) + "0" + (month);
            } else {
                months[index] = "" + (start.get(Calendar.YEAR) + yearOffset) + (month);
            }
            if (month % 12 == 0) {
                yearOffset += 1;
                month = 0;
            }
            index++;
        }

        return months;
    }

    /**
     * 两日期间的天数差
     *
     * @param dateEnd   结束时间
     * @param dateBegin 开始时间
     * @return
     */
    public static Integer diffDays(Date dateEnd, Date dateBegin) {
        return diff(dateEnd, dateBegin, DateTimeUnits.DAYS);
    }

    /**
     * 两日期间的月份查
     *
     * @param end   结束时间
     * @param start 开始时间
     * @return
     */
    public static Integer diffMonth(Calendar end, Calendar start) {
        int endYear = end.get(Calendar.YEAR);
        int startYear = start.get(Calendar.YEAR);

        return end.get(Calendar.MONTH) - start.get(Calendar.MONTH) + 12 * (endYear - startYear);
    }

    public static Integer diffHours(Date dateEnd, Date dateBegin) {
        return diff(dateEnd, dateBegin, DateTimeUnits.HOURS);
    }

    /**
     * 获取时间差
     *
     * @param dateEnd   结束时间
     * @param dateBegin 开始时间
     * @param dateDiff  返回值的类型 {@link DateTimeUnits}
     * @return 差值
     */
    public static int diff(Date dateEnd, Date dateBegin, DateTimeUnits dateDiff) {
        long diffInMs = dateEnd.getTime() - dateBegin.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMs);
        int hours = (int) (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMs)));
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        switch (dateDiff) {
            case DAYS:
                return days;
            case SECONDS:
                return seconds;
            case MINUTES:
                return minutes;
            case HOURS:
                return hours;
            case MILLISECONDS:
            default:
                return (int) diffInMs;
        }
    }

    /**
     * 获得本月第一天
     *
     * @param date
     * @return
     */
    public static String firstDateOfMonth(Date date, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }

    /**
     * 获得本月最后一天
     *
     * @param date
     * @return
     */
    public static String lastDateOfMonth(Date date, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }

    /**
     * 某天开始时间
     *
     * @param date 例如： 2017-08-01
     * @return 2017-08-01 00:00:00
     * @throws ParseException
     */
    public static Date dayBegin(String date) throws ParseException {
        return new SimpleDateFormat(FORMAT_DATE_TIME).parse(date + " 00:00:00.0");
    }

    /**
     * 某天结束时间
     *
     * @param date 例如： 2017-08-01
     * @return 2017-08-01 23:59:59
     * @throws ParseException
     */
    public static Date dayEnd(String date) throws ParseException {
        return new SimpleDateFormat(FORMAT_DATE_TIME).parse(date + " 23:59:59.999");
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型 Calendar.YEAR Calendar.MONTH Calendar.DAY ...
     * @param amount   数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 增加或减少天数
     *
     * @param date 时间
     * @param num  增加或减少小时
     * @return Date
     */
    public static Date addDay(Date date, float num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = (int) ((num * 60) / 60);
        int min = (int) ((num * 60) % 60);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * 增加月份
     *
     * @param date  日期
     * @param mount 增加数量。可为负数
     * @return
     */
    public static String addMonth(String date, int mount) {
        Date parseDate = parseDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate);
        calendar.add(Calendar.MONTH, mount);
        SimpleDateFormat s = new SimpleDateFormat(FORMAT_DATE);
        return s.format(calendar.getTime());
    }

    /**
     * 增加月份
     *
     * @param date  日期
     * @param mount 增加数量。可为负数
     * @return
     */
    public static Date addMonth(Date date, int mount) {
        return addInteger(date, mount, Calendar.MONTH);
    }

    /**
     * 增加或减少天数
     *
     * @param date 时间
     * @param num  增加或减少小时
     * @return Date
     */
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(5, num);
        return startDT.getTime();
    }

    /**
     * 时间加减小时
     *
     * @param oldTime
     * @param hour
     * @return
     */
    public static String addTime(String oldTime, int hour) {
        // 时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 将当前位置的gps转为指定格式
            Date dt = sdf.parse(handleDateStr(oldTime));
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            // 当前gps时间加上8个小时
            rightNow.add(Calendar.HOUR_OF_DAY, hour);
            return sdf.format(rightNow.getTime());
        } catch (ParseException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 日期字符串处理 补全.000微妙部分
     *
     * @param dateStr
     * @return
     */
    private static String handleDateStr(String dateStr) {
        if (dateStr.contains(".")) {
            return dateStr.concat(".000");
        }

        return dateStr;
    }

    /**
     * 增加分钟数
     *
     * @param date   时间
     * @param minute 增加或减少的分钟
     * @return
     */
    public static Date addMinutes(Date date, int minute) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.MINUTE, minute);
        return startDT.getTime();
    }

    /**
     * 增加秒数
     *
     * @param date   时间
     * @param second 增加或减少的秒数
     * @return
     */
    public static Date addSeconds(Date date, int second) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.SECOND, second);
        return startDT.getTime();
    }

    /**
     * 冒号分割 小时字符串
     *
     * @return String 1999:10:23:14 1999年10月23号14时
     */
    public static String nowHourColonSperateString() {
        return format(new Date(), "yyyy:MM:dd:HH");
    }

    public static Date stringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间差（秒数）
     *
     * @param dateEnd   时间1
     * @param dateBegin 时间2
     * @return double 时间差
     */
    public static Integer diffSeconds(Date dateEnd, Date dateBegin) {
        return diff(dateEnd, dateBegin, DateTimeUnits.SECONDS);
    }

    /**
     * 一个小时以前
     *
     * @return Date
     */
    public static Date getOneHourAgo() {
        Date date = new Date();

        long onHourAgo = date.getTime() - (60 * 60 * 1000);
        return new Date(onHourAgo);
    }

    /**
     * 一个小时以前
     *
     * @return Date
     */
    public static Date getOneHourAgo(Date date) {
        long onHourAgo = date.getTime() - (60 * 60 * 1000);
        return new Date(onHourAgo);
    }

    /**
     * 一个小时以前（字符串格式）
     *
     * @return String
     */
    public static String getOneHourAgoString() {
        Date oneHourAgo = getOneHourAgo();
        return format(oneHourAgo);
    }

    /**
     * 指定日期一个小时以前（字符串格式）
     *
     * @param date 日期
     * @return String
     */
    public static String getOneHourAgoString(Date date) {
        Date oneHourAgo = getOneHourAgo(date);
        return format(oneHourAgo);
    }

    /**
     * 获取本周第一天的日期
     *
     * @return Date
     */
    public static Date getThisWeekFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取指定日期当周的第一天的日期
     *
     * @return Date
     */
    public static Date getWeekFirstDay(String dateString) {
        Date date = parseDate(dateString);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 获取指定日期当周的第一天的日期字符串
     *
     * @return String
     */
    public static String getWeekFirstDayString(String dateString) {
        Date date = parseDate(dateString);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return formatDate(c.getTime());
    }

    /**
     * 获取指定日期当周的周末的日期字符串
     *
     * @return String
     */
    public static String getWeekLastDayString(String dateStr) {
        Date date = parseDate(dateStr);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return formatDate(c.getTime());
    }

    /**
     * 获取指定日期当周的周末的日期
     *
     * @return String
     */
    public static Date getWeekLastDay(String dateStr) {
        Date date = parseDate(dateStr);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 获取两个时间内的小时数组
     * 例如： begin => 2018-10-02 10:04:12 end => 2018-10-02 14:00:35
     * 返回 [10, 11, 12, 13, 14]
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return
     */
    public static Integer[] hoursBetween(Date begin, Date end) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        int beginHour = cal.get(Calendar.HOUR_OF_DAY);
        cal.setTime(end);
        Integer endHour = cal.get(Calendar.HOUR_OF_DAY);
        Integer hours = endHour - beginHour;
        Integer[] hoursAry = new Integer[hours + 1];
        for (int i = 0; i <= hours; i++) {
            hoursAry[i] = beginHour + i;
        }
        return hoursAry;
    }

    /**
     * 获取当天是本年的第多少周
     *
     * @return 第几周
     */
    public static Integer weekOfYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 返回 20170712122340格式的日期字符串
     *
     * @return String
     */
    public static String nowTimeStringForFileNameWithOutUnderline() {
        return downloadFileNameFormat(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式 Calendar.YEAR Calendar.MONTH Calendar.DAY ...
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    public static String downloadFileNameFormat(Date date, String pattern) {
        return (new SimpleDateFormat(pattern)).format(date.getTime());
    }

    /**
     * 获取期间的年龄
     *
     * @param date
     * @param otherDate
     * @return String
     */
    public static String getAge(Date date, Date otherDate) {
        int dis = diff(date, otherDate, DateTimeUnits.SECONDS);
        int year = dis / 365;
        int month = dis % 365 / 30;
        int day = dis % 365 % 31;
        return (year > 0 ? year + "岁" : "") + (month > 0 ? month + "个月" : "") + (day + "天");
    }

    /**
     * 昨日时间
     *
     * @param leftDay
     * @return
     */
    public static String todayBeforeDay(int leftDay) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, leftDay);
        return new SimpleDateFormat(FORMAT_TIME).format(cal.getTime());
    }

    /**
     * 月份以前
     *
     * @return
     */
    public static String todayBeforeMonth(int leftMonth) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, leftMonth);
        return new SimpleDateFormat(FORMAT_TIME).format(cal.getTime());
    }

    /**
     * 判断某个时间是否是在当前时间的七天之内
     *
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isLatestWeek(Date addtime, Date now) {
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(now);
        //设置为7天前
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        //得到7天前的时间
        Date before7days = calendar.getTime();
        if (before7days.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 日期差值计算枚举
     */
    public enum DateTimeUnits {
        /**
         * Days
         */
        DAYS,
        /**
         * Hours
         */
        HOURS,
        /**
         * Minutes
         */
        MINUTES,
        /**
         * Seconds
         */
        SECONDS,
        /**
         * Milliseconds
         */
        MILLISECONDS,
    }
}
