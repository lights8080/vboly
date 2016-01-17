package com.lihp.common.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.lihp.common.codec.CharEncoding;
import com.lihp.common.security.exception.DesDecryptException;
import com.lihp.common.security.exception.DesEncryptException;
import com.lihp.common.security.exception.DesKeyException;

public final class DesHelper {

    private DesHelper() {
    }

    /**
     * 生成密钥
     */
    public static String initKeyBase64()
        throws DesKeyException {

        return Base64.encodeBase64URLSafeString(DesUtils.initKey());
    }

    /**
     * 加密
     */
    public static String encryptBase64(final String key, final String data)
        throws DesEncryptException {

        byte[] keyBytes = Base64.decodeBase64(StringUtils.getBytesUtf8(key));
        byte[] dataBytes = StringUtils.getBytesUtf8(data);
        return Base64.encodeBase64String(DesUtils.encrypt(keyBytes, dataBytes));
    }

    /**
     * 解密
     */
    public static String decryptBase64(final String key, final String data)
        throws DesDecryptException {

        byte[] keyBytes = Base64.decodeBase64(StringUtils.getBytesUtf8(key));
        byte[] dataBytes = Base64.decodeBase64(data);
        return StringUtils.newStringUtf8(DesUtils.decrypt(keyBytes, dataBytes));
    }

    /**
     * 加密
     */
    public static String encryptBase64GB18030(final String key, final String data)
        throws DesEncryptException {

        byte[] keyBytes = Base64.decodeBase64(StringUtils.getBytesUtf8(key));
        byte[] dataBytes = StringUtils.getBytesUnchecked(data, CharEncoding.GB18030);
        return Base64.encodeBase64String(DesUtils.encrypt(keyBytes, dataBytes));
    }

    /**
     * 解密
     */
    public static String decryptBase64GB18030(final String key, final String data)
        throws DesDecryptException {

        byte[] keyBytes = Base64.decodeBase64(StringUtils.getBytesUtf8(key));
        byte[] dataBytes = Base64.decodeBase64(data);
        return StringUtils.newString(DesUtils.decrypt(keyBytes, dataBytes), CharEncoding.GB18030);
    }
}
