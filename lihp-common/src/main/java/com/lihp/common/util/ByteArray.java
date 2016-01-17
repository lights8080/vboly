package com.lihp.common.util;

/**
 * ByteArray工具类。
 */
public final class ByteArray {

	private static char[] numberLowerCase = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static char[] numberUpperCase = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private ByteArray() {
	}

	/**
	 * toHexString
	 * 
	 * @param bytes
	 *            byte[]
	 * @param boolean
	 * @return String
	 */
	public static String toHexString(byte[] bytes, boolean upperCase) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		StringBuffer buffer = new StringBuffer(0);
		for (int i = 0; i < bytes.length; i++) {
			buffer.append(byteToHexString(bytes[i], upperCase));
		}

		return buffer.toString();
	}

	/**
	 * byteToHexString
	 * 
	 * @param byte0
	 *            byte
	 * @return String
	 */
	public static String byteToHexString(byte byte0, boolean upperCase) {
		char[] buffer = new char[2];
		if (upperCase) {
			buffer[0] = numberUpperCase[byte0 >= 0 ? byte0 >> 4
					: (byte0 + 256) >> 4];
			buffer[1] = numberUpperCase[byte0 & 0x0F];
		} else {
			buffer[0] = numberLowerCase[byte0 >= 0 ? byte0 >> 4
					: (byte0 + 256) >> 4];
			buffer[1] = numberLowerCase[byte0 & 0x0F];
		}
		return new String(buffer);
	}
}
