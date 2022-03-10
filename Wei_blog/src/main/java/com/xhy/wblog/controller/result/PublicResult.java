package com.xhy.wblog.controller.result;


// 本类是拿来返回前端数据的实体类

public class PublicResult {

    // 状态
    private Boolean success;
    // 操作结果编码
    private Integer code;
    // 操作数据结果
    private Object date;
    // 返回消息
    private String message;


    public PublicResult() {
    }

    public PublicResult(Boolean success, Integer code, Object date, String message) {
        this.success = success;
        this.code = code;
        this.date = date;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
