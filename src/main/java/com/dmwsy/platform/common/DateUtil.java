package com.dmwsy.platform.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
/**
 * 日期相关操作工具类
 * 
 * @author peiguanghao
 *
 */
public class DateUtil {

    public static final String  format = "yyyy-MM-dd HH:mm:ss";
    public static final  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static String formatDateToString(Date date, String pattern) {
        if (null == date) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    public static Date parseStringToDate(String src, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(src == null){
        	return null;
        }
        try {
            return sdf.parse(src);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parseStringToDate(String src ) {
    	return parseStringToDate(src,format);
    }
    /**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}
    
    public static int timeCompare(Date t1, Date t2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(t1);
			c2.setTime(t2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int result = c1.compareTo(c2); // 返回值：1:c1>c2 0:c1=c2 -1:c1<c2
		return result;
	}
    /**
     * day 天前的日期  负数为之后的日期
     * @param date
     * @param day
     * @return
     */
    public static Date getDayBefore(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		date = calendar.getTime();
		return date;
	}
    public static Date getHourBefore(Date date,int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -hour);
        date = calendar.getTime();
        return date;
    }
    public static String getDayBeforeFormat(Date date,int day) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH, -day);
    	date = calendar.getTime();
    	return formatDateToString(date,"yyyy-MM-dd HH:mm:ss");
    }
	/**
	 * 根据日期和天数参数，返回增加后的日期
	 * @param trigger_time 首次触发日期
	 * @param days 要增加的天数
	 * @return long 
	 */
	@SuppressWarnings("unused")
	private static long addDate(final Date trigger_time,final Integer days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(trigger_time);
		cal.add(Calendar.DATE, days);
		return cal.getTimeInMillis();
	}
	
	/** 
     * 增加日期中某类型的某数值。如增加日期 
     * @param date 日期 
     * @param dateType 类型 
     * @param amount 数值 
     * @return 计算后日期 
     */  
    public static Date addInteger(Date date, int dateType, int amount) {  
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
	 * 根据两个日期返回相隔的天数
	 * @param time1 当前日期
	 * @param time2 首次推送日期
	 * @return long 
	 */
	public static long getQuot(String time1, String time2){
		long quot = 0;
		try {
			Date date1 = sdf.parse(time1);
			Date date2 = sdf.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**
	 * 获取两个日期的天数差  millis_time1 - millis_time2
	 * @param millis_time1
	 * @param millis_time2
	 * @return
	 */
	public static int getDayIntevals(long millis_time1,long millis_time2){
	    Calendar c1 = Calendar.getInstance();
	    c1.setTimeInMillis(millis_time1);
	    c1.set(Calendar.HOUR_OF_DAY, 0);
	    c1.set(Calendar.MINUTE, 0);
	    c1.set(Calendar.SECOND,0);
	    
	    Calendar c2 = Calendar.getInstance();
	    c2.setTimeInMillis(millis_time2);
	    c2.set(Calendar.HOUR_OF_DAY, 0);
	    c2.set(Calendar.MINUTE, 0);
	    c2.set(Calendar.SECOND,0);
	    int diff = (int) ((c1.getTimeInMillis()  - c2.getTimeInMillis()) / TimeUnit.DAYS.toMillis(1));
	    return diff;
	}
	
}
