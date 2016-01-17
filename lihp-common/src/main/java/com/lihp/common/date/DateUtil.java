package com.lihp.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author lihp
 * @date 2015-02-28
 */
public class DateUtil {

	// 返回模式
	public static final int YEAR_RETURN = 0;

	public static final int MONTH_RETURN = 1;

	public static final int DAY_RETURN = 2;

	public static final int HOUR_RETURN = 3;

	public static final int MINUTE_RETURN = 4;

	public static final int SECOND_RETURN = 5;

	// 时间格式
	public static final String YYYY = "yyyy";

	public static final String YYYYMM = "yyyy-MM";

	public static final String YYYYMMDD = "yyyy-MM-dd";

	public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static void main(String[] args) {
		int i = 1437581030;
		String millisToStr = DateUtil.millisToStr(i);
		System.out.println(millisToStr);
	}

	/**
	 * 获得指定日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月（1-12）
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param min
	 *            分
	 * @param sen
	 *            秒
	 * @return Date
	 */
	public static Date getDate(int year, int month, int day, int hour, int min,
			int sen) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hour, min, sen);
		return c.getTime();
	}

	/**
	 * 获得格式化时间字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return String
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (date != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 获得格式化时间
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return String
	 * @throws ParseException
	 */
	public static Date stringToDate(String strDate, String pattern)
			throws ParseException {
		if ("".equals(strDate.trim()))
			return null;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date d = df.parse(strDate);
		return d;
	}

	/**
	 * 获得毫秒
	 * 
	 * @param date
	 *            日期
	 * @return long
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 获取指定日期后最近的工作日(只排除周六、日)
	 * 
	 * @param someDate
	 *            指定日期
	 * @return 最近的工作日
	 */
	public static Date getLatestWorkDay(Date someDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(someDate);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, 3);
		} else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, 2);
		} else {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		return calendar.getTime();
	}

	/**
	 * 获得日期当天起始时间点
	 * 
	 * @param date
	 *            日期
	 * @return Date
	 */
	public static Date getDayBegin(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得日期当天截止时间点
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	/**
	 * 获得month的起始时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得month的截止时间点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	/**
	 * 增加天
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            增加天数
	 * @return Date
	 */
	public static Date addDay(Date date, int day) {
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(date);
		theCa.add(Calendar.DATE, day);
		return theCa.getTime();
	}

	/**
	 * 增加月
	 * 
	 * @param date
	 *            日期
	 * @param month
	 *            增加月数
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 增加年
	 * 
	 * @param date
	 *            日期
	 * @param year
	 *            增加年数
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 判断第一个时间是否在第二个时间之后
	 * 
	 * @param base
	 *            第一个时间参数
	 * @param compare
	 *            第二个时间参数
	 * @return true如果第一个时间晚于第二个时间，否则返回false
	 */
	public static boolean isAfter(Date base, Date compare) {
		if (getMillis(base) - getMillis(compare) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 验证给定日期是否是当天时间
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		Date d = new Date();
		return getMillis(getDayBegin(d)) <= date.getTime()
				&& getMillis(getDayEnd(d)) >= date.getTime();

	}

	/**
	 * 获得时间间隔
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param formatPattern
	 *            开始时间和结束时间格式
	 * @param returnPattern
	 *            返回模式
	 * @return long
	 * @throws ParseException
	 */
	public static long getBetween(String beginTime, String endTime,
			String formatPattern, int returnPattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);

		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		switch (returnPattern) {
		case YEAR_RETURN:
			return getByField(beginCalendar, endCalendar, Calendar.YEAR);
		case MONTH_RETURN:
			return getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12
					+ getByField(beginCalendar, endCalendar, Calendar.MONTH);
		case DAY_RETURN:
			return getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
		case HOUR_RETURN:
			return getTime(beginDate, endDate) / (60 * 60 * 1000);
		case MINUTE_RETURN:
			return getTime(beginDate, endDate) / (60 * 1000);
		case SECOND_RETURN:
			return getTime(beginDate, endDate) / 1000;
		default:
			return 0;
		}
	}

	/**
	 * 获得当天是周几
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDay(Date date) {
		if (date == null) {
			return "1";
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "1";
		}
		if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "2";
		}
		if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "3";
		}
		if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "4";
		}
		if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "5";
		}
		if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "6";
		}
		if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "7";
		}
		return "1";
	}

	/**
	 * 毫秒差转成日期格式
	 * 
	 * @param t
	 * @return
	 */
	public static String millisToStr(int t) {
		int d = t / 86400;
		t = t % 86400;
		int h = t / 3600;
		t = t % 3600;
		int m = t / 60;
		int s = t % 60;

		return d + "天" + h + "小时" + m + "分" + s + "秒";
	}

	private static long getByField(Calendar beginCalendar,
			Calendar endCalendar, int calendarField) {
		return endCalendar.get(calendarField)
				- beginCalendar.get(calendarField);
	}

	private static long getTime(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}
}