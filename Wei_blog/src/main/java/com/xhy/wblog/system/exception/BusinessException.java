package com.xhy.wblog.system.exception;


// 拿来返回错误信息

public class BusinessException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code) {
        super();
        this.code = code;
    }

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    protected BusinessException(String message, Integer code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
