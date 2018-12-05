package com.example.report.support;

/**
 * Created by wangyuan on 2018/11/26.
 */
public enum StaticParam {
    DAYS_PER_MONTH("22.5");// 每月工作基数

    private final String param;

    public String getParam() {
        return param;
    }

    StaticParam(String param) {
        this.param = param;
    }
}
