package com.kunyao.assistant.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Date 工具类
 * 
 * @author GeNing
 * @since  2016.07.29
 * 
 */
public class DateUtils {

	public static ThreadLocal<SimpleDateFormat> YMD_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy/MM/dd");
		};
	};
	
	public final static SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public final static SimpleDateFormat YMDHM_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public final static SimpleDateFormat YMD_FORMAT  = new SimpleDateFormat("yyyy/MM/dd");
	public final static SimpleDateFormat CN_MD_FORMAT = new SimpleDateFormat("MM月dd日");
	public final static SimpleDateFormat HM_FORMAT = new SimpleDateFormat("HH:mm");
	
	public static final int DAY = 1000 * 60 * 60 * 24;
	
	/**
	 * String转Date 格式：yyyy/MM/dd HH:mm:ss
	 * @param time
	 * @return {@link Date:yyyy/MM/dd HH:mm:ss}
	 */
	public static Date parseFullDate(String time) {
		Date date = null;
		try {
			date = FULL_FORMAT.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * String转Date 格式：yyyy/MM/dd HH:mm
	 * @param time
	 * @return {@link Date:yyyy/MM/dd HH:mm}
	 */
	public static Date parseYMDHMDate(String time) {
		Date date = null;
		try {
			date = YMDHM_FORMAT.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * String转Date 格式：yyyy/MM/dd
	 * @param time
	 * @return {@link Date:yyyy/MM/dd}
	 */
	public static Date parseYMDDate(String time) {
		Date date = null;
		try {
			date = YMD_FORMAT_THREAD_LOCAL.get().parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Date转String 格式：yyyy/MM/dd HH:mm:ss
	 * @param time
	 * @return {@link String:yyyy/MM/dd HH:mm:ss}
	 */
	public static String parseFullTime(Date date) {
		return FULL_FORMAT.format(date);
	}
	
	/**
	 * Date转String 格式：yyyy/MM/dd HH:mm
	 * @param time
	 * @return {@link String:yyyy/MM/dd HH:mm}
	 */
	public static String parseYMDHMTime(Date date) {
		return YMDHM_FORMAT.format(date);
	}
	
	/**
	 * Date转String 格式：yyyy/MM/dd
	 * @param time
	 * @return {@link String:yyyy/MM/dd}
	 */
	public static String parseYMDTime(Date date) {
		return YMD_FORMAT.format(date);
	}
	
	/**
	 * Date转String 格式：yyyy/MM/dd
	 * @param time
	 * @return {@link String:yyyy/MM/dd}
	 */
	public static String parseCNMDTime(Date date) {
		return CN_MD_FORMAT.format(date);
	}
	
	/**
	 * Date转String 格式：hhmm
	 * @param time
	 * @return {@link String:hh mm}
	 */
	public static String parseHHMMTime(Date date) {
		return HM_FORMAT.format(date);
	}
	
	/**
	 * 获取当前周几
	 * @param date
	 * @return {@link int}
	 */
	public static int getWeekOfDate(Date date) {      
	    Calendar calendar = Calendar.getInstance();
	    if(date != null) {        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w <= 0){        
	        w = 7;      
	    }      
	    return w;    
	}
	
	/**
	 * 获取当前周几
	 * @param week
	 * @return {@link String}
	 */
	public static String getWeekDay(int week) {
		String weekDay = null;
		switch (week) {
		case 1:
			weekDay = "周一";
			break;
		case 2:
			weekDay = "周二";
			break;
		case 3:
			weekDay = "周三";
			break;
		case 4:
			weekDay = "周四";
			break;
		case 5:
			weekDay = "周五";
			break;
		case 6:
			weekDay = "周六";
			break;
		case 7:
			weekDay = "周日";
			break;
		default:
			weekDay = "周日";
			break;
		}
		return weekDay;
	}
	
	/**
	 * 获取Date是几号
	 * @param date
	 * @return {@link int}
	 */
	public static int getMonthOfDate(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    if(date != null) {        
	         calendar.setTime(date);      
	    }        
	    return calendar.get(Calendar.DAY_OF_MONTH);      
	}
	
	/**
	 * 获取当月第一天
	 * @return
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance(); // 获取当前日期 
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);  // 设置为1号,当前日期既为本月第一天 
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		
		Date firstDate = calendar.getTime();
		String firstTime = parseFullTime(firstDate);
		return firstTime;
	}
	
	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();    
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDate = calendar.getTime();
		String lastTime = parseYMDTime(lastDate);
		return lastTime;
	}
	
	/**
	 * 获取下个月的最后一天
	 * @return
	 */
	public static String getNextMonthLastDay() {
		Date date=new Date();  
		Calendar calendar = Calendar.getInstance();    
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	    calendar.setTime(date);  
	    calendar.add(Calendar.MONTH, 2);  
	    calendar.set(Calendar.DATE, 0); 
		Date lastDate = calendar.getTime();
		String lastTime = parseYMDTime(lastDate);
		return lastTime;
	}
	
	/**
	 * 获取下个月第一天
	 * @return
	 */
	public static String getNextMonthFirstDay() {
		Calendar calendar = Calendar.getInstance(); // 获取当前日期 
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);  // 设置为1号,当前日期既为本月第一天 
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		
		Date firstDate = calendar.getTime();
		String firstTime = parseFullTime(firstDate);
		return firstTime;
	}
	
	
	/**
	 * 判断两个日期是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}
	
	/**
     * 获取当年的第一天
     * @param year
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();  
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }
     
    /**
     * 获取当年的最后一天
     * @param year
     * @return
     */
    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();  
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }
    
    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }
     
    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
         
        return currYearLast;
    }
    
    /**
     * 获取当天开始时间
     * @return
     */
    public static Date getTodayStartDate() {
    	Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);  
        
		Date todayDate = todayStart.getTime();
		
		return todayDate;
    }
    
    /**
     * 获取开始日期到结束日期之间的所有日期集合
     * @return
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        lDate.add(beginDate);// 把开始时间加入集合  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后  
            if (endDate.after(cal.getTime())) {  
                lDate.add(cal.getTime());  
            } else {  
                break;  
            }  
        }  
        lDate.add(endDate);// 把结束时间加入集合  
        return lDate;  
    }  
    
    /**
     * 获得在两个日期内间隔几天
     */
    public static int getBetweenDay(String startDate, String endDate) throws ParseException {
    	Date start = YMD_FORMAT.parse(startDate);
    	Date end = YMD_FORMAT.parse(endDate);
    	int day = (int) ((end.getTime() - start.getTime()) / DAY);
    	return day + 1;
    }
    
    public static int getBetweenDay(Date start, Date end) {
    	int day = (int) ((end.getTime() - start.getTime()) / DAY);
    	return day + 1;
    }
    
    /**
     * 是否在开始时间和结束时间内
     */
    public static boolean isInsideDate(Date startDate, Date endDate) throws ParseException {
    	Date currentDate = new Date();
    	if (currentDate.getTime() >= startDate.getTime() && currentDate.getTime() <= endDate.getTime()) {
			return true;
		}
    	return false;
    }
    
    /**
     * 测试主方法
     * @param args
     */
	public static void main(String[] args) {
		try {
			System.out.println(getBetweenDay(FULL_FORMAT.parse("2016/11/26 00:00:00"), FULL_FORMAT.parse("2016/11/28 23:59:59")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
}
