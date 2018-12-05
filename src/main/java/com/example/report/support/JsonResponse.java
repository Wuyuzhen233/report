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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JsonResponse [code=");
        builder.append(code);
        builder.append(", msg=");
        builder.append(msg);
        builder.append(", data=");
        builder.append(data);
        builder.append(", page=");
        builder.append(page);
        builder.append("]");
        return builder.toString();
    }

}

