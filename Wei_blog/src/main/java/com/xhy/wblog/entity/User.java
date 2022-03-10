package com.xhy.wblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@TableName("user")   // 说明实体类名
@Data   // 生成get和set
@AllArgsConstructor    // 生成全参构造
@NoArgsConstructor     // 生成空构造方法
@ToString              // 生成toString方法
public class User {


    @TableId(type = IdType.AUTO)    //主键自增
    @TableField("id")               // 表的属性名，若属性与表字段相同，或者可以进行驼峰映射，可以不写（下面我都没写）
    private Integer id;             // 主键id
    private Date createdTime;       // 创建时间
    private String password;        // 密码
    private String email;           // 邮箱
    private Date birthday;          // 生日
    private String photo;           // 头像
    private String intro;           // 简介
    private String name;            // 昵称
    private String address;         // 地址
    private String phone;           // 电话
    private String job;             // 职业
    private String trait;           // 特点
    private String interests;       // 兴趣
    private Integer gender;
    /*
    * gender：性别
    * 男 ： 0
    * 女 ： 1
    *
    */

}
