package com.lihp.common;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.lihp.common.date.TimestampUtil;

/**
 * 工具类
 * 
 * @author lihp
 * @date 2015-02-28
 */
public class CommonUtils {

	private static final Random random = new Random();


//	var d = parseInt(remaining_time/86400);
//	var t = remaining_time%86400;
//	var h = parseInt(t/3600);
//	t = t%3600;
//	var m = parseInt(t/60);
//	var s = t%60;
//	var str = '鍓╀綑鏃堕棿&nbsp;<em class="font-highlight">'+d+'</em>澶�<em class="font-highlight">'+h+'</em>鏃�<em class="font-highlight">'+m+'</em>鍒�<em class="font-highlight">'+s+'</em>绉�'
//	$('.goodsDetail-main .goodsDetail-info .goodsDetail-timeRemaining .timeRemaining-cont').html(str);
	
	public static void main(String[] args) {
		int t=10;
		//t=t-1;
		
		int d = t/86400;
			t = t%86400;
		int h = t/3600;
			t = t%3600;
		int m = t/60;
		int s = t%60;
		
		
		System.out.println(d+"天"+h+"小时"+m+"分"+s+"秒");
		
		Date date = new Date();
		long millis1 = TimestampUtil.DateToTimestamp(date);
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(date);
		theCa.add(Calendar.HOUR_OF_DAY, 2);
		long millis2 = TimestampUtil.DateToTimestamp(theCa.getTime());
		System.out.println(millis2-millis1);
	}

	/**
	 * 生成length位随机号码
	 * 
	 * @param length
	 *            数字长度
	 * @return 随机号码
	 */
	public static String getRandomNumber(int length) {
		if (length <= 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(random.nextInt(9) + 1);
		for (int i = 1; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * 生成length位随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
