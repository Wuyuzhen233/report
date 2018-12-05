package com.example.report.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * Created by wangyuan on 2018/11/22.
 */
@JSONType(orders = {"code", "msg", "data"})
public class JsonResponse extends JSON {

    private ResultCode code;
    private String msg;
    private Object data;
    private Integer page;

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    //{"a": "Hello", "b": "World"}


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":");
        sb.append(code);
        sb.append(",\"msg\":\"");
        sb.append(msg).append('\"');
        sb.append(",\"data\":");
        sb.append(data);
        sb.append(",\"page\":");
        sb.append(page);
        sb.append('}');
        return sb.toString();
    }
}

