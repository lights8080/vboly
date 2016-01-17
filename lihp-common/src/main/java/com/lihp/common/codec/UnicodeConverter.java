package com.lihp.common.codec;

import java.lang.Character.UnicodeBlock;

/**
 * Unicode转换器
 * 
 * @author lihp
 * @date 2015-03-03
 * 
 */
public class UnicodeConverter {

	/**
	 * UTF-8转Unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String utf8ToUnicode(String str) {
		char[] myBuffer = str.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				// 英文及数字等
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				// 全角半角字符
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				// 汉字
				String hexS = Integer.toHexString(myBuffer[i]);
				String unicode = "\\" + 'u' + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * unicode转UTF-8
	 * 
	 * @param s
	 * @return
	 */
	public static String unicodeToUtf8(String s) {
		char aChar;
		int len = s.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = s.charAt(x++);
			if (aChar == '\\') {
				aChar = s.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = s.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	/**
	 * GBK转UTF-8
	 * 
	 * @param gbk
	 * @return
	 */
	public String gbkToUtf8(String gbk) {
		String l_temp = GbkToUnicode(gbk);
		l_temp = unicodeToUtf8(l_temp);
		return l_temp;
	}

	/**
	 * UTF-8转GBK
	 * 
	 * @param gbk
	 * @return
	 */
	public String utf8ToGbk(String utf) {
		String l_temp = utf8ToUnicode(utf);
		l_temp = UnicodeToGbk(l_temp);
		return l_temp;
	}

	/**
	 * GBK转Unicode
	 * 
	 * @param str
	 *            GBK字符串
	 * @return String Uncode字符串
	 */
	public static String GbkToUnicode(String str) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char chr1 = (char) str.charAt(i);
			if (!isNeedConvert(chr1)) {
				result.append(chr1);
				continue;
			}
			result.append("\\u" + Integer.toHexString((int) chr1));
		}
		return result.toString();
	}

	/**
	 * Unicode转GBK
	 * 
	 * @param dataStr
	 *            Unicode字符串
	 * @return String GBK字符串
	 */
	public static String UnicodeToGbk(String dataStr) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		int li_len = dataStr.length();
		while (index < li_len) {
			if (index >= li_len - 1
					|| !"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));

				index++;
				continue;
			}
			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);

			char letter = (char) Integer.parseInt(charStr, 16);

			buffer.append(letter);
			index += 6;
		}
		return buffer.toString();
	}

	public static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);
	}

}
