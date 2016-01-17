package com.lihp.common.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.lihp.common.security.exception.DesDecryptException;
import com.lihp.common.security.exception.DesEncryptException;
import com.lihp.common.security.exception.DesKeyException;

public final class DesUtils {

    // 密钥算法-DES
    public static final String KEY_ALGORITHM = "DES";

    // 密码信息-算法:DES、模式:ECB、填充方式:PKCS5Padding
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    // 密钥长度-(java 6 只支持56位密码，Bouncy Castle 支持64位密码)
    public static final int KEY_SIZE = 56;

    private DesUtils() {
    }

    /**
     * 生成密钥
     */
    public static byte[] initKey()
        throws DesKeyException {

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (Exception e) {
            throw new DesKeyException(e.getMessage(), e);
        }
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] key, byte[] data)
        throws DesEncryptException {

        try {
            SecretKey secretKey = toKey(key);
            Cipher encipher = Cipher.getInstance(CIPHER_ALGORITHM);
            encipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return encipher.doFinal(data);
        } catch (Exception e) {
            throw new DesEncryptException(e.getMessage(), e);
        }
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] key, byte[] data)
        throws DesDecryptException {

        try {
            SecretKey secretKey = toKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new DesDecryptException(e.getMessage(), e);
        }
    }

    /**
     * 转换密钥
     */
    private static SecretKey toKey(byte[] key) {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }
}
