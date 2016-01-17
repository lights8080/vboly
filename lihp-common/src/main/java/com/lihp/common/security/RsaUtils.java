package com.lihp.common.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;

import com.lihp.common.security.exception.RsaDecryptException;
import com.lihp.common.security.exception.RsaEncryptException;
import com.lihp.common.security.exception.RsaKeyException;

public final class RsaUtils {

    // 非对称加密密钥算法
    public static final String KEY_ALGORITHM = "RSA";

    // 密码信息-算法:RSA、模式:ECB、填充方式:PKCS1Padding
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    // 公钥
    private static final String PUBLIC_KEY = "RSAPublicKey";

    // 私钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    // RSA密钥长度,默认为1024,密钥长度必须是64的倍数,范围在521~65526位之间
    private static final int KEY_SIZE = 1024;

    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final int MAX_DECRYPT_BLOCK = 128;

    private RsaUtils() {
    }

    /**
     * 初始化密钥
     */
    public static Map<String, Key> initKey()
        throws RsaKeyException {

        try {
            // 实例化实钥对生成器
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 初始化密钥对生成器
            keyPairGen.initialize(KEY_SIZE);
            // 生成密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 封装密钥
            ConcurrentHashMap<String, Key> keyMap = new ConcurrentHashMap<String, Key>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new RsaKeyException(e.getMessage(), e);
        }
    }

    /**
     * 取得公钥
     */
    public static Key getPublicKey(final Map<String, Key> keyMap) {
        return keyMap.get(PUBLIC_KEY);
    }

    /**
     * 取得私钥
     */
    public static Key getPrivateKey(final Map<String, Key> keyMap) {
        return keyMap.get(PRIVATE_KEY);
    }

    /**
     * 公钥加密
     */
    public static byte[] encryptByPublicKey(final byte[] key, final byte[] data)
        throws RsaEncryptException {

        try {
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 对数据分段加密
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            throw new RsaEncryptException(e.getMessage(), e);
        }
    }

    /**
     * 私钥加密
     */
    public static byte[] encryptByPrivateKey(final byte[] key, final byte[] data)
        throws RsaEncryptException {

        try {
            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成私钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 对数据分段加密
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            throw new RsaEncryptException(e.getMessage(), e);
        }
    }

    /**
     * 公钥解密
     */
    public static byte[] decryptByPublicKey(final byte[] key, final byte[] data)
        throws RsaDecryptException {

        try {
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成公钥
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 对数据分段解密
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            throw new RsaDecryptException(e.getMessage(), e);
        }
    }

    /**
     * 私钥解密
     */
    public static byte[] decryptByPrivateKey(final byte[] key, final byte[] data)
        throws RsaDecryptException {

        try {
            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成私钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 对数据分段解密
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            throw new RsaDecryptException(e.getMessage(), e);
        }
    }
}
