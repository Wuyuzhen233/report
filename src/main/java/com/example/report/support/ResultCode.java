package com.example.report.support;

/**
 * Created by wangyuan on 2018/11/22.
 */
public enum ResultCode {
    SUCCESS(200, "成功"),
    EXCEPTION(500, "系统异常"),
    FAIL(-1, "失败"),
    UNLOGIN(-2, "未登录");


    private Integer code;
    private String msg;

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
}
