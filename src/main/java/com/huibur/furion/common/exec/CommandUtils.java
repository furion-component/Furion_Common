package com.huibur.furion.common.exec;

import java.io.*;

/*******************************************************
 * Copyright(c)2019-2019 HuiBur. All rights reserved.
 * Header: CommandUtils.java
 * Discussion: Command line utils
 * Create Date: 2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class CommandUtils {

	public static String execute(String command) throws IOException {
		return execute(command, "GBK");
	}
	
	public static String execute(String command, String charsetName) throws IOException {
		Process process = Runtime.getRuntime().exec(command);
		// 记录dos命令的返回信息
		StringBuffer sb = new StringBuffer();
		// 获取返回信息的流
		InputStream in = process.getInputStream();
		Reader reader = new InputStreamReader(in, charsetName);
		BufferedReader bReader = new BufferedReader(reader);
		String res = bReader.readLine();
		while (res != null) {
			sb.append(res);
			sb.append("\n");
			res = bReader.readLine();
		}
		bReader.close();
		reader.close();
		return sb.toString();
	}
}
