package com.xhy.wblog.controller.result;


// 本类是拿来返回前端数据的实体类

public class PublicResult {

    // 操作结果编码
    private Integer code;
    // 操作数据结果
    private Object date;
    // 返回消息
    private String message;



    public PublicResult() {
    }

    // 返回状态码
    public PublicResult(Integer code) {
        this.code = code;
    }

    // 返回状态码 和 数据
    public PublicResult(Integer code, Object date) {
        this.code = code;
        this.date = date;
    }

    // 返回状态码和消息
    public PublicResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 返回状态码 和 消息 ，和数据
    public PublicResult(Integer code, Object date, String message) {
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
}
