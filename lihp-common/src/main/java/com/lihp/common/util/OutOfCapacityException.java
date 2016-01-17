package com.lihp.common.util;

/**
 * 超出容量异常。
 */
public class OutOfCapacityException extends Exception {

	/**
	 * 类的序列化版本UID。
	 */
	private static final long serialVersionUID = -2356774821396922203L;

	/**
	 * 构造一个超出容量异常。
	 */
	public OutOfCapacityException() {
		super();
	}

	/**
	 * 构造一个超出容量异常。
	 * 
	 * @param message
	 *            详细消息。
	 */
	public OutOfCapacityException(String message) {
		super(message);
	}

	/**
	 * 构造一个超出容量异常。
	 * 
	 * @param message
	 *            详细消息。
	 * @param cause
	 *            原因。
	 */
	public OutOfCapacityException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造一个超出容量异常。
	 * 
	 * @param cause
	 *            原因。
	 */
	public OutOfCapacityException(Throwable cause) {
		super(cause);
	}
}
