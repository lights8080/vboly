package com.lihp.common.util;

/**
 * String 类型帮助类
 */
public class StringHelper {

	/**
	 * 拼装字符串
	 * 
	 * @param args
	 *            需要拼装的对象
	 * @return 拼装后的字符串结果
	 */
	public static String combinedString(Object... args) {
		StringBuilder builder = new StringBuilder(64);
		for (Object s : args) {
			builder.append(s);
		}
		return builder.toString();
	}
}
