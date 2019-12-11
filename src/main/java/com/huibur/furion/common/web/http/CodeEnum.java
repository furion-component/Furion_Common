package com.huibur.furion.common.web.http;

public enum CodeEnum {
  PARAM_ERROR(150,"参数错误"),
  HAS_EXISTED(160,"数据已存在"),
  NOT_EXISTED(170,"数据不存在"),
  SUCCESS(200,"成功"),
  FAIL(210, "失败"),
  SERVER_ERROR(500,"服务器内部错误");

  private int code;
  private String msg;

  private CodeEnum(int code, String msg) {
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
