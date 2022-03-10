package com.xhy.wblog.controller.vo;


//接受注册前端数据的实体类
public class RegisterVo {

    private String email;
    private String password;
    private String captcha;
    private Integer gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterVo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
