package com.dream.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss:SSS";
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	
    /**
     * 得到当前的日期时间字符串
     * 
     * @return 日期时间字符串
     */
    public static String getDatetime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }
    
    /**
     * 
     * @param date Date
     * @param format 格式
     * @return
     */
    public static String getStringFromDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public static String getDatetime(Date time) {
        return getStringFromDate(time, FORMAT_DATETIME);
    }
    
    
    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @return 日期
     */
    public static Date getDateFromString(String s) {
        int len = s.length();
        String format;
        switch (len) {
        case 19:
            format = FORMAT_DATETIME;
            break;
        case 24:
            format = FORMAT_TIMESTAMP;
            break;
        default:
            format = FORMAT_DATE;
        }
        return getDateFromString(s, format);
    }
    
    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @param format 字符串格式.
     * @return 日期
     */
    public static Date getDateFromString(String s, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }
}
