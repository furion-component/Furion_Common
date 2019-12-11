package com.huibur.furion.common.web.http;

/*******************************************************
 * Copyright(c)2019 - The HuiBur .All rights reserved.
 * Header: ResponseMessage.java
 * Discussion:
 *     封装http返回信息结构
 * Create Date： 2019/06/05
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class ResponseMessage<T> {
  private int respCode;
  private String respMsg;
  private T data;

  public ResponseMessage() {
  }

  public ResponseMessage(int respCode, String message) {
    this.respCode = respCode;
    this.respMsg = message;
  }

  public ResponseMessage(int respCode, String message, T data) {
    this.respCode = respCode;
    this.respMsg = message;
    this.data = data;
  }

  public int getRespCode() {
    return this.respCode;
  }

  public void setRespCode(int respCode) {
    this.respCode = respCode;
  }

  public String getMessage() {
    return this.respMsg;
  }

  public void setMessage(String message) {
    this.respMsg = message;
  }

  public T getData() {
    return (T) this.data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
