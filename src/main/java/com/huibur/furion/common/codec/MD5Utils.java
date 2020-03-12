package com.huibur.furion.common.codec;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/*******************************************************
 * Copyright(c)2019-2020 HuiBur. All rights reserved.
 * Header: MD5Utils.java
 * Discussion: MD5 encryption
 * Create Date: 2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class MD5Utils {

    private static final String MD5 = "MD5";
    private static final String DEFAULT_ENCODING = "UTF-8";

    /***
     * Name: md5
     * Discussion: Md5 hash the input string.
     * @param input Encrypted string
     * @return cipher text
     */
    public static String md5(String input) {
        return md5(input, 1);
    }

    /***
     * Name: md5
     * Discussion: Md5 hash the input string.
     * @param input      plain text
     * @param iterations Number of iterations
     * @return cipher text
     */
    public static String md5(String input, int iterations) {
        try {
            return EncodeUtils.encodeHex(DigestUtils.digest(input.getBytes(DEFAULT_ENCODING), MD5, null, iterations));
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

    /***
     * Name: md5
     * Discussion: Md5 hash the input string
     * @param input byte plain text
     * @return  byte cipher text
     */
    public static byte[] md5(byte[] input) {
        return md5(input, 1);
    }

    /***
     * Name: md5
     * Discussion: Md5 hash the input string
     * @param input      plain text
     * @param iterations Number of iterations
     * @return byte cipher text
     */
    public static byte[] md5(byte[] input, int iterations) {
        return DigestUtils.digest(input, MD5, null, iterations);
    }

    /***
     * Name: md5
     * Discussion: Md5 hash the input string
     * @param input      plain text
     * @return byte cipher text
     * @throws IOException e
     */
    public static byte[] md5(InputStream input) throws IOException {
        return DigestUtils.digest(input, MD5);
    }

    /***
     * Name: md5File
     * Discussion: Md5 hash the input file
     * @param file   file
     * @return  cipher text
     */
    public static String md5File(File file) {
        return md5File(file, -1);
    }


    /***
     * Name: md5File
     * Discussion: Md5 hash the input file, Support to get the MD5 value of the file part
     * @param file   file
     * @param size fileSize(file size or the file part)
     * @return  cipher text
     */
    public static String md5File(File file, int size) {
        if (file != null && file.exists()) {
            try {
                InputStream in = FileUtils.openInputStream(file);
                byte[] bytes = null;
                if (size != -1 && file.length() >= size) {
                    bytes = IOUtils.toByteArray(in, size);
                } else {
                    bytes = IOUtils.toByteArray(in);
                }
                return EncodeUtils.encodeHex(md5(bytes));
            } catch (IOException e) {
                return StringUtils.EMPTY;
            }
        }
        return StringUtils.EMPTY;
    }

}
