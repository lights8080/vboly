package com.lihp.common.security;

import java.security.Key;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.lihp.common.security.exception.RsaDecryptException;
import com.lihp.common.security.exception.RsaEncryptException;
import com.lihp.common.security.exception.RsaKeyException;

public final class RsaHelper {

    private RsaHelper() {
    }

    /**
     * 初始化密钥
     */
    public static Map<String, Key> initKey()
        throws RsaKeyException {

        return RsaUtils.initKey();
    }

    /**
     * 取得公钥
     */
    public static Key getPublicKey(final Map<String, Key> keyMap) {
        return RsaUtils.getPublicKey(keyMap);
    }

    /**
     * 取得私钥
     */
    public static Key getPrivateKey(final Map<String, Key> keyMap) {
        return RsaUtils.getPrivateKey(keyMap);
    }

    /**
     * 公钥加密
     */
    public static String encryptBase64ByPublicKey(final Key publicKey, final String data)
        throws RsaEncryptException {

        byte[] keyBytes = publicKey.getEncoded();
        byte[] dataBytes = StringUtils.getBytesUtf8(data);
        return Base64.encodeBase64URLSafeString(RsaUtils.encryptByPublicKey(keyBytes, dataBytes));
    }

    /**
     * 私钥加密
     */
    public static String encryptBase64ByPrivateKey(final Key privateKey, final String data)
        throws RsaEncryptException {

        byte[] keyBytes = privateKey.getEncoded();
        byte[] dataBytes = StringUtils.getBytesUtf8(data);
        return Base64.encodeBase64URLSafeString(RsaUtils.encryptByPrivateKey(keyBytes, dataBytes));
    }

    /**
     * 公钥解密
     */
    public static String decryptBase64ByPublicKey(final Key publicKey, final String data)
        throws RsaDecryptException {

        byte[] keyBytes = publicKey.getEncoded();
        byte[] dataBytes = Base64.decodeBase64(data);
        return StringUtils.newStringUtf8(RsaUtils.decryptByPublicKey(keyBytes, dataBytes));
    }

    /**
     * 私钥解密
     */
    public static String decryptBase64ByPrivateKey(final Key privateKey, final String data)
        throws RsaDecryptException {

        byte[] keyBytes = privateKey.getEncoded();
        byte[] dataBytes = Base64.decodeBase64(data);
        return StringUtils.newStringUtf8(RsaUtils.decryptByPrivateKey(keyBytes, dataBytes));
    }
}
