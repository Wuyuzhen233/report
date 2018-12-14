
package com.example.report.support;

public enum ResultCode {

    /**
     * (保留码) 表示未知异常
     */
    UNEXCEPTED(9999),

    /**
     * 成功
     */
    SUCCESS(1000),

    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(1001),

    /**
     * 用户不存在
     */
    USER_NOT_EXISTS(1002),

    /**
     * 入库失败
     */
    FAIL_DATABASE(1003),

    /**
     * 登录信息不完整
     */
    LOGIN_INFO_INCOMPLETE(1004),

    /**
     * 入参不完整
     */
    PARAMS_INCOMPLETE(1005),
    /**
     * 没有权限
     */
    NOT_HAVE_AUTHORITY(1006);

    private int value;

    private ResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

