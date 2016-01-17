package com.lihp.common.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java.sql.Timestamp java.util.Date转换工具类
 * 
 * @author lihp
 * @date 2015-02-28
 */
public class TimestampUtil {

	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		// 1425722326
		// System.out.println(DateToTimestamp(new Date()));
		System.out.println(TimestampToDate(1425722326));

	}

	/**
	 * 10位int型的时间戳转换为String(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param time
	 * @return
	 */
	public static String timestampToString(Integer time) {
		long temp = (long) time * 1000;
		Timestamp ts = new Timestamp(temp);
		return dateFormat.format(ts);
	}

	/**
	 * 10位int型的时间戳转Date
	 * 
	 * @param time
	 * @return
	 */
	public static Date TimestampToDate(Integer time) {
		long temp = (long) time * 1000;
		return new Timestamp(temp);
	}

	/**
	 * Date类型转换为10位时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static Integer DateToTimestamp(Date time) {
		Timestamp ts = new Timestamp(time.getTime());
		return (int) ((ts.getTime()) / 1000);
	}

	/**
	 * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static Integer StringToTimestamp(String time) {
		int times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
		if (times == 0)
			System.out.println("String转10位时间戳失败");
		return times;

	}
}
