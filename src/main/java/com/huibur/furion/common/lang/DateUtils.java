package com.huibur.furion.common.lang;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM", 
		"yyyy年MM月dd日", "yyyy年MM月dd日 HH时mm分ss秒", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时", "yyyy年MM月",
		"yyyy"};
	

	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	

	public static String formatDate(long dateTime, String pattern) {
		return formatDate(new Date(dateTime), pattern);
	}
	

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (date != null){
			if (StringUtils.isBlank(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			formatDate = FastDateFormat.getInstance(pattern).format(date);
		}
		return formatDate;
	}
	

	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
    

	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	

	public static String getDate(String pattern) {
//		return DateFormatUtils.format(new Date(), pattern);
		return FastDateFormat.getInstance(pattern).format(new Date());
	}
	
//	/**
//	 * 得到当前日期前后多少天，月，年的日期字符串
//	 * @param pattern 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
//	 * @param amont 数量，前为负数，后为正数
//	 * @param type 类型，可参考Calendar的常量(如：Calendar.HOUR、Calendar.MINUTE、Calendar.SECOND)
//	 * @return
//	 */
	public static String getDate(String pattern, int amont, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(type, amont);
		return FastDateFormat.getInstance(pattern).format(calendar.getTime());
	}
	

	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}


	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}


	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}


	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}


	public static String getDay() {
		return formatDate(new Date(), "dd");
	}


	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	

	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}


	public static long pastDays(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(24*60*60*1000);
	}


	public static long pastHour(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*60*1000);
	}
	

	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*1000);
	}
    

	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	

	public static int getMonthHasDays(Date date){
//		String yyyyMM = new SimpleDateFormat("yyyyMM").format(date);
		String yyyyMM = FastDateFormat.getInstance("yyyyMM").format(date);
		String year = yyyyMM.substring(0, 4);
		String month = yyyyMM.substring(4, 6);
		String day31 = ",01,03,05,07,08,10,12,";
		String day30 = "04,06,09,11";
		int day = 0;
		if (day31.contains(month)) {
			day = 31;
		} else if (day30.contains(month)) {
			day = 30;
		} else {
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && (y % 100 != 0)) || y % 400 == 0) {
				day = 29;
			} else {
				day = 28;
			}
		}
		return day;
	}
	

	public static int getWeekOfYear(Date date){
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static Date getOfDayFirst(Date date) {
		if (date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	

	public static Date getOfDayLast(Date date) {
		if (date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getServerStartDate(){
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}
	

	public static String formatDateBetweenString(Date beginDate, Date endDate){
		String begin = DateUtils.formatDate(beginDate);
		String end = DateUtils.formatDate(endDate);
		if (StringUtils.isNoneBlank(begin, end)){
			return begin + " ~ " + end;
		}
		return null;
	}
	

	public static Date[] parseDateBetweenString(String dateString){
		Date beginDate = null; Date endDate = null;
		if (StringUtils.isNotBlank(dateString)){
			String[] ss = StringUtils.split(dateString, "~");
			if (ss != null && ss.length == 2){
				String begin = StringUtils.trim(ss[0]);
				String end = StringUtils.trim(ss[1]);
				if (StringUtils.isNoneBlank(begin, end)){
					beginDate = DateUtils.parseDate(begin);
					endDate = DateUtils.parseDate(end);
				}
			}
		}
		return new Date[]{beginDate, endDate};
	}

	public static Integer getTimestampsForDate(Date date, String dateFormat){
		return Integer.valueOf(new SimpleDateFormat(dateFormat).format(date));
	}
}
