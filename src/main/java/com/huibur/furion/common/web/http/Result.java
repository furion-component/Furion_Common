package com.huibur.furion.common.web.http;

/*******************************************************
 * Copyright(c)2018 - The HuiBur .All rights reserved.
 * Header: Result.java
 * Discussion: http接口返回类，成功，失败两种状态
 * Create Date：2018/7/6
 * Author: Jerry
 * Version: 1.0
 *******************************************************/
public class Result {
  public static ResponseMessage success() {
    return new ResponseMessage(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
  }

  public static <T> ResponseMessage<T> success(int code, String message) {
    return new ResponseMessage<>(code, message);
  }

  public static <T> ResponseMessage<T> success(int code, T t) {
    return new ResponseMessage<>(code, "", t);
  }

  public static <T> ResponseMessage<T> success(T t) {
    return new ResponseMessage<>(CodeEnum.SUCCESS.getCode(), "", t);
  }

  public static ResponseMessage error() {
    return new ResponseMessage<>(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
  }

  public static ResponseMessage error(String message) {
    return error(CodeEnum.FAIL.getCode(), message);
  }

  public static ResponseMessage error(int code, String message) {
    return error(code, message, null);
  }

  public static <T> ResponseMessage<T> error(int code, String message, T t) {
    return new ResponseMessage<>(code, message, t);
  }
}
