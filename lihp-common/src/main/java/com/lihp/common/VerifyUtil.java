package com.lihp.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具包
 * 
 * @author lihp
 * 
 */
public class VerifyUtil {

	/**
	 * 以1开头的11位数字
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		String regex = "^(1)\\d{10}$";
		return checkByPattern(regex, phoneNumber);

	}

	/**
	 * 检查email格式是否正确 X@X. 后缀”第二个X字符数量必须大于等于2，
	 * 整体长度要求：长度50个半角字符，必须有@和.符号，并且@和.不能在一起，最后1位不能是.； 只能是字母、数字、“-”(中划线)、“_”
	 * (下划线)、“@”和“.”的组合；@前后必须有内容。
	 * 
	 * @param emailAddress
	 * @return
	 */
	public static boolean checkEmail(String emailAddress) {
		String regex = "^([a-zA-Z0-9._-]*)+([a-zA-Z0-9_-]{1,})+@([a-zA-Z0-9-_]{2,}+\\.)+([a-zA-Z0-9-_.]*)+([a-zA-Z0-9-_]{1,})$";
		return checkByPattern(regex, emailAddress);
	}

	/**
	 * 检查用户名 要求：4—20位字符，只能包含字母、数字、汉字和“_”（下划线）且不允许全部为数字。
	 * 
	 * @param accountName
	 * @return
	 */
	public static boolean checkAccountName(String accountName) {
		if (isNumber(accountName)) {
			return false;
		}
		String regex = "^([a-zA-Z\u4e00-\u9fa50-9_]{4,20})$";
		return checkByPattern(regex, accountName);
	}

	/**
	 * 检查整数
	 * 
	 * @param numString
	 * @return
	 */
	public static boolean isNumber(String numString) {
		String regex = "^([0-9]{1,})";
		return checkByPattern(regex, numString);
	}

	public static boolean checkByPattern(String regex, String checkString) {
		if (regex == null) {
			return false;
		}
		Pattern ptt = Pattern.compile(regex);
		Matcher mch = ptt.matcher(checkString);
		return mch.matches();
	}
	
}
