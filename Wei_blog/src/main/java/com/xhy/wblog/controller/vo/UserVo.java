package com.xhy.wblog.controller.vo;

// 本处是用来接收前端数据的实体对象

public class UserVo {


    private String name;
    private String password;
    private Integer gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
