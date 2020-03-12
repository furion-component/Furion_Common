package com.huibur.furion.common.codec;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;
import com.huibur.furion.common.lang.ExceptionUtils;

/*******************************************************
 * Copyright(c)2019-2020 HuiBur. All rights reserved.
 * Header: DigestUtils.java
 * Discussion: Irreversible encryption tools
 * Create Date: 2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class DigestUtils {
    private static SecureRandom random = new SecureRandom();

	/***
	 * Name: genSalt
	 * Discussion: Generate random Byte [] as salt key
	 * @param numBytes array size
	 * @return byte array
	 ***/
    public static byte[] genSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Name: digest
     * Discussion: Hash strings, support md5 and sha1 algorithms.
     * @param input      String to be hashed
     * @param algorithm  Hashing algorithm（"SHA-1"、"MD5"）
     * @param salt       salt key
     * @param iterations Number of iterations
     * @return byte array
     */
    public static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * Name: digest
     * Discussion: Sha1 the file.
     * @param input      file stream
     * @param algorithm  Hashing algorithm（"SHA-1"、"MD5"）
     * @return byte array
     * @throws IOException e
     */
    public static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

}
