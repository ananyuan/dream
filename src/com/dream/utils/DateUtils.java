package com.dream.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
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
}
