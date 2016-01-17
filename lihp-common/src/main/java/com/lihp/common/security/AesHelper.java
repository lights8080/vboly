package com.lihp.common.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.lihp.common.security.exception.AesDecryptException;
import com.lihp.common.security.exception.AesEncryptException;
import com.lihp.common.security.exception.AesKeyException;

public final class AesHelper {

    private AesHelper() {
    }

    /**
     * 生成密钥
     */
    public static String initKeyBase64()
        throws AesKeyException {

        return Base64.encodeBase64URLSafeString(AesUtils.initKey());
    }

    /**
     * 加密
     */
    public static String encryptBase64(final String key, final String data)
        throws AesEncryptException {

        byte[] keyBytes = Base64.decodeBase64(StringUtils.getBytesUtf8(key));
        byte[] dataBytes = StringUtils.getBytesUtf8(data);
        return Base64.encodeBase64URLSafeString(AesUtils.encrypt(keyBytes, dataBytes));
    }

    /**
     * 解密
     */
    public static String decryptBase64(final String key, final String data)
        throws AesDecryptException {

        byte[] keyBytes = Base64.decodeBase64(StringUtils.getBytesUtf8(key));
        byte[] dataBytes = Base64.decodeBase64(data);
        return StringUtils.newStringUtf8(AesUtils.decrypt(keyBytes, dataBytes));
    }
}
