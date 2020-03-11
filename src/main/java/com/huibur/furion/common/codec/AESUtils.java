package com.huibur.furion.common.codec;

import com.huibur.furion.common.lang.ExceptionUtils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************
 * Copyright(c)2019-2020 HuiBur. All rights reserved.
 * Header: AESUtils.java
 * Discussion: AES algorithm
 * Create Date：2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class AESUtils {
    private static final String AES = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    /**
     * AES key length, default length is 128 bits (16 bytes)
     */
    private static final int DEFAULT_AES_KEY_SIZE = 128;
    /**
     * Generate a random vector, the default size is cipher.getBlockSize(), 16 bytes
     */
    private static final int DEFAULT_IV_SIZE = 16;
    /**
     * generate generateIV random number object
     */
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final byte[] DEFAULT_KEY = new byte[]{-97, 88, -94, 9, 70, -76, 126, 25, 0, 3, -20, 113, 108, 28, 69, 125};

    /***
     * Name: genKey
     * Discussion: Generates an AES key, returns a byte array with a default length of 128 bits (16 bytes)
     * @return AES key byte array
     ***/
    public static byte[] genKey() {
        return genKey(DEFAULT_AES_KEY_SIZE);
    }

    /***
     * Name: genKey
     * Discussion: Generate AES key with optional length of 128,192,256 bits
     * @param keySize 128,192,256 (AES key with optional length)
     * @return AES key byte array
     ***/
    public static byte[] genKey(int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(keySize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /***
     * Name: genIV
     * Discussion: Generate a random vector.The default size is cipher.getBlockSize (), 16 bytes.
     * @return vector byte array
     ***/
    public static byte[] genIV() {
        byte[] bytes = new byte[DEFAULT_IV_SIZE];
        RANDOM.nextBytes(bytes);
        return bytes;
    }

    /***
     * Name: genKeyString
     * Discussion: Generates an AES key, returns string with a default length of 128 bits (16 bytes).
     * @return AES key string
     ***/
    public static String genKeyString() {
        return EncodeUtils.encodeHex(genKey(DEFAULT_AES_KEY_SIZE));
    }

    /***
     * Name: encode
     * Discussion: encrypt data by default key
     * @param input plain text
     * @return String
     ***/
    public static String encode(String input) {
        try {
            return EncodeUtils.encodeHex(encode(input.getBytes(DEFAULT_URL_ENCODING), DEFAULT_KEY));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /***
     * Name: encode
     * Discussion: encrypt data by key
     * @param input plain text
     * @param key  aes key
     * @return String
     ***/
    public static String encode(String input, String key) {
        try {
            return EncodeUtils.encodeHex(encode(input.getBytes(DEFAULT_URL_ENCODING), EncodeUtils.decodeHex(key)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /***
     * Name: decode
     * Discussion: decode data by default key
     * @param input cipher text
     * @return plain text
     ***/
    public static String decode(String input) {
        try {
            return new String(decode(EncodeUtils.decodeHex(input), DEFAULT_KEY), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /***
     * Name: decode
     * Discussion: decode data by key
     * @param input cipher text
     * @param key  aes key
     * @return plain text
     ***/
    public static String decode(String input, String key) {
        try {
            return new String(decode(EncodeUtils.decodeHex(input), EncodeUtils.decodeHex(key)), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /***
     * Name: encode
     * Discussion: encode data by key
     * @param input Plain text character array
     * @param key  aes key array
     * @return cipher text character array
     ***/
    public static byte[] encode(byte[] input, byte[] key) {
        return aes(input, key, Cipher.ENCRYPT_MODE);
    }

    /***
     * Name: encode
     * Discussion: decode data by key
     * @param input Plain text character array
     * @param key  aes key
     * @param iv  vector byte array
     * @return cipher text character array
     ***/
    public static byte[] encode(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, Cipher.ENCRYPT_MODE);
    }

    /***
     * Name: decode
     * Discussion: decode data by key
     * @param input cipher text character array
     * @param key  aes key array
     * @return Plain text character array
     ***/
    public static byte[] decode(byte[] input, byte[] key) {
        return aes(input, key, Cipher.DECRYPT_MODE);
    }

    /***
     * Name: decode
     * Discussion: decode data by key
     * @param input cipher text character array
     * @param key  aes key
     * @param iv  vector byte array
     * @return Plain text character array
     ***/
    public static byte[] decode(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, Cipher.DECRYPT_MODE);
    }

    /***
     * Name: aes
     * Discussion: decode data by key
     * @param input Plain text character array
     * @param key  aes key
     * @param mode  Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @return cipher text character array
     ***/
    private static byte[] aes(byte[] input, byte[] key, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /***
     * Name: aes
     * Discussion: decode data by key
     * @param input Plain text character array
     * @param key  aes key
     * @param iv  vector byte array
     * @param mode  Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @return Plain text character array
     ***/
    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(AES_CBC);
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }
}