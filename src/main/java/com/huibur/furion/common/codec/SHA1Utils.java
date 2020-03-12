package com.huibur.furion.common.codec;

import java.io.IOException;
import java.io.InputStream;

/*******************************************************
 * Copyright(c)2019-2019 HuiBur. All rights reserved.
 * Header: SHA1Utils.java
 * Discussion: SHA-1 algorithm
 * Create Date: 2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class SHA1Utils {

	private static final String SHA1 = "SHA-1";


	public static byte[] genSalt(int numBytes) {
		return DigestUtils.genSalt(numBytes);
	}


	public static byte[] sha1(byte[] input) {
		return DigestUtils.digest(input, SHA1, null, 1);
	}


	public static byte[] sha1(byte[] input, byte[] salt) {
		return DigestUtils.digest(input, SHA1, salt, 1);
	}


	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return DigestUtils.digest(input, SHA1, salt, iterations);
	}


	public static byte[] sha1(InputStream input) throws IOException {
		return DigestUtils.digest(input, SHA1);
	}

}
