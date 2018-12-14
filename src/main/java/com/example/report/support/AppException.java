package com.example.report.support;

import org.apache.commons.lang3.StringUtils;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 8449738842423044010L;

    private ResultCode code;

    public AppException(ResultCode code) {
        this.code = code;
    }

    public AppException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResultCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isBlank(super.getMessage())) {
            return code.toString();
        }
        return super.getMessage();
    }
}
