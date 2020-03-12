package com.huibur.furion.common.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huibur.furion.common.collect.ListUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

/*******************************************************
 * Copyright(c)2019-2019 HuiBur. All rights reserved.
 * Header: EncodeUtils.java
 * Discussion: Encoding and decoding tools for various formats
 * Create Date：2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class EncodeUtils {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

//    /**
//     * Hex encode.
//     */
    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

//    /**
//     * Hex decode.
//     */
    public static byte[] decodeHex(String input) {

        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * Base64 encode.
//     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

//    /**
//     * Base64 decode.
//     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

//	/**
//	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
//	 */
//	public static String encodeUrlSafeBase64(byte[] input) {
//		return Base64.encodeBase64URLSafe(input);
//	}

//    /**
//     * Base64 decode.
//     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

//    /**
//     * Base64 decode.
//     */
    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

//    /**
//     * Base62 encode。
//     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

//    /**
//     * Html encode.
//     */
    public static String encodeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

//    /**
//     * Html decode.
//     */
    public static String decodeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

//    /**
//     * Xml encode.
//     */
    public static String encodeXml(String xml) {
        return StringEscapeUtils.escapeXml10(xml);
    }

//    /**
//     * Xml decode.
//     */
    public static String decodeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

//    /**
//     * URL encode, Encode default UTF-8.
//     */
    public static String encodeUrl(String part) {
        return encodeUrl(part, DEFAULT_URL_ENCODING);
    }

//    /**
//     * URL encode, Encode default UTF-8.
//     */
    public static String encodeUrl(String part, String encoding) {
        if (part == null) {
            return null;
        }

        try {
            return URLEncoder.encode(part, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * URL decode, Encode default UTF-8.
//     */
    public static String decodeUrl(String part) {
        return decodeUrl(part, DEFAULT_URL_ENCODING);
    }

//    /**
//     * URL decode, EEncode default UTF-8.
//     */
    public static String decodeUrl(String part, String encoding) {
        if (part == null) {
            return null;
        }
        try {
            return URLDecoder.decode(part, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * URL decode（twice）, Encode default UTF-8.
//     */
    public static String decodeUrl2(String part) {
        return decodeUrl(decodeUrl(part));
    }

    // Precompiled XSS filtered regular expressions
    private static List<Pattern> xssPatterns = ListUtils.newArrayList(
            Pattern.compile("(<\\s*(script|link|style|iframe)([\\s\\S]*?)(>|<\\/\\s*\\1\\s*>))|(</\\s*(script|link|style|iframe)\\s*>)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\s*(href|src)\\s*=\\s*(\"\\s*(javascript|vbscript):[^\"]+\"|'\\s*(javascript|vbscript):[^']+'|(javascript|vbscript):[^\\s]+)\\s*(?=>)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\s*on[a-z]+\\s*=\\s*(\"[^\"]+\"|'[^']+'|[^\\s]+)\\s*(?=>)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(eval\\((.*?)\\)|xpression\\((.*?)\\))", Pattern.CASE_INSENSITIVE)
    );

//    /**
//     * XSS Illegal character filtering, the content that starts with <!-HTML-> uses the following rules (reserved tags)
//     */
    public static String xssFilter(String text) {
        String oriValue = StringUtils.trim(text);
        if (text != null) {
            String value = oriValue;
            for (Pattern pattern : xssPatterns) {
                Matcher matcher = pattern.matcher(value);
                if (matcher.find()) {
                    value = matcher.replaceAll(StringUtils.EMPTY);
                }
            }
            // If the format is not HTML, XML, or JOSN, then HTML, ", <,> transcoding is performed.
            if (!StringUtils.startsWithIgnoreCase(value, "<!--HTML-->")    // HTML
                    && !StringUtils.startsWithIgnoreCase(value, "<?xml ")    // XML
                    && !StringUtils.contains(value, "id=\"FormHtml\"")        // JFlow
                    && !(StringUtils.startsWith(value, "{") && StringUtils.endsWith(value, "}")) // JSON Object
                    && !(StringUtils.startsWith(value, "[") && StringUtils.endsWith(value, "]")) // JSON Array
            ) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < value.length(); i++) {
                    char c = value.charAt(i);
                    switch (c) {
                        case '>':
                            sb.append("＞");
                            break;
                        case '<':
                            sb.append("＜");
                            break;
                        case '\'':
                            sb.append("＇");
                            break;
                        case '\"':
                            sb.append("＂");
                            break;
//					case '&':
//						sb.append("＆");
//						break;
//					case '#':
//						sb.append("＃");
//						break;
                        default:
                            sb.append(c);
                            break;
                    }
                }
                value = sb.toString();
            }
//			if (logger.isInfoEnabled() && !value.equals(oriValue)){
//				logger.info("xssFilter: {}   <=<=<=   {}", value, text);
//			}
            return value;
        }
        return null;
    }

    // Precompiled SQL filtering regular expressions
    private static Pattern sqlPattern = Pattern.compile("(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)", Pattern.CASE_INSENSITIVE);

//    /**
//     * SQL filtering to prevent injection, select related code for incoming parameter input, replace empty.
//     * @author ThinkGem
//     */
    public static String sqlFilter(String text) {
        if (text != null) {
            String value = text;
            Matcher matcher = sqlPattern.matcher(value);
            if (matcher.find()) {
                value = matcher.replaceAll(StringUtils.EMPTY);
            }
//			if (logger.isWarnEnabled() && !value.equals(text)){
//				logger.info("sqlFilter: {}   <=<=<=   {}", value, text);
//				return StringUtils.EMPTY;
//			}
            return value;
        }
        return null;
    }


}
