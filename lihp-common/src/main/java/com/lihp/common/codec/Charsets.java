package com.lihp.common.codec;

import java.nio.charset.Charset;

public final class Charsets extends org.apache.commons.codec.Charsets {

	public static final Charset GBK = Charset.forName(CharEncoding.GBK);

	private Charsets() {
		super();
	}
}
