package com.example.report.support;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import com.example.report.utils.SpringContextUtil;
@JSONType(orders = {"code", "data", "message"})
public class Result<T> extends JSON {

  private int code;
  private T data;
  private String message;

  private Result() {

  }

  private Result(T data) {
    this.code = ResultCode.SUCCESS.getValue();
    this.data = data;
  }

  private Result(ResultCode code, String message) {
    ValidateMessageParser vm = SpringContextUtil.getBean(ValidateMessageParser.class);
    if (vm != null) {
      message = vm.parseMessage(code, message);
    }
    this.code = code.getValue();
    this.message = message;
  }

  private Result(ResultCode code, String message, T data) {
    ValidateMessageParser vm = SpringContextUtil.getBean(ValidateMessageParser.class);
    if (vm != null) {
      message = vm.parseMessage(code, message);
    }
    this.code = code.getValue();
    this.message = message;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static Result success() {
    return new Result(null);
  }

  /**
   * 解决范型问题，无法展示data基础类型
   */
  public static <U> Result<U> success(U data) {
    return new Result(data);
  }

  public static Result failed(Exception e) {
    if (e instanceof AppException) {
      return fail((AppException) e);
    } else {
      return new Result(ResultCode.UNEXCEPTED, null);
    }
  }

  private static Result fail(AppException e) {
    return new Result(e.getCode(), e.getMessage());
  }

  public static Result failed(ResultCode resultCode, String message) {
    return new Result(resultCode, message);

  }

  public static <U> Result<U> failed(ResultCode resultCode, String message, U data) {
    return new Result(resultCode, message, data);

  }
}
